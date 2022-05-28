package redcoder.chat.client.icon;

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

public abstract class IconResource {

    private static final Logger LOGGER = Logger.getLogger(IconResource.class.getName());
    private static final Map<String, ImageIcon> HEAD_IMG_ICONS = new HashMap<>();

    static {
        try {
            loadHeadImgIcon();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "IconResource", e);
        }
    }

    /**
     * 获取头像图标
     *
     * @param iconName 图标名称
     * @return 头像图标
     */
    public static ImageIcon getHeadImgIcon(String iconName) {
        ImageIcon imageIcon = HEAD_IMG_ICONS.get(iconName);
        if (imageIcon == null) {
            URL url = IconResource.class.getResource(iconName);
            if (url == null) {
                url = IconResource.class.getClassLoader().getResource(iconName);
            }
            if (url != null) {
                imageIcon = new ImageIcon(url);
                HEAD_IMG_ICONS.put(iconName, imageIcon);
            }
        }
        return imageIcon;
    }

    public static Collection<ImageIcon> getHeadImageIcons() {
        return HEAD_IMG_ICONS.values();
    }

    private static void loadHeadImgIcon() throws Exception {
        ClassLoader classLoader = IconResource.class.getClassLoader();
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
                    String iconName = f.getName();
                    String path = f.getAbsolutePath();
                    HEAD_IMG_ICONS.put(iconName, new ImageIcon(path));
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
                    String iconName = name.substring(name.lastIndexOf("/") + 1);
                    HEAD_IMG_ICONS.put(iconName, new ImageIcon(resource));
                }
            }
        }
    }
}
