package org.innereye.ms.compress;

import java.io.IOException;

/**
 * The Data Compress/UnCompress
 */
public interface Compress {

    /**
     * The Data compress.
     * 
     * @param data
     * @return
     * @throws IOException
     */
    byte[] compress(byte[] data) throws IOException;

    /**
     * The Data uncompress.
     * 
     * @param data
     * @return
     * @throws IOException
     */
    byte[] uncompress(byte[] data) throws IOException;
}
