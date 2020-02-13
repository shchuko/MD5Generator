package shchuko.md5genlib;

import java.util.*;

public class MD5RollingGen extends MD5Gen {
    private LinkedList<Byte> bytesQueue = new LinkedList<>();
    private long bytesAdded = 0;

    /**
     * Reset and calculate new hash from input
     * @param input Input bytes
     * @return Reference to this object
     */
    @Override
    public MD5Gen update(byte[] input) {
        reset();
        addBytesPart(input);
        return this;
    }

    /**
     * Get hash result from added bytes. To add bytes use {@link #update(byte[])} or {@link #addBytesPart(byte[])}
     * @return MD5 hash calculation result
     */
    @Override
    public MD5HashResult getHashResult() {
        int aPrev = super.getBufA();
        int bPrev = super.getBufB();
        int cPrev = super.getBufC();
        int dPrev = super.getBufD();

        byte[] partToAdd = new byte[bytesQueue.size()];
        int i = 0;
        for (byte b : bytesQueue) {
            partToAdd[i++] = b;
        }

        int nextByteNum = super.fillWordsArrayFromBytes(partToAdd);
        nextByteNum = super.appendAlignedBitToWords(nextByteNum);
        // Need to fix bug in super class - make argument long
//        super.appendZeroPadding(bytesAdded + 1, nextByteNum);
        super.appendZeroPadding((int) (bytesAdded + 1), nextByteNum);
        appendLength(bytesAdded * 8);
        calculate();

        MD5HashResult hashResult = super.getHashResult();
        super.restoreBufferState(aPrev, bPrev, cPrev, dPrev);
        return hashResult;
    }

    /**
     * Start new calculation
     */
    public void reset() {
        resetAll();
    }

    /**
     * Add part of bytes to calculate MD5 hash
     * @param bytesPart Part of bytes
     * @return Reference to this object
     */
    public MD5RollingGen addBytesPart(byte[] bytesPart) {
        bytesAdded += bytesPart.length;

        for (byte b : bytesPart) {
            bytesQueue.add(b);
        }

        int partToAddLen = (bytesQueue.size() / 64) * 64;
        byte[] partToAdd = new byte[partToAddLen];

        int i = 0;
        Iterator<Byte> it = bytesQueue.iterator();
        while (i < partToAddLen) {
            partToAdd[i] = it.next();
            it.remove();
            ++i;
        }

        super.fillWordsArrayFromBytes(partToAdd);
        super.calculate();
        return this;
    }


    /**
     * Full reset to start new calculation
     */
    @Override
    void resetAll() {
        bytesQueue.clear();
        bytesAdded = 0;
        super.resetAll();
    }
}
