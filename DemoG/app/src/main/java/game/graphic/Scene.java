package game.graphic;
import android.graphics.Canvas;
import game.graphic.sprite.SpriteView;
import game.graphic.window.Window_Gold;
import game.graphic.party.Direction;
import game.graphic.map.MapScence;
import game.graphic.party.Delection;

public class Scene
{
    private static Window_Gold winGold = new Window_Gold();
    //private static Window_Gold winGold2 = new Window_Gold(winGold, Direction.DOWN);

    public static void  draw(Canvas canva)
    {
        MapScence.drawBase(canva);
        SpriteView.drawSprite(canva);
        MapScence.drawTop(canva);
        Delection.drawDelection(canva);
        
        //winGold.draw(canva);
        //winGold2.draw(canva);
    }
}
