package game.operate;
import game.graphic.party.Direction;
import java.util.ArrayList;

public class SpriteEvent 
{
    private boolean autoWalk = false;
    /*设置的路线*/
    private ArrayList<Direction> dirs= new ArrayList<Direction>();
    /*是否循环移动的标志*/
    private boolean loopMove = true;
    /*当前移动到的序号*/
    private int index4Move = 0;
    /*顺序与倒序标记*/
    private boolean orderAno = true;

    public SpriteEvent clone()
    {
       return this;
    }
    /*设置移动路线*/
    public void setMovePath(int startX, int startY, int endX, int endY, boolean isLoop)
    {

    }
    /*设置移动路线与是否循环*/
    public void setDetailMovePath(boolean isLoop, Direction... dirs)
    {
        for (Direction dir:dirs)
        {
            this.dirs.add(dir);
        }
        loopMove = isLoop;
    }
    /*设置移动路线默认循环*/
    public void setDetailMovePath(Direction... dir)
    {
        setDetailMovePath(true, dir);
    }
    /*设置自动路线后所有路线设置无效化*/
    public void setAutoPath()
    {
        autoWalk = true;
    }
    public int getsize()
    {
        return dirs.size();
    }
    public int c()
    {
        return index4Move;
    }
    /*获取精灵的下一步*/
    public Direction nextDirection()
    {
        Direction dir = null;
        if (autoWalk)
        {
            int i = (int) (Math.random() * 4);
            dir = Direction.values()[i];
        }
        else
        {
            /*没有走完时顺序走*/
            if (index4Move <= dirs.size() && orderAno)
            { 
                /*通过序号得到下一步*/             
                if (index4Move == dirs.size())
                {
                    dir = null;
                    index4Move = dirs.size()-1;
                    orderAno = false;
                }      
                else
                {      
                    dir = dirs.get(index4Move);
                    index4Move++;
                }

            }
            /*否则原路返回走*/
            else if (loopMove && index4Move >= 0 && !orderAno)
            {

                dir = dirs.get(index4Move);
                if (dir == Direction.UP)
                    dir =  Direction.DOWN;
                else if (dir == Direction.DOWN)
                    dir = Direction.UP;  
                else if (dir == Direction.LEFT)
                    dir = Direction.RIGHT;
                else if (dir == Direction.RIGHT)
                    dir = Direction.LEFT;

                if (index4Move <= 0)
                {
                    dir = null;
                    index4Move = 0;
                    orderAno = true;
                }
                else
                {
                    index4Move--;
                }

            }
        }
        return dir;
    }
}
