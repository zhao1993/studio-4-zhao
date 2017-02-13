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
        return true == MapScence.canMove(sprite);
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
        boolean flag = true;
        int id = sprite.getId();
        Delection.Position dp = getPosition(sprite);
        a:
        for (Map.Entry<Integer,Delection.Position> entry:dePoints.entrySet())
        {
            if (entry.getKey() != id)
            {
                if (entry.getValue().x == dp.x && entry.getValue().y == dp.y)
                {
                    flag = false;
                    break a;
                }
            }
        }
        return flag;
    }
    private static Delection.Position getPosition(Sprite sprite)
    {
        int x = sprite.x() / sprite.WIDTH;
        int y = sprite.y() / sprite.HEIGHT;
        Delection.Position p = new Delection.Position();

        /*见sprite*/
        if (sprite.isFixed())
        {
            p.x = x;
            p.y = y;
        }
        if (sprite.isMove() && !sprite.isFixed())
        {
            switch (sprite.dir())
            {
                case UP:p.y = y - 1;break;
                case DOWN:p.y = y + 1;break;
                case LEFT:p.x = x - 1;break;
                case RIGHT:p.x = x + 1;break;
            }
        }
        return p;
    }
    public static boolean canMove(Sprite sprite)
    {
        return Delection.canMoveOfScreenAndMap(sprite) 
            && !Delection.nextStepiSprite(sprite);
    }
    public static class Position
    {
        public int x;
        public int y;
    }
    //测试用将精灵的碰撞点绘制出来
    public static void drawDelection(Canvas canva)
    {
        for (Map.Entry<Integer,Delection.Position> entry:dePoints.entrySet())
        {
            Delection.Position dp =  entry.getValue();
            canva.drawRect(new Rect(dp.x, dp.y, dp.x + Sprite.WIDTH, dp.y + Sprite.HEIGHT), Paint.paint());
        }
    }
}
