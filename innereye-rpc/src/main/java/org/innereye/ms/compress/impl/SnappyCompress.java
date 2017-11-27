package org.innereye.ms.compress.impl;

import java.io.IOException;

import org.innereye.ms.compress.Compress;
import org.innereye.ms.compress.support.SPI;
import org.xerial.snappy.Snappy;

/**
 * The Data Compression Based on snappy.
 */
@SPI("snappy")
public class SnappyCompress implements Compress {

    @Override
    public byte[] compress(byte[] data) throws IOException {
        return Snappy.compress(data);
    }

    @Override
    public byte[] uncompress(byte[] data) throws IOException {
        return Snappy.uncompress(data);
    }
}
