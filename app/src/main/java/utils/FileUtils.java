package utils;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import models.Character;

/**
 * Created by Ishibori on 07/09/2017.
 */

public class FileUtils {
    public static String CHARACTER_FILE = "user_char.inf";

    public static void serializeObject(Context context, Serializable obj)
    {
        serializeObject(context, CHARACTER_FILE, obj);
    }

    private static void serializeObject(Context context, String filename, Serializable obj)
    {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public static Character loadCharacter(Context context)
    {
        return loadCharacter(context, CHARACTER_FILE);
    }

    private static Character loadCharacter(Context context, String filename)
    {
        try {
            FileInputStream fis = context.openFileInput(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            Character user_char = (Character) is.readObject();
            is.close();
            fis.close();
            return user_char;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
