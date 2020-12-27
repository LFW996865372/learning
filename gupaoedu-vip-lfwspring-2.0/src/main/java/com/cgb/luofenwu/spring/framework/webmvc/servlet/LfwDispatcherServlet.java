package com.cgb.luofenwu.spring.framework.webmvc.servlet;


import com.cgb.luofenwu.spring.framework.annotation.LfwController;
import com.cgb.luofenwu.spring.framework.annotation.LfwRequestMapping;
import com.cgb.luofenwu.spring.framework.context.LfwApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MVC总控
 * 职责：负责任务调度，请求分发
 */
public class LfwDispatcherServlet extends HttpServlet {
    private LfwApplicationContext applicationContext;

    private List<LfwHandlerMapping> handlerMappings = new ArrayList<LfwHandlerMapping>();

    private Map<LfwHandlerMapping, LfwHandlerAdapter> handlerAdapters = new HashMap<LfwHandlerMapping, LfwHandlerAdapter>();

    private List<LfwViewResolver> viewResolvers = new ArrayList<LfwViewResolver>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //6、委派,根据URL去找到一个对应的Method并通过response返回
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            try {
                processDispatchResult(req, resp, new LfwModelAndView("500"));
            } catch (Exception e1) {
                e1.printStackTrace();
                resp.getWriter().write("500 Exception,Detail : " + Arrays.toString(e.getStackTrace()));
            }
        }
    }

    /**
     * 核心方法入口
     * 完成了对HandlerMapping的封装
     * 完成了对方法返回值的封装 ModelAndView
     *
     * @param req
     * @param resp
     * @throws Exception
     */
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1、通过URL获得一个HandlerMapping
        LfwHandlerMapping handlerMapping = getHandler(req);
        if (handlerMapping == null) {
            //找不到服务器的资源404
            processDispatchResult(req, resp, new LfwModelAndView("404"));
            return;
        }
        //2、根据一个HandlerMaping获得一个HandlerAdapter
        LfwHandlerAdapter handlerAdapter = getHandlerAdapter(handlerMapping);

        //3、解析某一个方法的形参和返回值之后，统一封装为ModelAndView对象
        LfwModelAndView mv = handlerAdapter.handler(req, resp, handlerMapping);
        //4、把ModelAndView变成一个ViewResolver
        processDispatchResult(req, resp, mv);
    }

    private LfwHandlerAdapter getHandlerAdapter(LfwHandlerMapping handlerMapping) {
        if (this.handlerAdapters.isEmpty()) {
            return null;
        }
        return this.handlerAdapters.get(handlerMapping);
    }

    /**
     * 把ModelAndView变成一个ViewResolver
     *
     * @param req
     * @param resp
     * @param mv
     * @throws Exception
     */
    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, LfwModelAndView mv) throws Exception {
        if (null == mv) {
            return;
        }
        if (this.viewResolvers.isEmpty()) {
            return;
        }
        //转view往浏览器输出
        for (LfwViewResolver viewResolver : this.viewResolvers) {
            LfwView view = viewResolver.resolveViewName(mv.getViewName());
            view.render(mv.getModel(), req, resp);
            return;
        }
    }

    /**
     * 通过URL获得一个HandlerMapping
     *
     * @param req
     * @return
     */
    private LfwHandlerMapping getHandler(HttpServletRequest req) {
        if (this.handlerMappings.isEmpty()) {
            return null;
        }
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        //将上下文部分去除掉；将//替换为/
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");

        for (LfwHandlerMapping mapping : handlerMappings) {
            Matcher matcher = mapping.getPattern().matcher(url);
            if (!matcher.matches()) {
                continue;
            }
            return mapping;
        }
        return null;
    }

    /**
     * =======================初始化========================
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化Spring核心IoC容器
        applicationContext = new LfwApplicationContext(config.getInitParameter("contextConfigLocation"));
        //初始化九大组件
        try {
            initStrategies(applicationContext);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化失败");
        }
        System.out.println("Lfw Spring framework is init.");
    }

    /**
     * 初始化3大组件
     *
     * @param context
     * @throws Exception
     */
    private void initStrategies(LfwApplicationContext context) throws Exception {
        //TODO 暂时只实现3大组件
        //handlerMapping
        initHandlerMappings(context);
        //初始化参数适配器
        initHandlerAdapters(context);
        //初始化视图转换器
        initViewResolvers(context);
    }

    /**
     * 初始化视图转换器
     *
     * @param context
     */
    private void initViewResolvers(LfwApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        for (File file : templateRootDir.listFiles()) {
            this.viewResolvers.add(new LfwViewResolver(templateRoot));
        }
    }

    /**
     * 初始化参数适配器
     *
     * @param context
     */
    private void initHandlerAdapters(LfwApplicationContext context) {
        for (LfwHandlerMapping handlerMapping : handlerMappings) {
            this.handlerAdapters.put(handlerMapping, new LfwHandlerAdapter());
        }
    }

    /**
     * 初始化处理器映射器
     *
     * @param context
     * @throws Exception
     */
    private void initHandlerMappings(LfwApplicationContext context) throws Exception {
        //如果没有需要处理的映射
        if (this.applicationContext.getBeanDefinitionCount() == 0) {
            return;
        }

        for (String beanName : this.applicationContext.getBeanDefinitionNames()) {
            Object instance = applicationContext.getBean(beanName);
            Class<?> clazz = instance.getClass();
            if (!clazz.isAnnotationPresent(LfwController.class)) {
                continue;
            }

            //提取class上配置的url
            String baseUrl = "";
            if (clazz.isAnnotationPresent(LfwRequestMapping.class)) {
                LfwRequestMapping requestMapping = clazz.getAnnotation(LfwRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            //只获取public的方法
            for (Method method : clazz.getMethods()) {
                if (!method.isAnnotationPresent(LfwRequestMapping.class)) {
                    continue;
                }
                //提取每个方法上面配置的url
                LfwRequestMapping requestMapping = method.getAnnotation(LfwRequestMapping.class);

                // //demo//query
                String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*", ".*")).replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                LfwHandlerMapping handlerMapping = new LfwHandlerMapping(pattern, instance, method);
                handlerMappings.add(handlerMapping);
                System.out.println("initHandlerMappings:" + handlerMapping);
            }
        }
    }
}
