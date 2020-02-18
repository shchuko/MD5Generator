package shchuko.md5genlib;

import org.junit.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class MD5GenUtilsUnitTests {
    @Test
    public void getBytesHashTest() {
        byte[] bytes = ("helloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWorld1sorld1so" +
                "rld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1so").getBytes();
        String expected = "bc2c76bd36fccf63d548d32cea02a599";
        String actual = MD5GenUtils.getBytesHash(bytes).getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void getStringHashTest() {
        String s = "helloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWorld1sorld1so" +
                "rld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1so";
        String expected = "bc2c76bd36fccf63d548d32cea02a599";
        String actual = MD5GenUtils.getStringHash(s).getHashHexString(false);
        assertEquals(expected, actual);
    }
}
