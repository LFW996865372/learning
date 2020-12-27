import com.cgb.luofenwu.spring.framework.context.LfwApplicationContext;

/**
 * @Autor:LFW
 * @Description:
 * @Date:create in 2020/11/160:37
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(Integer.valueOf(200) == Integer.valueOf(200));
        System.out.println(Integer.valueOf(100) == Integer.valueOf(100));
        System.out.println(Integer.valueOf(100) == new Integer(100));
        String a = "a";
        String b = "a";
        String c = new String("a");
        String d = new String("a");
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a.equals(c));
        System.out.println(d.equals(c));

//        LfwApplicationContext a=new LfwApplicationContext("application.properties");
//        System.out.println(a);
    }
}
