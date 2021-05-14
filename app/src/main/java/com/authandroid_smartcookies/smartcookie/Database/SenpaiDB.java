package com.authandroid_smartcookies.smartcookie.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SenpaiDB extends SQLiteOpenHelper {
    private static final String TAG = "SENPAI";
    public static String DB_PATH;
    public static String DB_NAME = "database.db";

    public SenpaiDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        assert context != null;
        DB_PATH = context.getFilesDir().getPath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void copyDatabase(Context context) {
        try {
            InputStream myInput = context.getAssets().open(DB_NAME);
            // Path to the just created empty db
            String outFileName = DB_PATH + DB_NAME;
            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
