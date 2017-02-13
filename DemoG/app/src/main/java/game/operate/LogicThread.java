package game.operate;
import game.graphic.Button;
import game.graphic.GameView;
import game.graphic.party.Direction;
import game.graphic.sprite.SpriteFactory;
import game.graphic.sprite.SpriteLogic;
import game.graphic.map.MapScence;

public class LogicThread extends Thread
{
    @Override
    public void run()
    {
        // TODO: Implement this method
        while (GameView.run)
        {
            synchronized (GameView.key)
            {

                if (!GameView.alternate)
                {
                    MapScence.logic();
                    SpriteLogic.logic();
                    /*这里写逻辑*/
                    switch (GameView.button)
                    {
                        case DOWN:
                            GameView.player.updatePosition(Direction.DOWN);
                            break;
                        case UP:
                            GameView.player.updatePosition(Direction.UP);
                            break;
                        case LEFT:
                            GameView.player.updatePosition(Direction.LEFT);
                            break;
                        case RIGHT:
                            GameView.player.updatePosition(Direction.RIGHT);
                            break;
                        case OK:
                            //GameView.player.updatePostion(Direction.DOWN);
                            MapScence.setMap("demo01");
                            break;
                        case CANCLE:
                            //GameView.player.updatePostion(Direction.DOWN);
                            break;                 
                        case MENU:
                            //GameView.player.updatePostion(Direction.DOWN);
                            break; 

                    }
                    GameView.button = Button.NULL;
                    GameView.alternate = true;
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

}
