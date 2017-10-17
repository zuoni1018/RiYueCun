package com.zuoni.qrcode.zxing;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * @类功能说明: 生成二维码图片示例
 */
public class CreateQRImageTest {

    private int QR_WIDTH = 200, QR_HEIGHT = 200;//长宽

    /**
     * @return void
     * @throws
     * @方法功能说明: 生成二维码图片, 实际使用时要初始化sweepIV, 不然会报空指针错误
     * @参数: @param url 要转换的地址或字符串,可以是中文
     */

    //要转换的地址或字符串,可以是中文
    public Bitmap createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;//设置颜色
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;//设置点的颜色
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);

            return bitmap;
            //显示到一个ImageView上面
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
