package shchuko.md5genlib;

import org.junit.*;
import static org.junit.Assert.*;

public class MD5HashResultUnitTests {
    @Test
    public void getHashBytesTest() {
        MD5HashResult result = new MD5HashResult(0xd98c1dd4, 0x4b2008f, 0x980980e9, 0x7e42f8ec);
        byte[] expected = { -44, 29, -116, -39, -113, 0, -78, 4, -23, -128, 9, -104, -20, -8, 66, 126 };
        byte[] actual = result.getHashBytes();

        assertArrayEquals(expected, actual);
    }

    @Test
    public void getHashHexStringUpperCaseTest() {
        MD5HashResult result = new MD5HashResult(0x58231bb1, 0x8c7f5196, 0x35109aa0, 0x2b70d5e0);
        String expected = "b11b235896517f8ca09a1035e0d5702b".toUpperCase();
        String actual = result.getHashHexString(true);

        assertEquals(expected, actual);
    }

    @Test
    public void getHashHexStringLowerCaseTest() {
        MD5HashResult result = new MD5HashResult(0x58231bb1, 0x8c7f5196, 0x35109aa0, 0x2b70d5e0);
        String expected = "b11b235896517f8ca09a1035e0d5702b";
        String actual = result.getHashHexString(false);

        assertEquals(expected, actual);
    }
}
