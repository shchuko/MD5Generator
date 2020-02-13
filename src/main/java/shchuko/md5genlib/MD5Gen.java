package shchuko.md5genlib;

import java.util.ArrayList;

/**
 * MD5 hash generator
 */
public class MD5Gen {
    /** MD5 calculation buffer A initialisation value */
    private static final int A_CONST = 0x67452301;
    /** MD5 calculation buffer B initialisation value */
    private static final int B_CONST = 0xefcdab89;
    /** MD5 calculation buffer C initialisation value */
    private static final int C_CONST = 0x98badcfe;
    /** MD5 calculation buffer D initialisation value */
    private static final int D_CONST = 0x10325476;

    /** Values of noise function 2^32*abs(sin(i)), i : 1..64 */
    private static final int[] T_VALUES = {
            0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee,
            0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
            0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be,
            0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
            0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa,
            0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
            0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed,
            0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
            0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c,
            0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
            0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05,
            0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
            0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039,
            0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
            0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1,
            0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391
    };

    /** Left rotate values for algorithm */
    private static final int[] S_VALUES = {
            7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22,
            5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20, 5,  9, 14, 20,
            4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23,
            6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21
    };

    /** Input data in MD5 calculation format (with appended 1-bit, padding and length) */
    private ArrayList<Integer> words = new ArrayList<>();

    /** MD5 calculation buffer A */
    private int a = A_CONST;
    /** MD5 calculation buffer B */
    private int b = B_CONST;
    /** MD5 calculation buffer C */
    private int c = C_CONST;
    /** MD5 calculation buffer D */
    private int d = D_CONST;


    /**
     * Generate MD5-hash for input bytes
     * @param input Input bytes
     * @return Reference for this MD5Gen
     */
    public MD5Gen update(byte[] input) {
        resetAll();
        fillWordsArrayFromBytes(input);
        appendLength(input.length * 8);
        calculate();
        return this;
    }

    /**
     * Get hash result as {@link MD5HashResult} object
     * @return {@link MD5HashResult} object
     */
    public MD5HashResult getHashResult() {
        return new MD5HashResult(a, b, c, d);
    }


    /**
     * Get generated MD5-hash value in bytes representation. To generate hash invoke {@link #update(byte[])}
     * @deprecated Use {@link #getHashResult()}.{@link MD5HashResult#getHashBytes() getHashBytes()} instead
     * @return MD5-hash value bytes
     */
    @Deprecated
    public byte[] getHashBytes() {
        return getHashResult().getHashBytes();
    }

    /**
     * Get generated MD5-hash in HEX format.
     * To generate hash invoke {@link #update(byte[])}
     * @deprecated Use {@link #getHashResult()}.{@link MD5HashResult#getHashHexString(boolean) getHashHexString(boolean)}
     * instead
     * @param upperCase True: capital case HEX, false: lower case HEX
     * @return String with HEX hash value
     */
    @Deprecated
    public String getHashHexString(boolean upperCase) {
        return getHashResult().getHashHexString(upperCase);
    }

    /**
     * Appends byte to {@link #words} list
     * @param byteVal Byte to append
     * @param byteNum Byte place number in int32 variable
     * @return New value for byteNum (passed as argument)
     */
    private int appendByteToWords(byte byteVal, int byteNum) {
        if (byteNum == 0) {
            words.add(byteVal & 0xFF);
        } else {
            int lastIndex = words.size() - 1;
            int value = words.get(lastIndex) | ((byteVal & 0xFF) << 8 * byteNum);
            words.set(lastIndex, value);
        }

        return byteNum == 3 ? 0 : byteNum + 1;
    }

    /**
     * Fill {@link #words} list from bytes array, add zero-padding (part of MD5 algorithm)
     * @param bytes Input bytes
     */
    private void fillWordsArrayFromBytes(byte[] bytes) {
        // Byte of word we're filling next [3..0]. Starting from the 3rd
        int byteNum = 0;
        // Storing input data
        for (byte b : bytes) {
            byteNum = appendByteToWords(b,
                    byteNum);
        }

        // Adding 1-bit to the end, padded with zeroes to full byte
        final byte ONE_PADDED_BIT = (byte) 0b10000000;
        byteNum = appendByteToWords(ONE_PADDED_BIT, byteNum);

        // Adding zero padding
        long bytesFilled = bytes.length + 1;
        final byte ZERO_BYTE = 0;
        while (bytesFilled < (448 / 8) || (bytesFilled - (448 / 8)) % 512 != 0) {
            byteNum = appendByteToWords(ZERO_BYTE, byteNum);
            ++bytesFilled;
        }
    }

    /**
     * Append length value to {@link #words} (part of MD5 algorithm)
     * @param length INT64 length value
     */
    private void appendLength(long length)  {
        words.add((int) length);
        words.add((int) (length >>> (4 * 8)));
    }

    /**
     * Reset calculation buffers {@link #a}, {@link #b}, {@link #c}, {@link #d}
     */
    private void resetBuffers() {
        a = A_CONST;
        b = B_CONST;
        c = C_CONST;
        d = D_CONST;
    }

    /**
     * Reset calculation buffers {@link #a}, {@link #b}, {@link #c}, {@link #d}, empty {@link #words} list.
     * Prepare for new calculation
     */
    private void resetAll() {
        resetBuffers();
        words.clear();
    }

    /**
     * Calculate MD5 hash from {@link #words}. Result stored in {@link #a}, {@link #b}, {@link #c}, {@link #d}.
     * (part of MD5 algorithm)
     */
    private void calculate() {
        for (int xBegin = 0; xBegin < words.size(); xBegin += 16) {
            int aa = a;
            int bb = b;
            int cc = c;
            int dd = d;

            int funResult;
            byte k;
            for (byte i = 0; i < 64; ++i) {
                if (i < 16) {
                    // Round 1
                    funResult = funF(b, c, d);
                    k = i;
                } else if (i < 32) {
                    // Round 2
                    funResult = funG(b, c, d);
                    k = (byte) ((5 * i + 1) & 0xf);
                } else if (i < 48) {
                    // Round 3
                    funResult = funH(b, c, d);
                    k = (byte) ((3 * i + 5) & 0xf);
                } else {
                    // Round 4
                    funResult = funI(b, c, d);
                    k = (byte) ((7 * i) & 0xf);
                }

                int valueToRotate = a + funResult + words.get(xBegin + k) + T_VALUES[i];
                int rotateResult = Integer.rotateLeft(valueToRotate, S_VALUES[i]);
                a = d;
                d = c;
                c = b;
                b += rotateResult;
            }

            a += aa;
            b += bb;
            c += cc;
            d += dd;
        }
    }

    /**
     * Special 'F' function for MD5 calculation algorithm
     * @param x First argument
     * @param y Second argument
     * @param z Third argument
     * @return 'F' function result
     */
    private static int funF(int x, int y, int z) {
        return (x & y) | ((~x) & z);
    }

    /**
     * Special 'G' function for MD5 calculation algorithm
     * @param x First argument
     * @param y Second argument
     * @param z Third argument
     * @return 'G' function result
     */
    private static int funG(int x, int y, int z) {
        return (x & z) | ((~z) & y);
    }

    /**
     * Special 'H' function for MD5 calculation algorithm
     * @param x First argument
     * @param y Second argument
     * @param z Third argument
     * @return 'H' function result
     */
    private static int funH(int x, int y, int z) {
        return x ^ y ^ z;
    }

    /**
     * Special 'I' function for MD5 calculation algorithm
     * @param x First argument
     * @param y Second argument
     * @param z Third argument
     * @return 'I' function result
     */
    private static int funI(int x, int y, int z) {
        return y ^ ((~z) | x);
    }
}
