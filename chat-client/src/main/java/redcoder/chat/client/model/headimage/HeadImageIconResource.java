package redcoder.chat.client.model.headimage;

import javax.swing.*;
import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class HeadImageIconResource {

    private static final Logger LOGGER = Logger.getLogger(HeadImageIconResource.class.getName());
    private static final Map<String, HeadImageIcon> HEAD_IMG_ICONS = new HashMap<>();

    static {
        try {
            loadHeadImage();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "IconResource", e);
        }
    }

    /**
     * 获取头像图标
     *
     * @param headImageName 头像名称
     * @return 头像图标
     */
    public static HeadImageIcon getHeadImage(String headImageName) {
        HeadImageIcon imageIcon = HEAD_IMG_ICONS.get(headImageName);
        if (imageIcon == null) {
            URL url = HeadImageIconResource.class.getResource(headImageName);
            if (url == null) {
                url = HeadImageIconResource.class.getClassLoader().getResource(headImageName);
            }
            if (url != null) {
                imageIcon = new HeadImageIcon(headImageName, new ImageIcon(url));
                HEAD_IMG_ICONS.put(headImageName, imageIcon);
            }
        }
        return imageIcon;
    }

    public static Collection<HeadImageIcon> getHeadImageIcons() {
        return HEAD_IMG_ICONS.values();
    }

    private static void loadHeadImage() throws Exception {
        ClassLoader classLoader = HeadImageIconResource.class.getClassLoader();
        URL url = classLoader.getResource("headimg");
        if (url == null) {
            return;
        }
        String str = url.toString();
        if (str.startsWith("file")) {
            loadLocalFile(url);
        } else if (str.startsWith("jar")) {
            loadJarFile(classLoader, url);
        } else {
            LOGGER.log(Level.WARNING, "Unknown URL[ " + str + "], we can't handle it.");
        }
    }

    private static void loadLocalFile(URL url) throws Exception {
        File file = new File(url.toURI());
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    String name = f.getName();
                    String path = f.getAbsolutePath();
                    HEAD_IMG_ICONS.put(name, new HeadImageIcon(name, new ImageIcon(path)));
                }
            }
        }
    }

    private static void loadJarFile(ClassLoader classLoader, URL url) throws Exception {
        JarURLConnection connection = (JarURLConnection) url.openConnection();
        JarFile jarFile = connection.getJarFile();
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            String name = entries.nextElement().getName();
            if (name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".jpg")) {
                URL resource = classLoader.getResource(name);
                if (resource != null) {
                    String headImageName = name.substring(name.lastIndexOf("/") + 1);
                    HEAD_IMG_ICONS.put(headImageName, new HeadImageIcon(headImageName, new ImageIcon(resource)));
                }
            }
        }
    }
}
