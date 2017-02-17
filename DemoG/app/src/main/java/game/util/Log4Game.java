package game.util;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Log4Game
{
    public static Mode write= Mode.INFO;
    public static void add(Mode mode, Object... obj)
    {
        if (mode.value() < write.value())
            return;
        StringBuffer err =new StringBuffer();
        err.append(new SimpleDateFormat("hh:mm:ss").format(new Date()));
        err.append("[cased by]-->");
        for (Object ob:obj)
        {
            err.append(ob.toString());
            err.append("--");
        }
        err.append("\n\r");
        save(err.toString());

    }
    private static void save(String err)
    {
        File file = new File("/storage/emulated/0/err.txt");
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch (IOException e)
            {}
        }
        try
        {
            FileOutputStream fot = new FileOutputStream(file, true);
            try
            {
                fot.write(err.getBytes(), 0, err.getBytes().length);
            }
            catch (IOException e)
            {}

            try
            {
                if (fot != null)
                    fot.close();

            }
            catch (IOException e)
            {}}
        catch (FileNotFoundException e)
        {}
    }
    public enum Mode
    {
        ERROR(1),WARN(0),INFO(-1);
        private int value = 0;
        private Mode(int i){
            value = i;
        }
        public int value(){
            return value;
        }
    }
}

