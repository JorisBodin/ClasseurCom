package com.joris.classeurcom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater = null;
    private ArrayList<Item> listeItem;

    public ItemAdapter(Activity a, ArrayList<Item> listeItem) {
        activity = a;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listeItem = listeItem;
    }

    public int getCount() {
        return listeItem.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.grid_adapter, null);

        Item item = listeItem.get(position);

        ImageView image = (ImageView) vi.findViewById(R.id.grid_image);
        TextView name = (TextView) vi.findViewById(R.id.grid_name);


        try {
            File f = new File(item.getImage());
            Uri yourUri = Uri.fromFile(f);
            image.setImageBitmap(getBitmapFromUri(yourUri));
        } catch (IOException e) {
            e.printStackTrace();
        }
        name.setText(item.getNom());

        return vi;
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                activity.getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}