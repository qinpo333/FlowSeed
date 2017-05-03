package com.cmcc.seed.utils;

import java.io.*;

/**
 * Created by kunrong on 15/5/26.
 */
public class SerializerUtil {
    public static byte[] serialize(Object o) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        byte[] buf = baos.toByteArray();
        oos.flush();
        return buf;
    }

    public static Object deserialize(byte[] bytes) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object obj = ois.readObject();
        return obj;
    }
}
