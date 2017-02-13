package game.graphic.map;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import game.graphic.sprite.Sprite;
import game.graphic.AssetsFactory;
import game.util.Log4Game;
import android.graphics.Bitmap;
import game.graphic.party.Marge;

public final class Map
{
    private static Map gameMap;
    /*字段*/
    private  String nStr = "\n";
    private  String nrStr = "\n\r";
    private  String widthStr = "width";
    private  String heightStr = "height";
    private  String tilewidthStr = "tilewidth";
    private  String tileheightStr = "tileheight";
    private  String orientationStr = "orientation";
    private  String tilesetStr = "tileset";
    private  String dataStr = "data";
    private  int width;
    private  int height;
    private  int tilewidth;
    private  int tileheight;
    private  String orientation;
    private  StringBuffer mData = null;
    private  ArrayList<Integer[][]> maps = new ArrayList<Integer[][]>();
    private  ArrayList<String> mapNames = new ArrayList<String>();

    public Bitmap getMapic()
    {
        Bitmap[] bmps =new Bitmap[mapNames.size()];
        for (int i=0;i < bmps.length;i++)
        {
            bmps[i] = AssetsFactory.getMapBitmap(mapNames.get(i));
        }
        Bitmap bmp =Marge.createBitmap(bmps);
        return bmp;

    }
    public  void init(String name)
    {
        mData = AssetsFactory.getMapData(name);
        width = getInteger(widthStr);
        height = getInteger(heightStr);
        tilewidth = getInteger(tilewidthStr);
        tileheight = getInteger(tileheightStr);
        orientation = getMessage(orientationStr);
        parseMapFileName(tilesetStr);
        parseData();
    }
    /* 获取头部信息 */
    private  int getInteger(String tag)
    {
        String tempStr = getMessage(tag);
        Integer integer = null;
        try
        {
            integer = Integer.valueOf(tempStr);
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, this.getClass(), "method:getInteger", e);
        }
        return integer;
    }

    /* 获取信息针对头部 */
    private  String getMessage(String tag)
    {
        String msgStr = null;
        try
        {
            int tagIndex = mData.indexOf(tag.toString()) + tag.length() + 1;
            msgStr = mData.substring(tagIndex, mData.indexOf(nStr, tagIndex) - 1);
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, this.getClass(), "method:getMessage()", e);
        }
        return msgStr;
    }

    /* 获取文件名字 */
    private  void parseMapFileName(String tag)
    {
        int[] integers = growCount(tag);
        /* 忽略第一个 */
        try
        {
            for (int i = 1; i < integers.length; i++)
            {
                String line = mData.substring(integers[i], mData.indexOf(nStr, integers[i]));
                String name = line.substring(line.lastIndexOf("/") + 1, line.lastIndexOf("."));
                mapNames.add(name);
            }
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, this.getClass(), "method:parseMapFileName()", e);
        }
    }

    /* 获取子字符串在字符串中出现的次数 并返回出现位置的下标 */
    private  int[] growCount(String sub)
    {
        ArrayList<Integer> arrays = new ArrayList<Integer>();
        int[] countIndex = null;
        int start = 0;
        try
        {
            while (true)
            {
                int i = mData.indexOf(sub, start + 1);
                if (i != -1)
                {
                    arrays.add(i);
                    start = i;
                }
                else
                {
                    break;
                }
            }
            countIndex = new int[arrays.size()];
            for (int i = 0; i < countIndex.length; i++)
            {
                countIndex[i] = arrays.get(i);
            }
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, this.getClass(), "method:getCount()", e);
        }
        return countIndex;
    }

    /* 解析数据为maps 赋值 */
    private  void parseData()
    {
        /* 获取data字段的出现次数 */
        int[] dataCount = growCount(dataStr);
        try
        {
            for (int i = 0; i < dataCount.length; i++)
            {
                Integer[][] map = new Integer[height][width];
                String data = mData.substring(dataCount[i] + dataStr.length() + 1, mData.indexOf(nrStr, dataCount[i]));
                String[] datas = data.split(",");
                for (int j = 0; j < datas.length; j++)
                {
                    map[j / width][j % width] = Integer.valueOf(datas[j].trim());
                }
                maps.add(map);
            }
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, this.getClass(), "method:growCount()", e);
        }
    }

    /**
     * 得到对应层的数组数据
     * 
     * @param layer
     *            对应的层次序号
     * @return 返回integer数组 没有则返回null
     */
    public  Integer[][] getLayerData(int layer)
    {
        if (layer >= 0 && layer < maps.size())
        {
            return maps.get(layer);
        }
        return null;
    }

    /**
     * 根据坐标方向获取是否 面前的数字是否为零
     * 
     * @param sprite
     *            精灵
     * @return 如果为零返回true
     */
    public  boolean isZero(Sprite sprite)
    {
        boolean flag = false;
        /* 定义方向修正 */
        int _x = 0;
        int _y = 0;
        /* 修正坐标 */
        switch (sprite.dir())
        {
            case UP:
                _y = -tileheight;
                break;
            case DOWN:
                _y = tileheight;
                break;
            case LEFT:
                _x = -tilewidth;
                break;
            case RIGHT:
                _x = tilewidth;
                break;
        }
        try
        {
            int x = (_x + sprite.x()) / tilewidth;
            int y = (_y + sprite.y()) / tileheight;
            /* 得到碰撞层并判断是否为零 */
            if (getLayerData(2)[y][x] == 0)
            {       
                flag = true;
            }
            else
            {
                Log4Game.add(Log4Game.Mode.ERROR, 666);
            }
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, getClass(), "method:isZreo()", e);
        }
        return flag;
    }
    public static Map getInstance()
    {
        if (gameMap == null)
        {
            gameMap = new Map();
        }
        return gameMap;
    }
    private Map()
    {}
    /*数据信息获取*/

    public int getWidth()
    {
        return width;
    }


    public int getHeight()
    {
        return height;
    }



    public int getTilewidth()
    {
        return tilewidth;
    }



    public int getTileheight()
    {
        return tileheight;
    }



    public String getOrientation()
    {
        return orientation;
    }

    public int getLayerCount()
    {
        return maps.size();
    }
}

