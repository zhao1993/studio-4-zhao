package game.graphic.sprite;
/**/
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import android.graphics.Canvas;
import java.util.Hashtable;
import game.graphic.Paint;

public class SpriteFactory
{
    /*精灵类*/
    private static String sprite ="game.graphic.sprite.Sprite";
    /*记录下当前存在的精灵id同一id存在则不创建*/
    static Hashtable<Integer,Sprite> sprites=new Hashtable<Integer,Sprite>();
    /*只允许一个player实例*/
    static Sprite player;
    private static Sprite createSprite(int id, String name, boolean isnpc)
    {
        if (sprites.containsKey(id))
            return sprites.get(id);
        try
        {
            Class<Sprite> sClazz = (Class<Sprite>) Class.forName(SpriteFactory.sprite);
            Constructor<Sprite> ctr =sClazz.getDeclaredConstructor(int.class, String.class);
            Sprite sprite = ctr.newInstance(id, name);
            Field field = sprite.getClass().getDeclaredField("isNPC");
            field.setAccessible(true);
            field.setBoolean(sprite, isnpc);
            sprites.put(id, sprite);
            return sprite;
        }
        catch (Exception e)
        {

        }
        return null;
    }
    public static Sprite getPlayer(int id, String name)
    {
        if (null == player)
        {
            player = SpriteFactory.createSprite(id, name, false);
        }
        return player;
    }
    public static Sprite createSprite(int id, String name)
    {
        return SpriteFactory.createSprite(id, name, true);
    }
    /*测试用随机生成指定个数的精灵*/
    public static void debugCreateSprite(int count)
    {
        for (int i=0;i < count;i++)
        {
            int x = (int)( Math.random() * 1024);
            int y =(int) (Math.random() * 1780);
            int id = (i + 10) * 10;
            Sprite sprite = createSprite(id,"People2_2");
            sprite.setPosition(x,y);
            sprite.setSpeed(2);      
        }
    }
}
