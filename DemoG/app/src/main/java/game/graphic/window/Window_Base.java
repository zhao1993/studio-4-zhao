package game.graphic.window;

/*作所有窗口的父类*/
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import game.graphic.party.Direction;
import game.graphic.AssetsFactory;
import android.graphics.Matrix;
import game.graphic.party.Marge;

public class  Window_Base 
{
    public int x;
    public int y;
    public int width;
    public int height;
    protected Bitmap winSkin;
    protected Path path;
    protected Paint paint;
    protected Paint stokenPaint;

    public Window_Base(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        winSkin = AssetsFactory.getWinSkin("Window");
        winSkin = Marge.createBitmap(winSkin, winSkin, winSkin, winSkin, winSkin, winSkin, winSkin, winSkin, winSkin, winSkin);
        init();
    }

    private void init()
    {
        path = new Path();
        path.moveTo(300, 500);
        path.lineTo(120, 200);
        path.lineTo(800, 800);
        path.close();

        paint = new Paint();
        paint.setTextSize(30);
        paint.setARGB(205, 0, 0, 255);
        stokenPaint = new Paint();
        stokenPaint.setStyle(Paint.Style.STROKE);
        stokenPaint.setARGB(255, 240, 240, 240);
        stokenPaint.setStrokeWidth(6);
    }
    public void locateOf(Window_Base win, Direction dir)
    {
        switch (dir)
        {
            case UP:
                y = win.y - height - 1;
                break;
            case DOWN:
                y = win.y + win.height + 1;
                break;
            case LEFT:
                x = win.x - width - 1;
                break;
            case RIGHT:
                x = win.x + win.width + 1;
                break;
        }
    }
    public void draw(Canvas canvas)
    {
        //canvas.drawRect(new RectF(x, y, x + width, y + height), paint);
        //canvas.drawRect(new RectF(x, y, x + width, y + height), stokenPaint);
        //Matrix matrix = new Matrix();
        //设置放大倍数
        //matrix.setScale(1, -1);
        //设置位置
        //-matrix.setTranslate(10, 500);
        //设置旋转角度。后面两参数好像无用
        // matrix.setRotate(5f, 5, 5);
        //设置旋转和等比放大 
        //matrix.setSinCos(0, 6);

        //matrix.postTranslate(0, winSkin.getHeight());
        //matrix.mapPoints(new float[]{10f,30f,50f,50f});
        canvas.drawBitmap(winSkin, 10, 20, stokenPaint);
        // canvas.drawPath(path, paint);
    }
}
