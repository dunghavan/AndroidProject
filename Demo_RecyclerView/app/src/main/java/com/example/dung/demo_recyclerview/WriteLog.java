package com.example.dung.demo_recyclerview;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Dung on 12/22/2017.
 */

public class WriteLog {
    static String FILE_NAME = "log.txt";

    public static void WriteToFile(String content) {
        try {
            File file = new File(FILE_NAME);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            //FileOutputStream fos = MyApplication.getCurrentContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);

            fos.write(content.getBytes());
            Log.d("Write log", "Write Log OK!");

            fos.close();
        } catch (Exception e) {
            // TODO: handle exception
            Log.d("Err write log", e.toString());
        }

    }
}
