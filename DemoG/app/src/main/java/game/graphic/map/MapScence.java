package game.graphic.map;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import game.graphic.sprite.Sprite;
import java.util.ArrayList;
import android.graphics.Rect;
import game.util.Log4Game;
import game.graphic.Paint;

public class MapScence
{
    private static Map map;
    private static Bitmap bitmap;
    private static int row=8;
    private static int cos=20;
    /*保存地图层数据用*/
    private static ArrayList<Integer[][]> mapdatas;
    static{
        map = Map.getInstance();
    }
    /*地图逻辑*/
    public static void logic()
    {
        if (!MapInfo.mapName.equals("") && MapInfo.change)
        {
            MapInfo.drawMap = true;
            MapInfo.change = false;
        }
    }

    /*绘制地图*/
    public static void drawBase(Canvas canva)
    {
        drawMap(canva, 0);
        drawMap(canva, 1);
        drawMap(canva, 2);
    }
    public static void drawTop(Canvas canva)
    {

        // canva.drawBitmap(bitmap, 0, 0, null);
        drawMap(canva, 3);
    }
    /*设置数据并分析初始化*/
    public static  void setMap(String name)
    {
        map.init(name);
        bitmap = map.getMapic();
        row = bitmap.getWidth() / map.getTilewidth();
        cos = bitmap.getHeight() / map.getTileheight();
        MapInfo.change = true;
    }
    public static boolean canMove(Sprite sprite)
    {
        if (!MapInfo.drawMap)
            return true;
        return map.isZero(sprite);
    }
    private static void drawMap(Canvas canva, int layer)
    {
        if (!MapInfo.drawMap)
            return;
        Integer[][] data = map.getLayerData(layer);
        Rect rct = new Rect();
        Rect dst = new Rect();
        int w = map.getTilewidth();
        int h = map.getTileheight();
        try
        {
            for (int i= 0; i < data.length;i++)
            {

                for (int j= 0;j < data[i].length;j++)
                {
                    int title = data[i][j] - 1;
                    int x = title % row;
                    int y = title / row;
                    rct.left = x * w;
                    rct.top = y * h;
                    rct.right = rct.left + w;
                    rct.bottom = rct.top + h;
                    dst.left =  j * w;
                    dst.top = i * h;
                    dst.right = dst.left + w;
                    dst.bottom = dst.top + h;
                    canva.drawBitmap(bitmap, rct, dst, null);
                }

            }
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, "MapSence---->method:drawMap-->", e);         
        }
    }
}
