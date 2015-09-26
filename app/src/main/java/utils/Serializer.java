package utils;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer {
    public Serializer() {
    }

    public static String serialize(Serializable obj) {
        if (obj == null) {
            return "";
        } else {
            try {
                ByteArrayOutputStream e = new ByteArrayOutputStream();
                ObjectOutputStream objStream = new ObjectOutputStream(e);
                objStream.writeObject(obj);
                objStream.close();
                return encodeBytes(e.toByteArray());
            } catch (IOException var3) {
                var3.printStackTrace();
                Log.i("Serilization Error", var3.getMessage());
                return null;
            }
        }
    }

    public static Object deserialize(String str) {
        if (str != null && str.length() != 0) {
            try {
                ByteArrayInputStream e = new ByteArrayInputStream(decodeBytes(str));
                ObjectInputStream objStream = new ObjectInputStream(e);
                return objStream.readObject();
            } catch (Exception var3) {
                Log.i("Serilization Error", var3.getMessage());
                return null;
            }
        } else {
            return null;
        }
    }

    public static Object deserializeFallback(String str) {
        if (str != null && str.length() != 0) {
            DeserializerObjectInputStream objStream = null;

            Object var3;
            try {
                ByteArrayInputStream e = new ByteArrayInputStream(decodeBytes(str));
                objStream = new DeserializerObjectInputStream(e);
                var3 = objStream.readObject();
                return var3;
            } catch (IOException var15) {
                Log.i("Serilization Error", var15.getMessage());
                var3 = null;
            } catch (ClassNotFoundException var16) {
                var16.printStackTrace();
                var3 = null;
                return var3;
            } finally {
                try {
                    objStream.close();
                } catch (IOException var14) {
                    var14.printStackTrace();
                }

            }

            return var3;
        } else {
            return null;
        }
    }

    public static String encodeBytes(byte[] bytes) {
        StringBuffer strBuf = new StringBuffer();

        for (int i = 0; i < bytes.length; ++i) {
            strBuf.append((char) ((bytes[i] >> 4 & 15) + 97));
            strBuf.append((char) ((bytes[i] & 15) + 97));
        }

        return strBuf.toString();
    }

    public static byte[] decodeBytes(String str) {
        byte[] bytes = new byte[str.length() / 2];

        for (int i = 0; i < str.length(); i += 2) {
            char c = str.charAt(i);
            bytes[i / 2] = (byte) (c - 97 << 4);
            c = str.charAt(i + 1);
            bytes[i / 2] = (byte) (bytes[i / 2] + (c - 97));
        }

        return bytes;
    }
}
