package game.graphic.window;
import game.graphic.party.Direction;

public class Window_Gold extends Window_Base
{
    public  Window_Gold()
    {
        super(10, 100, 400, 200);
    }
    public Window_Gold(Window_Base win, Direction dir)
    {
        this();
        super.locateOf(win, dir);
    }
    
    
}
