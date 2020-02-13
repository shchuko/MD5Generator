package shchuko.md5genlib;

import org.junit.*;
import static org.junit.Assert.*;

public class MD5RollingGenUnitTests {

    @Test
    public void smallSingleInputTest() {
        byte[] md5GenInput = "md4".getBytes();
        String expected = "c93d3bf7a7c4afe94b64e30c2ce39f4f";
        String actual = new MD5RollingGen().addBytesPart(md5GenInput).getHashResult().getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void bigSingleInputTest() {
        byte[] md5GenInput = ("helloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWorld1sorld1so" +
                "rld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1so").getBytes();
        String expected = "bc2c76bd36fccf63d548d32cea02a599";
        String actual = new MD5RollingGen().addBytesPart(md5GenInput).getHashResult().getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void smallPartedInputTest() {
        byte[] inputFirstPart = "md4".getBytes();
        byte[] inputSecondPart = "md5".getBytes();

        String expected = "79c29032893e125ce2c97feb2d68571f";
        String actual = new MD5RollingGen()
                .addBytesPart(inputFirstPart)
                .addBytesPart(inputSecondPart)
                .getHashResult()
                .getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void bigPartedInputTest() {
        byte[] inputFirstPart = "helloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloWhelloW".getBytes();
        byte[] inputSecondPart = "orld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1sorld1so".getBytes();

        String expected = "bc2c76bd36fccf63d548d32cea02a599";
        String actual = new MD5RollingGen()
                .addBytesPart(inputFirstPart)
                .addBytesPart(inputSecondPart)
                .getHashResult()
                .getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void resetTest() {
        MD5RollingGen gen = new MD5RollingGen();
        byte[] md5GenInputFirst = "hello".getBytes();
        String expectedFirst = "5d41402abc4b2a76b9719d911017c592";
        String actualFirst = gen.addBytesPart(md5GenInputFirst).getHashResult().getHashHexString(false);
        assertEquals(expectedFirst, actualFirst);

        gen.reset();

        byte[] md5GenInputSecond = "helloWorld1s".getBytes();
        String expectedSecond = "b11b235896517f8ca09a1035e0d5702b";
        String actualSecond = gen.addBytesPart(md5GenInputSecond).getHashResult().getHashHexString(false);
    }
}
