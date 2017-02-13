package game.graphic.sprite;

/*精灵同一绘制*/
import android.graphics.Canvas;
import android.graphics.Paint;
import game.graphic.sprite.Sprite;
import java.util.Map;

public class SpriteView
{
    public static void drawSprite(Canvas canva)
    {
        if (SpriteFactory.sprites.size() > 0)
        {
            for (Map.Entry<Integer,Sprite> set : SpriteFactory.sprites.entrySet())
            {
                set.getValue().drawSprite(canva);
            }
        }
    }
}
