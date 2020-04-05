package ch.versusvirus.reddrop.logic;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class BarcodeGenerator {

    /* Usage example:
        BarcodeGenerator bcg = new BarcodeGenerator();

        Bitmap code = bcg.generateBitmap("123456");
        ImageView qr_image = (ImageView) findViewById(R.id.imageView);
        qr_image.setImageBitmap(code);
     */

    private BarcodeFormat format;

    public BarcodeGenerator() {
        format = BarcodeFormat.CODABAR;
    }

    public BarcodeGenerator(BarcodeFormat format) {
        this.format = format;
    }

    public Bitmap generateBitmap(String value_to_encode) {
        return generateBitmap(value_to_encode, 200, 200);
    }

    public Bitmap generateBitmap(String value_to_encode, int width, int height) {
        return generateBitmap(value_to_encode, width, height, Color.BLACK, Color.WHITE);
    }

    public Bitmap generateBitmap(String value_to_encode, int width, int height, int fgColor, int bgColor) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        BitMatrix bitMatrix = null;
        try {
            bitMatrix = multiFormatWriter.encode(value_to_encode, BarcodeFormat.CODABAR, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
        int bmHeight = bitMatrix.getHeight();
        int bmWidth = bitMatrix.getWidth();
        Bitmap bmp = Bitmap.createBitmap(bmWidth, bmHeight, Bitmap.Config.RGB_565);
        for (int x = 0; x < bmWidth; x++) {
            for (int y = 0; y < bmHeight; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? fgColor : bgColor);
            }
        }
        return bmp;
    }

}
