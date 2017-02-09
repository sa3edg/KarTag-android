package com.kartag.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

public class ImageUtils {

	public static void displayImage(String imageData, ImageView imageView)
    {
		byte[] data = Base64.decode(imageData, Base64.DEFAULT);
		Bitmap bmp=BitmapFactory.decodeByteArray(data, 0, data.length);
        if(bmp!=null)
        {
            imageView.setImageBitmap(bmp);
        }
    } 
}
