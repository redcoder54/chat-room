package redcoder.chat.common.log;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogTest {

    private static final Logger LOGGER = Logger.getLogger(LogTest.class.getName());

    @BeforeClass
    public static void beforeAll() {
        LoggingUtils.resetLogManager();
    }

    @Test
    public void info(){
        LOGGER.log(Level.INFO, "hello");
    }
}
