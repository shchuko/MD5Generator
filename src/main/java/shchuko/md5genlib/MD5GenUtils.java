package shchuko.md5genlib;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Additional utilities to generate MD5 hash
 */
public class MD5GenUtils {
    /**
     * Generate hash from byte[]
     * @param bytes Input bytes to hash
     * @return Hash value
     */
    public static MD5HashResult getBytesHash(byte[] bytes) {
        return new MD5Gen().update(bytes).getHashResult();
    }

    /**
     * Generate hash from String
     * @param s Input string
     * @return Hash value
     */
    public static MD5HashResult getStringHash(String s) {
        return new MD5Gen().update(s.getBytes()).getHashResult();
    }

    /**
     * Generate hash from file by path
     * @param path File to calculate hash from
     * @return Hash result
     * @throws IOException If file couldn't be opened
     */
    public static MD5HashResult getHashOfFile(Path path) throws IOException {
        // 32 MiB chunk size max
        final int BUFFER_SIZE = 1024 * 1024 * 32;

        MD5RollingGen gen = new MD5RollingGen();

        FileInputStream fis = new FileInputStream(path.toFile());
        byte[] buffer = new byte[BUFFER_SIZE];
        int readSize;
        while ((readSize = fis.read(buffer)) > 0){
            if (readSize == buffer.length) {
                gen.addBytesPart(buffer);
            } else {
                gen.addBytesPart(Arrays.copyOf(buffer, readSize));
            }
        }
        fis.close();

        return gen.getHashResult();
    }


}
