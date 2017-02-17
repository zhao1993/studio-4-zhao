package game.graphic.sprite;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import game.graphic.AssetsFactory;
import game.graphic.GameView;
import game.graphic.party.Delection;
import game.graphic.party.Direction;
import game.operate.SpriteEvent;
public  class Sprite
{
    /*精灵id*/
    private int id;
    /*精灵是否是个npc精灵默认是true*/
    /*只允许一个false存在*/
    private boolean isNPC = true;
    /*精灵的事件*/
    private SpriteEvent spEvent;
    /*精灵裁剪宽高*/
    private int width = 32;
    private int height = 32;
    /*精灵绘制宽高 (同时作用于精灵移动一步的最大距离)*/
    public static final int WIDTH = 32;   //64/4
    public static final int HEIGHT = 32;
    /*精灵坐标*/
    private int x;
    private int y;
    /*精灵行走速度 默认为4 最佳速度 为绘制宽高的1/8*/
    private int speed = 4;
    /*精灵移动的距离*/
    private int distance = 0;
    /*精灵移动状态标记*/
    private boolean isMove;
    /*原地踏步标记*/
    private boolean isFixed ;
    /*精灵方向*/
    private Direction dir = Direction.UP;
    /*循环播放的间隔 ms/60 默认300 屏幕刷新5次更新一次动画*/
    private final static long INTERVAL = 256;
    /*循环播放的初始计时*/
    private long start;
    /*精灵的心走位图*/
    private Bitmap bitmap ;
    /*循环播放的精灵横向帧 最大2*/
    private int count;
    /*精灵是否为障碍物 default true*/
    private boolean isBlock = true;
    /*精灵是否可见 default true*/
    private boolean isVisible = true;

    /*DEBUG参数区临*/
    private Paint debug_p=new Paint();

    private Sprite()
    {

    }
    /*初始化一个指定id,精灵位图文件的精灵*/
    private Sprite(int id, String name)
    {
        this.id = id; 
        bitmap = AssetsFactory.getBitmap4Sprite(name);
        if (bitmap == null)
            bitmap = AssetsFactory.get8Bitmap4Sprite(name);
        start = System.currentTimeMillis();
        setPosition(0, 0);
        debug();
    }
    /*测试数据区域*/
    /*对测试参数初始化等*/
    public void debug()
    {
        debug_p = new Paint();
        debug_p.setTextSize(30);
        debug_p.setAlpha(180);
    }

    /*设置精灵是否为障碍物false时将不加入Delection*/
    public void setBlock(boolean isBlock)
    {
        this.isBlock = isBlock;
    }
    /*获取当前精灵是否是block*/
    public boolean isBlock()
    {
        return isBlock;
    }
    /*设置精灵是否可见*/
    public void setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }
    /*获取当前精灵是否可见*/
    public boolean isVisible()
    {
        return isVisible;
    }
    /*无参数针对npc*/
    public void updatePosition()
    {
        if (isNPC && spEvent == null)
        {
            setEvent(null);
        }
        /*同样需要非移动状态时才能获取*/
        if (isNPC  && !isMove && !isFixed)
        {
            /*获取下一个路线*/
            /*如果给如的方向为null则不移动(原地走动)*/
            setPosition(spEvent.nextDirection());       
        }
        else
        {
            /*如果被非npc调用则不动*/
        }
        /** 移动/**/
        move();
    }
    /*带参数的是针对player*/
    public void updatePosition(Direction dir)
    {
        if (isNPC)
        {
            /*如果是npc角色调用则进入npc处理方法*/
            updatePosition();
        }
        else
        {
            setPosition(dir);
        }
        /*移动*/
        move();
    }
    /**/
    private void setPosition(Direction dir)
    {

        /*移动标记没开启说明精灵移动完成进行新的计算*/
        if (dir == null)
        {
            isMove = true;
            isFixed = true;
            return;
        }
        /*不是正在移动的状态时*/
        else if (!isMove)
        {     
            /*无论是否可以移动都改变精灵方向*/
            this .dir = dir;             
            /*检测精灵是否可以移动*/
            if (Delection.canMove(this))
            {
                /*可以移动则开启移动标记*/
                isMove = true;
            }
            /*不论精灵是否可以移动*/
            /*如果精灵是障碍物为精灵添加修正碰撞*/
            if (isBlock)
            {
                Delection.record4Sprite(this);
                
            }
        }

    }
    //下左右上
    public void drawSprite(Canvas canva)
    {

        frameAnimation(canva);
    }
    /*移动*/
    private void move()
    {

        /*是移动状态时*/
        if (isMove)
        {
            if (!isFixed)
            {
                switch (dir)
                {
                    case UP:
                        y = y - speed;
                        break;
                    case DOWN:
                        y = y + speed;
                        break;
                    case LEFT:
                        x = x - speed;
                        break;
                    case RIGHT:
                        x = x + speed;
                        break;
                }
            }
            distance += speed;
            switch (dir)
            {
                case UP:
                case DOWN:       
                    if (distance >= Sprite.HEIGHT)
                    {
                        /*偏移用来纠正坐标的不正确计算*//*原地踏步是否*/
                        int skewing =!isFixed ?distance - Sprite.HEIGHT: 0;
                        if (dir == dir.UP)
                            y += skewing;
                        else if (dir == dir.DOWN)
                            y -= skewing;
                        /*移动完成,清除移动距离和状态标记*/
                        distance = 0;
                        isMove = false;
                        isFixed = false;
                    }
                    break;
                case LEFT:
                case RIGHT:
                    if (distance >= Sprite.WIDTH)
                    {
                        int skewing = !isFixed ? distance - Sprite.WIDTH: 0;
                        if (dir == dir.LEFT)
                            x += skewing;
                        else if (dir == dir.RIGHT)
                            x -= skewing;
                        distance = 0;
                        isMove = false; 
                        isFixed = false;
                    }
                    break;
            }    

        }
    }
    /*根据方向坐标循环播放*/
    private void frameAnimation(Canvas canva)
    {        
        long end = System.currentTimeMillis();
        if ((end - start) >= this.INTERVAL)
        {
            start = System.currentTimeMillis();
            if (++count > 2)
                count = 0;
        }
        Rect rct = null;
        Rect dst = null;

        switch (dir)
        {
                /*下左右上*/
            case UP:
                rct = new Rect(count * width, 3 * height, count * width + width, 3 * height + height);
                dst = new Rect(x, y, x + Sprite.WIDTH, y + Sprite.HEIGHT);
                break;
            case DOWN:
                rct = new Rect(count * width, 0 * height, count * width + width, 0 * height + height);
                dst = new Rect(x, y, x + Sprite.WIDTH, y + Sprite.HEIGHT);
                break;
            case LEFT:
                rct = new Rect(count * width, 1 * height, count * width + width, 1 * height + height);
                dst = new Rect(x, y, x + Sprite.WIDTH, y + Sprite.HEIGHT);
                break;
            case RIGHT:
                rct = new Rect(count * width, 2 * height, count * width + width, 2 * height + height);
                dst = new Rect(x, y, x + Sprite.WIDTH, y + Sprite.HEIGHT);
                break;
        }
        canva.save();
        if (!isNPC)
        {
            canva.drawText(Integer.toString(count) + isNPC, 720, 50, debug_p);
            canva.drawText(Long.toString((end - start) % this.INTERVAL), 650, 50, debug_p);
            canva.drawText(x + "==" + y, 800, 50, debug_p);
        }
        else
        {
            canva.drawText(dir.toString() + ":" + spEvent.c() + ":" + spEvent.getsize() + ":" + x() + "-" + y(), 650, 40 + id * 40, debug_p);
            canva.drawText(id + "", x, y, debug_p);
        }
        /*精灵位图存在并且精灵为可见状态才进行绘制*/
        if (bitmap != null && isVisible)
            canva.drawBitmap(bitmap, rct, dst, new Paint());
        canva.restore();
    }
    /*纠正精灵的坐标**//**初始化精灵的时候调用*/
    public void positionCorrect()
    { 
        x = x / Sprite.WIDTH * Sprite.WIDTH;
        y = y / Sprite.HEIGHT * Sprite.HEIGHT;
        /*记录碰撞点*/
        Delection.record4Sprite(this);
    }
    /**设置精灵坐标**/
    public void setPosition(int x, int y)
    {
        int w = GameView.width();
        int h = GameView.height();
        this.x = x < 0 ?0: x > w ?w - Sprite.WIDTH: x;
        this.y = y < 0 ?0: y >= h ?h - Sprite.HEIGHT: y;
        positionCorrect();

    }
    /*角色原地踏步一次*/
    public void feixed()
    {

    }
    /*设置npc的事件*/
    public void setEvent(SpriteEvent event)
    {
        if (null == event)
        {
            spEvent = new SpriteEvent();
            spEvent.setAutoPath();
        }
        else
        {
            spEvent = event;
        }
    }
    /*取得精灵坐标*/
    public int x()
    {
        return x;
    }
    public int y()
    {
        return y;
    }
    public int getId()
    {
        return id;
    }
    public Direction dir()
    {
        return dir;
    }
    public boolean isMove()
    {
        return isMove;
    }
    public boolean isFixed()
    {
        return isFixed;
    }
    /*设置精灵移动速度*/
    public void setSpeed(int spd)
    {
        spd = spd < 0 ?1: spd > 8 
            ?8: spd % 2 == 1 && spd / 2
            > 0 && spd < 8 ?spd + 1: spd;
        this.speed = spd ;
        byte a = 56;
        int[][] f = new int[3][];
       f[2] = new int[3];
    }
}
