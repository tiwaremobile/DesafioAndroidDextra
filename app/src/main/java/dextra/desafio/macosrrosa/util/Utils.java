package dextra.desafio.macosrrosa.util;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

/**
 * Created by marcos.fael on 11/01/2016.
 */
public class Utils {

    public static float pxToDp(final float px, Context c) {
        return px / c.getResources().getDisplayMetrics().density;
    }

    public static float dpTopx(final float dp, Context c) {
        return dp * c.getResources().getDisplayMetrics().density;
    }

    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage, Context c) {
        int targetWidth = (int) dpTopx(50, c);
        int targetHeight = (int) dpTopx(50, c);

        Bitmap targetBitmap = Bitmap.createBitmap(
                targetWidth,
                targetHeight,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
}
