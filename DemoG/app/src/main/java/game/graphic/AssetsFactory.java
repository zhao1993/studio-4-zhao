package game.graphic;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import game.util.Log4Game;
import java.io.IOException;
import java.io.InputStream;

public final class AssetsFactory implements AssertsPath
{    
    private static AssetManager am;
    /*
     去掉文件后缀名|
     */
    public static String noEndWith(String fileName)
    {
        int index = fileName.indexOf(".");
        if (index != -1)
        {
            return fileName.substring(0, index);
        }
        return fileName;
    }

    /*优化版*/
    /*必须执行这个方法才可以调用其他方法*/
    public static void initialization(Context context)
    {
        try
        {
            am = context.getResources().getAssets();
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, "game.graphic.AssetFactory.Method:initialization()" + e);

        }
    }

    /*获取地图文件*/
    public static StringBuffer getMapData(String name)
    {
        // TODO: Implement this method
        InputStream is = null;
        try
        {
            is = getStream(MAPDATA + "/" + name + ".txt");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            return new StringBuffer(new String(buffer));
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, "game.graphic.AssetFactory.Method:getMapData()" + e);
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {}
            }
        }
        return null;
    }

    /*获取窗口图片文件*/
    public static Bitmap getWinSkin(String fName)
    {
        return AssetsFactory.getBitmap(SYSTEM, fName);
    }
    /*获取地图图块文件*/
    public static Bitmap getMapBitmap(String name)
    {
        return AssetsFactory.getBitmap(TILES, name);
    }
    /*获取一个精灵图片单一精灵图片素材*/
    public static Bitmap getBitmap4Sprite(String name)
    {

        return AssetsFactory.getBitmap(CHARACTERS, name);
    }
    /*获取一个精灵图片4*2精灵图片素材*/
    /*针对8个图块的图片获取图片 0-7的序号 如格式people_0*/
    public static Bitmap get8Bitmap4Sprite(String name)
    {

        Bitmap bitmap = null;
        /*先分析文件名*/
        if (name.matches("[\\w]+[_][0-7]"))
        {
            int underline = name.indexOf("_");

            int index = Integer.valueOf(name.substring(underline + 1, name.length()));
            String newFile = name.substring(0, underline);

            bitmap = getBitmap4Sprite(newFile);

            if (bitmap != null)
            {
                try
                {
                    /*一块图片的宽高*/
                    int width = bitmap.getWidth() / 4;
                    int height = bitmap.getHeight() / 2;

                    /*获取的序号修正*/
                    int x = index % 4;
                    int y= index >= 4 ?1: 0;        
                    /*裁剪图片处理*/
                    bitmap = Bitmap.createBitmap(bitmap, x * width,
                                                 y * height, x * width + width, y * height + height);

                }
                catch (Exception e)
                {
                    Log4Game.add(Log4Game.Mode.WARN, "game.graphic.AssetFactory.Method:get8Bitmap4Sprite()" + e);
                }       
            }
        }
        if (bitmap == null)
            bitmap =  getBitmap4Sprite(name);

        return bitmap;
    }

    /*获取一个图片在指定的路径下*/
    public static Bitmap getBitmap(String path, String name)
    {
        InputStream in = null;
        try
        {
            Bitmap bmp=null;
            in = getStream(path + "/" + name + ".png");
            if (in != null)
                bmp = BitmapFactory.decodeStream(in);
            return bmp;
        }
        catch (Exception e)
        {
            Log4Game.add(Log4Game.Mode.ERROR, "game.graphic.AssetFactory.Method:getBitmap()" + e);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {

                }
            }
        }
        return null;
    }
    /*从assets中获取一个流文件 需要一个完整路径文件名*/
    public static InputStream getStream(String name)
    {
        InputStream input = null;
        try
        {
            input = am.open(name);
        }
        catch (IOException e)
        {
            Log4Game.add(Log4Game.Mode.WARN, "game.graphic.AssetFactory.Method:getStream()" + e);
        }
        return input;
    }
}
