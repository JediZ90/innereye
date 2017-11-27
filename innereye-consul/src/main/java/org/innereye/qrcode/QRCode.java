package org.innereye.qrcode;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCode {

    private static final Path BASE_IMAGE_PATH = Paths.get("src/test/resources/qrcode/");

    private static BufferedImage loadImage(String fileName) throws IOException {
        Path file = BASE_IMAGE_PATH.resolve(fileName);
        if (!Files.exists(file)) {
            file = Paths.get("core/").resolve(BASE_IMAGE_PATH).resolve(fileName);
        }
        return ImageIO.read(file.toFile());
    }

    private static BitMatrix createMatrixFromImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);
        BitMatrix matrix = new BitMatrix(width, height);
        for (int y = 0;y < height;y++) {
            for (int x = 0;x < width;x++) {
                int pixel = pixels[y * width + x];
                int luminance = (306 * ((pixel >> 16) & 0xFF) + 601 * ((pixel >> 8) & 0xFF)
                        + 117 * (pixel & 0xFF)) >> 10;
                if (luminance <= 0x7F) {
                    matrix.set(x, y);
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) throws Exception {
        int bigEnough = 256;
        Writer writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode("你好", BarcodeFormat.QR_CODE, bigEnough, bigEnough, null);
        Path tempFile = Paths.get("/Users/zhangbaohao/mygoo/slothos/innereye/innereye-consul/src/test/resources/qrcode/test1.png");
        MatrixToImageWriter.writeToPath(matrix, "png", tempFile);
        // BufferedImage newImage = ImageIO.read(tempFile.toFile());
        // int tooSmall = 20;
        // matrix = writer.encode("http://www.google.com/",
        // BarcodeFormat.QR_CODE, tooSmall, tooSmall, null);
        // int strangeWidth = 500;
        // int strangeHeight = 100;
        // matrix = writer.encode("http://www.google.com/",
        // BarcodeFormat.QR_CODE, strangeWidth, strangeHeight, null);
    }

    private static void doTestFormat(String format, MatrixToImageConfig config) throws IOException {
        int width = 2;
        int height = 3;
        BitMatrix matrix = new BitMatrix(width, height);
        matrix.set(0, 0);
        matrix.set(0, 1);
        matrix.set(1, 2);
        BufferedImage newImage;
        Path tempFile = Files.createTempFile(null, "." + format);
        try {
            MatrixToImageWriter.writeToPath(matrix, format, tempFile, config);
            newImage = ImageIO.read(tempFile.toFile());
        }
        finally {
            Files.delete(tempFile);
        }
        for (int y = 0;y < height;y++) {
            for (int x = 0;x < width;x++) {
                int expected = matrix.get(x, y) ? config.getPixelOnColor() : config.getPixelOffColor();
                int actual = newImage.getRGB(x, y);
            }
        }
    }
}
