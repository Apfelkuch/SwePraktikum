package de.swe_wi2223_praktikum;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileManager {

    public static final String BESTELLUNGEN = "bestellungen.med";
    public static final String KALENDER = "kalender.med";
    public static final String LOG = "log.med";
    public static final String MEDIKAMENT = "medikamente.med";
    public static final String HOME = "homescreen.med";
    public static final String PLAN = "plan.med";

    public static boolean save(String filename, Context context, Object o) {
        System.out.println("Saving: " + filename);
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(o);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Object load(String filename, Context context) {
        System.out.println("Loading: " + filename);
        if (new File(filename).exists()) {
            return null;
        }
        Object object;
        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            object = ois.readObject();
            ois.close();
        } catch (FileNotFoundException fileNotFoundException) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return object;
    }
}

