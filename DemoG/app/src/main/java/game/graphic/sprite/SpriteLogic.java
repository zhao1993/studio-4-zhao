package game.graphic.sprite;

import game.graphic.sprite.Sprite;
import java.util.Map;

public class SpriteLogic
{public static void logic()
    {
        if (SpriteFactory.sprites.size() > 0)
        {
            for (Map.Entry<Integer,Sprite> set : SpriteFactory.sprites.entrySet())
            {
                set.getValue().updatePosition();
            }
        }
    }
}
