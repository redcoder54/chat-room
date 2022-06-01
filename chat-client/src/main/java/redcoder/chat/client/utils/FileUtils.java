package redcoder.chat.client.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class FileUtils {

    public static byte[] read(File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Cannot read directory");
        }

        byte[] bytes = new byte[1024];
        try (FileInputStream fin = new FileInputStream(file);
             BufferedInputStream bin = new BufferedInputStream(fin);
             ByteArrayOutputStream baos = new ByteArrayOutputStream(1024)) {
            int len;
            while ((len = bin.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read file.", e);
        }
    }
}
