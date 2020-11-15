import com.cgb.luofenwu.spring.framework.context.LfwApplicationContext;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/11/160:37
 */
public class Test {
    public static void main(String[] args) {
        LfwApplicationContext a=new LfwApplicationContext("application.properties");
        System.out.println(a);
    }
}
