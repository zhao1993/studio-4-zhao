package game.graphic;
import android.graphics.Paint;

public class Paint
{
    public static Paint normalPaint(float size)
    {
        Paint p = paint();
        p.setTextSize(size);
        return p;
    }
    public static Paint paint()
    {
        return new Paint();
    }
    public static Paint alphaPaint(int alpha)
    {
        Paint p = paint();
        p.setAlpha(alpha);
        return p;
    }
}
