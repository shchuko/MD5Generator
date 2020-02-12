package shchuko.md5genlib;

import org.junit.*;
import static org.junit.Assert.*;

public class MD5GenUnitTests {
    @Test
    public void GetHashBytesEmptyInputTest() {
        byte[] expected = { -44, 29, -116, -39, -113, 0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 126 };
        byte[] actual = new MD5Gen().update(new byte[] { }).getHashBytes();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void GetHashStringEmptyInputLowerCaseTest() {
        String expected = "d41d8cd98f00b204e9800998ecf8427e";
        String actual = new MD5Gen().update(new byte[]{ }).getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void GetHashStringEmptyInputCapitalCaseTest() {
        String expected = "d41d8cd98f00b204e9800998ecf8427e".toUpperCase();
        String actual = new MD5Gen().update(new byte[]{ }).getHashHexString(true);
        assertEquals(expected, actual);
    }

    @Test
    public void GetHashStringCorrectHashValueFirstTest() {
        byte[] md5GenInput = "md5".getBytes();
        String expected = "1bc29b36f623ba82aaf6724fd3b16718";
        String actual = new MD5Gen().update(md5GenInput).getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void GetHashStringCorrectHashValueSecondTest() {
        byte[] md5GenInput = "md4".getBytes();
        String expected = "c93d3bf7a7c4afe94b64e30c2ce39f4f";
        String actual = new MD5Gen().update(md5GenInput).getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void GetHashStringCorrectHashValueThirdTest() {
        byte[] md5GenInput = "hello".getBytes();
        String expected = "5d41402abc4b2a76b9719d911017c592";
        String actual = new MD5Gen().update(md5GenInput).getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void GetHashStringCorrectHashValueFourthTest() {
        byte[] md5GenInput = "helloWorld1s".getBytes();
        String expected = "b11b235896517f8ca09a1035e0d5702b";
        String actual = new MD5Gen().update(md5GenInput).getHashHexString(false);
        assertEquals(expected, actual);
    }

    @Test
    public void GetHashStringTwiceTest() {
        MD5Gen gen = new MD5Gen();
        byte[] md5GenInputFirst = "hello".getBytes();
        String expectedFirst = "5d41402abc4b2a76b9719d911017c592";
        String actualFirst = gen.update(md5GenInputFirst).getHashHexString(false);
        assertEquals(expectedFirst, actualFirst);

        byte[] md5GenInputSecond = "helloWorld1s".getBytes();
        String expectedSecond = "b11b235896517f8ca09a1035e0d5702b";
        String actualSecond = gen.update(md5GenInputSecond).getHashHexString(false);
        assertEquals(expectedSecond, actualSecond);
    }

}
