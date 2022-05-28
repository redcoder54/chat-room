package redcoder.chat.common.utils;

import redcoder.chat.common.model.ChatMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MessageSerializeUtils {

    public static byte[] serialize(ChatMessage message) {
        try (ByteArrayOutputStream baout = new ByteArrayOutputStream(4096);
             ObjectOutputStream out = new ObjectOutputStream(baout)) {
            out.writeObject(message);
            return baout.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("序列化失败", e);
        }
    }

    public static ChatMessage deserialize(byte[] bytes) {
        try (ByteArrayInputStream bain = new ByteArrayInputStream(bytes);
             ObjectInputStream in = new ObjectInputStream(bain)) {
            Object obj = in.readObject();
            return (ChatMessage) obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("反序列化失败", e);
        }
    }
}
