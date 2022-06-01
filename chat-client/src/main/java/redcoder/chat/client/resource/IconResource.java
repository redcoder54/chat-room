package redcoder.chat.client.resource;

import redcoder.chat.client.model.AvatarIcon;
import redcoder.chat.client.utils.SystemUtils;

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
    private static final Map<String, AvatarIcon> AVATAR_ICONS = new HashMap<>();
    private static final Map<String, ImageIcon> IMAGE_ICONS = new HashMap<>();
    private static String avatarPathPortion;

    /**
     * get image icon
     *
     * @param imageName image name
     * @return image icon
     */
    public static ImageIcon getImageIcon(String imageName) {
        ImageIcon imageIcon = IMAGE_ICONS.get(imageName);
        if (imageIcon == null) {
            URL url = IconResource.class.getResource(imageName);
            if (url == null) {
                url = IconResource.class.getClassLoader().getResource(imageName);
            }
            if (url != null) {
                imageIcon = new ImageIcon(url);
                IMAGE_ICONS.put(imageName, imageIcon);
            }
        }
        return imageIcon;
    }

    /**
     * 获取头像图标
     *
     * @param avatarName 头像名称
     * @return 头像图标
     */
    public static AvatarIcon getAvatarIcon(String avatarName) {
        AvatarIcon imageIcon = AVATAR_ICONS.get(avatarName);
        if (imageIcon == null) {
            URL url = IconResource.class.getResource(avatarName);
            if (url == null) {
                url = IconResource.class.getClassLoader().getResource(avatarName);
            }
            if (url != null) {
                imageIcon = new AvatarIcon(avatarName, new ImageIcon(url));
                AVATAR_ICONS.put(avatarName, imageIcon);
            }
        }
        return imageIcon;
    }

    public static Collection<AvatarIcon> getHeadImageIcons() {
        return AVATAR_ICONS.values();
    }

    private static void loadIcon() throws Exception {
        ClassLoader classLoader = IconResource.class.getClassLoader();
        URL url = classLoader.getResource("icons");
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
                if (f.isDirectory()) {
                    loadLocalFile(f.toURI().toURL());
                } else {
                    String name = f.getName();
                    String path = f.getAbsolutePath();
                    IMAGE_ICONS.put(name, new ImageIcon(path));
                    if (path.contains(avatarPathPortion)) {
                        AVATAR_ICONS.put(name, new AvatarIcon(name, new ImageIcon(path)));
                    }
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
                    String imageName = name.substring(name.lastIndexOf("/") + 1);
                    IMAGE_ICONS.put(imageName, new ImageIcon(resource));
                    if (name.contains(avatarPathPortion)) {
                        AVATAR_ICONS.put(imageName, new AvatarIcon(imageName, new ImageIcon(resource)));
                    }
                }
            }
        }
    }

    static {
        try {
            if (SystemUtils.isWindowsOS()) {
                avatarPathPortion = "icons\\avatar";
            } else {
                avatarPathPortion = "icons/avatar";
            }
            loadIcon();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "IconResource", e);
        }
    }
}
