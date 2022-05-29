package redcoder.chat.core.utils;

import redcoder.chat.core.model.RcMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageSerializeUtils {

    public static byte[] serialize(RcMessage message) {
        try (ByteArrayOutputStream baout = new ByteArrayOutputStream(4096);
             ObjectOutputStream out = new ObjectOutputStream(baout)) {
            out.writeObject(message);
            return baout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("序列化失败", e);
        }
    }

    public static RcMessage deserialize(byte[] bytes) {
        try (ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bain)) {
            Object obj = in.readObject();
            return (RcMessage) obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("反序列化失败", e);
        }
    }
}
