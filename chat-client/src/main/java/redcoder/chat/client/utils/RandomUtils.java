package redcoder.chat.client.utils;

import java.util.Random;

public class RandomUtils {

    private static final String[] NAMES = {"小明", "小华", "小帅", "小美", "张三", "李四", "王二", "麻子"};
    private static final Random RANDOM = new Random();

    public static String randomName() {
        int i = RANDOM.nextInt(NAMES.length);
        return NAMES[i];
    }
}
