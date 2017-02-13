package game.graphic.party;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Marge
{
    public static Bitmap createBitmap(Bitmap first, Bitmap second)
    {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }
    public static Bitmap createBitmap(Bitmap... bmps)
    {
        int width = 0;
        int height = 0;
        int total =0;
        for (Bitmap bmp:bmps)
        {
            width = Math.max(width, bmp.getWidth());
            height += bmp.getHeight();
        }

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        for (Bitmap bmp:bmps)
        {
            canvas.drawBitmap(bmp, 0, total, null);
            total += bmp.getHeight();
        }
        return result;
    }
}
