package com.katenzo.camtenzo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.logging.Filter;


public class FilterImageActivity extends Activity {

    private ImageView image;
    private Button filterGrayscale;
    private Button filterSephia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_image);

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.raw.meme);

        image = (ImageView) findViewById(R.id.image_container);
        image.setImageBitmap(bitmap);

        filterGrayscale = (Button) findViewById(R.id.filter_gray_scale);

        filterGrayscale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(FilterImage.convertToGrayScale(bitmap));
            }
        });

        filterSephia = (Button) findViewById(R.id.filter_sephia);
        filterSephia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image.setImageBitmap(FilterImage.convertToSephia(bitmap,1,10,10,255));
            }
        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
