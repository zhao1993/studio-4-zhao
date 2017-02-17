package game.graphic;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import game.graphic.party.Direction;
import game.graphic.sprite.Sprite;
import game.graphic.sprite.SpriteFactory;
import game.operate.LogicThread;
import game.operate.SpriteEvent;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable
{
    private SurfaceHolder gHolder;
    private Context gContext;
    private Canvas gCanvas;
    /*画面的宽高*/
    private static int scene_width;
    private static int scene_height;
    /*玩家*/
    public static Sprite player;
    /*按钮*/
    public static Button button ;
    /*测试文本*/
    public static String str = "";
    /*运行标记*/
    public static boolean run;
    /*是否应许长按*/
    public static boolean longPress;
    /*画面与逻辑的交替执行标志*/
    public static boolean alternate;
    /*画面逻辑交替的锁资源*/
    public static String key =new String();


    public GameView(Context gContext)
    {
        this(gContext, null);
    }
    public GameView(Context gContext, AttributeSet aSet)
    {
        super(gContext);
        this.gContext = gContext;
        gHolder = this.getHolder();
        gHolder.addCallback(this);
    }
    /*进入按钮事件处理*/
    public static boolean mTouch(Button btn, MotionEvent mEvent)
    {  
        // TODO: Implement this method
        /*记录按钮是否已经被释放*/
        boolean released = false ;
        if (mEvent.getAction() == mEvent.ACTION_DOWN)
        {
            button = btn;
        }
        if (mEvent.getAction() == mEvent.ACTION_UP)
        {
            released = true;
        }
        if (mEvent.getAction() == mEvent.ACTION_MOVE)
        {

        }
        if (!longPress && !released)
            button = btn;
        return false;
    }

    /*画面创建*/
    @Override
    public void surfaceCreated(SurfaceHolder p1)
    {
        // TODO: Implement this method
        run = true;
        /**初始化资源*/
        AssetsFactory.initialization(gContext);
        /**初始化参数*/
        init();
        player = SpriteFactory.getPlayer(6, "people008");
        player.setSpeed(4);
        SpriteFactory.debugCreateSprite(1);
        
        /*逻辑线程*/
        new LogicThread().start();
        /*绘制线程*/
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4)
    {
        // TODO: Implement this method
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder p1)
    {
        // TODO: Implement this method
        run = false;
        System.exit(0);
    }
    /*绘制线程*/
    @Override
    public void run()
    {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        while (GameView.run)
        {
            synchronized (GameView.key)
            {
                if (GameView.alternate)
                {
                    /*绘制代码*/
                    gCanvas = gHolder.lockCanvas();
                    long start = System.currentTimeMillis();
                    if (null != gCanvas)
                    {
                        gCanvas.drawColor(Color.GREEN);
                        gCanvas.drawText(scene_width + ";" + scene_height, 300, 500, paint);
                        Scene.draw(gCanvas);
                        int lengt0 = str.length();
                        for (int i = 0;i < 5;i++)
                            gCanvas.drawText(str.substring(i * 30 >= lengt0 ?lengt0: i > 0 ?i * 30: 0, i * 30 + 30 > lengt0 ?lengt0: i * 30 + 30), 100, 100 + 40 * i, paint);
                    } 
                    gHolder.unlockCanvasAndPost(gCanvas);
                    long end = System.currentTimeMillis();
                    if (end - start < 60)
                    {
                        try
                        {
                            Thread.sleep(60 - (end - start));
                        }
                        catch (InterruptedException e)
                        {

                        }
                    }
                    GameView.alternate = false;
                    GameView.key.notifyAll();
                }
                else
                {
                    try
                    {
                        GameView.key.wait();
                    }
                    catch (InterruptedException e)
                    {

                    }
                }
            }
        }
    }
    /**参数初始化*/
    private void init()
    {
        scene_width = super.getWidth();
        scene_height = super.getHeight();
    }
    /**获取画面的宽高*/
    public static  int width()
    {
        return scene_width;
    }
    public static int height()
    {
        return scene_height;
    }
}
