package com.harsha.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class Utility {

    /**
     * download the image from the url to bitmap
     * @param src
     * @return
     */
    public static byte[] getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            return bArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
