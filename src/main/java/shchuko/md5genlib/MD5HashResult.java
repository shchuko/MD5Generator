package shchuko.md5genlib;

public class MD5HashResult {
    /** MD5 calculation buffer A result */
    private int a;
    /** MD5 calculation buffer B result */
    private int b;
    /** MD5 calculation buffer C result */
    private int c;
    /** MD5 calculation buffer D result */
    private int d;

    /** Hash bytes representation cache */
    private byte[] bytesRepresentationCache = null;
    /** Hash hex string representation cache */
    private String stringHexRepresentationCache = null;

    /**
     * Create hash result from buffers values
     * @param aBuf A buffer value
     * @param bBuf B buffer value
     * @param cBuf C buffer value
     * @param dBuf D buffer value
     */
    MD5HashResult(int aBuf, int bBuf, int cBuf, int dBuf) {
        a = aBuf;
        b = bBuf;
        c = cBuf;
        d = dBuf;
    }

    /**
     * Get generated MD5-hash value in bytes representation.
     * @return MD5-hash value bytes
     */
    public byte[] getHashBytes() {
        if (bytesRepresentationCache != null) {
            return bytesRepresentationCache;
        }

        bytesRepresentationCache = new byte[16];
        for (int i = 0; i < 4; ++i) {
            bytesRepresentationCache[i] = (byte) (a >>> 8 * i);
            bytesRepresentationCache[i + 4] = (byte) (b >>> 8 * i);
            bytesRepresentationCache[i + 8] = (byte) (c >>> 8 * i);
            bytesRepresentationCache[i + 12] = (byte) (d >>> 8 * i);
        }
        return bytesRepresentationCache;
    }

    /**
     * Get generated MD5-hash in HEX format.
     * @param upperCase True: capital case HEX, false: lower case HEX
     * @return String with HEX hash value
     */
    public String getHashHexString(boolean upperCase) {
        if (stringHexRepresentationCache != null) {
            return stringHexRepresentationCache;
        }

        if (bytesRepresentationCache == null) {
            getHashBytes();
        }

        StringBuilder sb = new StringBuilder();

        String formatValue = upperCase ? "%02X" : "%02x";
        for (byte aByte : bytesRepresentationCache) {
            sb.append(String.format(formatValue, aByte & 0xFF));
        }

        stringHexRepresentationCache = sb.toString();
        return stringHexRepresentationCache;
    }
}
