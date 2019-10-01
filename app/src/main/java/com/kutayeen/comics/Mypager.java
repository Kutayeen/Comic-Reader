package com.kutayeen.comics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Mypager extends PagerAdapter {

    private final String dir;
    private Context context;
    ArrayList<Pages> pages;
    ZipFile zipFile;
    public Mypager(Context context, String dir) {
        this.context = context;
        this.dir=dir;
        System.out.println(dir);
        pages = new ArrayList<Pages>();
        try {
            zipFile = new ZipFile(dir);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            Bitmap photo = null;

            while (entries.hasMoreElements()) {

                ZipEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".png") || entry.getName().endsWith(".jpg")) {

                    pages.add(new Pages(entry.getName(), entry));
                }

            }
            Collections.sort(pages, new Pages.CustomComparator());
    } catch (IOException e) {
            e.printStackTrace();
        }}
    /*
    This callback is responsible for creating a page. We inflate the layout and set the drawable
    to the ImageView based on the position. In the end we add the inflated layout to the parent
    container .This method returns an object key to identify the page view, but in this example page view
    itself acts as the object key
    */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, null);
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageBitmap(getImageAt(position));
        container.addView(view);
        return view;
    }
    /*
    This callback is responsible for destroying a page. Since we are using view only as the
    object key we just directly remove the view from parent container
    */
    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
    /*
    Returns the count of the total pages
    */
    @Override
    public int getCount() {
        return pages.size();
    }
    /*
    Used to determine whether the page view is associated with object key returned by instantiateItem.
    Since here view only is the key we return view==object
    */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }
    private Bitmap getImageAt(int position) {

        try {
            return BitmapFactory.decodeStream(zipFile.getInputStream(pages.get(position).entry));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}