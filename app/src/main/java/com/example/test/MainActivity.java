package com.example.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        File file = new File(getExternalFilesDir(null), "soldiers_positions2");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(1);
            fos.write(12);
            fos.write('E');
            fos.write(0);
            fos.write(2);

            fos.write('E');
            fos.write(0);
            fos.write(3);

            fos.write('F');
            fos.write(0);
            fos.write(4);

            fos.write('F');
            fos.write(0);
            fos.write(5);

            fos.write('E');
            fos.write(1);
            fos.write(1);

            fos.write('A');
            fos.write(1);
            fos.write(2);

            fos.write('A');
            fos.write(1);
            fos.write(5);

            fos.write('F');
            fos.write(1);
            fos.write(6);

            fos.write('E');
            fos.write(2);
            fos.write(3);

            fos.write('F');
            fos.write(3);
            fos.write(3);

            fos.write('W');
            fos.write(4);
            fos.write(1);

            fos.write('W');
            fos.write(4);
            fos.write(5);

            fos.write(2);
            fos.write(12);
            fos.write('A');
            fos.write(11);
            fos.write(1);

            fos.write('F');
            fos.write(11);
            fos.write(3);

            fos.write('E');
            fos.write(11);
            fos.write(5);

            fos.write('E');
            fos.write(10);
            fos.write(2);

            fos.write('F');
            fos.write(10);
            fos.write(4);

            fos.write('E');
            fos.write(10);
            fos.write(6);

            fos.write('F');
            fos.write(9);
            fos.write(1);

            fos.write('E');
            fos.write(9);
            fos.write(3);

            fos.write('F');
            fos.write(9);
            fos.write(5);

            fos.write('A');
            fos.write(9);
            fos.write(7);

            fos.write('W');
            fos.write(7);
            fos.write(5);

            fos.write('W');
            fos.write(6);
            fos.write(2);

            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}