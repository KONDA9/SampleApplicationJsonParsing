package com.altimetrik.sampleapplicationjsonparsing;
/**
 * Created by bkondaiah on 09-10-2015.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    Context context;
    protected ArrayList<JsonArrayDTO> jsonArrayList;
    LayoutInflater inflater;

    public ListViewAdapter(Context context, ArrayList<JsonArrayDTO> iTunesDetails) {
        this.jsonArrayList = iTunesDetails;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() {
        return jsonArrayList.size();
    }

    public JsonArrayDTO getItem(int position) {
        return jsonArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {


            convertView = this.inflater.inflate(R.layout.listrow_item,
                    parent, false);

            holder.txtTitle = (TextView) convertView
                    .findViewById(R.id.title_TV);
            holder.txtSinger = (TextView) convertView
                    .findViewById(R.id.description_TV);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        JsonArrayDTO jsonDetails = jsonArrayList.get(position);
        holder.txtTitle.setText(jsonDetails.getTitle());
        holder.txtSinger.setText(jsonDetails.getDescription());
        new LoadImageFromURL().execute(holder.albumImage, jsonDetails.getImage());

        return convertView;
    }

    private class LoadImageFromURL extends AsyncTask<Object, Void, Bitmap> {
        ImageView ivPreview = null;

        @Override
        protected Bitmap doInBackground(Object... params) {
            this.ivPreview = (ImageView) params[0];
            String url = (String) params[1];
            System.out.println(url);
            return loadBitmap(url);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
//            ivPreview.setImageBitmap(result);
        }
    }

    public Bitmap loadBitmap(String url) {
        URL newUrl = null;
        Bitmap bitmap = null;
        try {
            newUrl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newUrl.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return bitmap;
    }

    private class ViewHolder {
        TextView txtTitle;
        TextView txtSinger;
        ImageView albumImage;
    }
}