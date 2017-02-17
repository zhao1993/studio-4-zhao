package game.graphic.party;
import game.graphic.GameView;
import game.graphic.sprite.Sprite;
import java.util.ArrayList;
import game.graphic.map.MapScence;
import java.util.HashMap;
import java.util.Map;
import android.graphics.Canvas;
import java.util.Set;
import android.graphics.Rect;
import game.graphic.Paint;
import game.util.Log4Game;
/*关于碰撞检测的处理类*/
public class Delection
{
    //private static ArrayList<Delection.Position> dePoints = new ArrayList<Delection.Position>();
    private static HashMap<Integer,Delection.Position> dePoints = new HashMap<Integer,Delection.Position>();
    /*检测精灵是否可以进行下一步移动(针对界面和地图)*/
    public static boolean canMoveOfScreenAndMap(Sprite sprite)
    {
        int spriteX = sprite.x();
        int spriteY = sprite.y();
        int width = Sprite.WIDTH;
        int height = Sprite.HEIGHT;
        if (sprite.dir() == Direction.UP && spriteY - height < 0)
            return false;
        if (sprite.dir() == Direction.DOWN && spriteY + height * 2 > GameView.height())
            return false;      
        if (sprite.dir() == Direction.LEFT && spriteX - width < 0)
            return false;
        if (sprite.dir() == Direction.RIGHT && spriteX + width * 2 > GameView.width())
            return false;
        return MapScence.canMove(sprite);
    }
    /*记录精灵的碰撞*/
    /*会将精灵的下一个目标点(可以的精灵)作为碰撞点
     不可移动的会将现在的坐标作为碰撞点
     */
    public static void record4Sprite(Sprite sprite)
    {
        dePoints.put(sprite.getId(), getPosition(sprite));
    }
    public static boolean nextStepiSprite(Sprite sprite)
    {
        int id = sprite.getId();
        Delection.Position dp = getPosition(sprite);
        for (Map.Entry<Integer,Delection.Position> entry:dePoints.entrySet())
        {
            if (entry.getKey() != id)
            {
                if (entry.getValue().x == dp.x && entry.getValue().y == dp.y)
                {
                    return true;
                }
            }
        }
        return false;
    }
    //得到精灵现在的坐标(实际获取下一点的坐标)
    private static Delection.Position getPosition(Sprite sprite)
    {
        int x = sprite.x() / sprite.WIDTH;
        int y = sprite.y() / sprite.HEIGHT;

        if (sprite.isMove() && !sprite.isFixed())
        {
            switch (sprite.dir())
            {
                case UP:y = y - 1;break;
                case DOWN:y = y + 1;break;
                case LEFT:x = x - 1;break;
                case RIGHT:x = x + 1;break;
            }
        }
        Log4Game.add(Log4Game.Mode.ERROR, "精灵ID" + sprite.getId() + "下一点坐标" + x + ":" + y);
        return new Delection.Position(x, y);
    }
    public static boolean canMove(Sprite sprite)
    {
        return Delection.canMoveOfScreenAndMap(sprite) 
         && !Delection.nextStepiSprite(sprite);
    }
    public static class Position
    {
        public Position(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public int x;
        public int y;
    }
    //测试用将精灵的碰撞点绘制出来
    public static void drawDelection(Canvas canva)
    {
        for (Map.Entry<Integer,Delection.Position> entry:dePoints.entrySet())
        {
            Delection.Position dp =  entry.getValue();
            canva.drawRect(new Rect(dp.x*Sprite.WIDTH, dp.y*Sprite.HEIGHT, dp.x*Sprite.WIDTH + Sprite.WIDTH, dp.y*Sprite.HEIGHT + Sprite.HEIGHT), Paint.paint());
        }
    }
}
