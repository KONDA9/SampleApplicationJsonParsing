package com.altimetrik.sampleapplicationjsonparsing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by bkondaiah on 09-10-2015.
 */
public class SingleItemView extends Activity {

    private String title, description, image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);

        Intent i = getIntent();
        title = i.getStringExtra("title");
        description = i.getStringExtra("description");
        Bundle extras = getIntent().getExtras();
        image = i.getStringExtra("image");

        TextView txtTitle = (TextView) findViewById(R.id.title_TV);
        TextView txtDescription = (TextView) findViewById(R.id.description_TV);

        ImageView imageIMV = (ImageView) findViewById(R.id.image_IMV);

        txtTitle.setText(title);
        txtDescription.setText(description);
        new LoadImageFromURL().execute(imageIMV,image);
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
            ivPreview.setImageBitmap(result);
        }
    }
// This method represents return the bitmap image
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


}
