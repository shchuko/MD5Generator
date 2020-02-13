package shchuko.md5genlib;

import java.util.*;

public class MD5RollingGen extends MD5Gen {
    private LinkedList<Byte> bytesQueue = new LinkedList<>();
    private long bytesAdded = 0;
    private MD5HashResult resultCache = null;

    public void reset() {
        resetAll();
    }

    public MD5RollingGen addBytesPart(byte[] bytes) {
        // TODO - remove if check
        if (resultCache != null) {
            throw new RuntimeException("Functionality not implemented yet");
        }
        bytesAdded += bytes.length;

        for (byte b : bytes) {
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
     * 
     * @return
     */
    @Override
    public MD5HashResult getHashResult() {
        // TODO - Need to fix bug in super class
//        int aPrev = super.getABuf();
//        int bPrev = super.getBBuf();
//        int cPrev = super.getCBuf();
//        int dPrev = super.getDBuf();

        if (resultCache != null) {
            return resultCache;
        }
        byte[] partToAdd = new byte[bytesQueue.size()];
        int i = 0;
        for (byte b : bytesQueue) {
            partToAdd[i++] = b;
        }

        int nextByteNum = super.fillWordsArrayFromBytes(partToAdd);
        nextByteNum = super.appendAlignedBitToWords(nextByteNum);
        // TODO - Need to fix bug in super class
//        super.appendZeroPadding(bytesAdded + 1, nextByteNum);
        super.appendZeroPadding((int) (bytesAdded + 1), nextByteNum);
        appendLength(bytesAdded * 8);
        calculate();

        resultCache = super.getHashResult();

        // TODO - Need to fix bug in super class
//        super.restoreState(aPrev, bPrev, cPrev, dPrev);

        return resultCache;
    }

    @Override
    void resetAll() {
        bytesQueue.clear();
        bytesAdded = 0;
        resultCache = null;
        super.resetAll();
    }
}
