package com.kutayeen.comics;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private File root;
    private ArrayList fileList = new ArrayList<>();
    GridView simpleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);
        root = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath());
        try {
            getfile(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

        simpleList = (GridView) findViewById(R.id.gridview);
        Adapter myAdapter=new Adapter(this,R.layout.grid_view_items,fileList);
        simpleList.setAdapter(myAdapter);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item comic = (Item) fileList.get(position);

                Intent intent = new Intent(getBaseContext(), Open_Comics.class);
                intent.putExtra("dir",comic.getentries() );
                startActivity(intent);
            }
        });
    }

    public ArrayList<File> getfile(File dir) throws IOException {
        File[] listFile = dir.listFiles();
        if (listFile != null && listFile.length > 0) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {

                    getfile(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(".cbz"))

                    {
                        ZipFile zipFile = new ZipFile(listFile[i].getAbsolutePath());
                        Enumeration<? extends ZipEntry> entries = zipFile.entries();
                        Bitmap photo = null;
                        ArrayList<ZipEntry> entryList = new ArrayList<ZipEntry>();
                        ArrayList<String> shortish = new ArrayList<String>();
                        while(entries.hasMoreElements()) {

                            ZipEntry entry = entries.nextElement();
                            if(entry.getName().endsWith(".png")||entry.getName().endsWith(".jpg")){

                                entryList.add(entry);
                                shortish.add(entry.getName());
                            }

                        }
                        int smallestIndex = 0;
                        for (int y = 1; y < shortish.size(); y++) {
                            int compare = shortish.get(y).compareTo(shortish.get(smallestIndex));
                            if (compare < 0)
                                smallestIndex = y;
                        }
                        photo = BitmapFactory.decodeStream( zipFile.getInputStream(entryList.get(smallestIndex)));


                        fileList.add(new Item(listFile[i].getName(),photo,listFile[i].getAbsolutePath()));
                        zipFile.close();
                    }
                }

            }
        }
        return fileList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    root = new File(Environment.getExternalStorageDirectory()
                            .getAbsolutePath());
                    try {
                        getfile(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    simpleList = (GridView) findViewById(R.id.gridview);
                    Adapter myAdapter=new Adapter(this,R.layout.grid_view_items,fileList);
                    simpleList.setAdapter(myAdapter);

                    simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Item comic = (Item) fileList.get(position);

                            Intent intent = new Intent(getBaseContext(), Open_Comics.class);
                            intent.putExtra("dir",comic.getentries() );
                            startActivity(intent);
                        }
                    });
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}