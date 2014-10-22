package com.katenzo.camtenzo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by daori on 10/6/14.
 */
public class FilterImageActivityFungsionalTestCase extends ActivityInstrumentationTestCase2<FilterImageActivity>{

    private FilterImageActivity activity;
    private ImageView imageContainer;
    private Button filterGrayScale;
    private Button buttonSend;
    private Button filterSephia;
    private Intent filterIntentImage;
    private Bitmap dummyImage;


    public FilterImageActivityFungsionalTestCase(String name) {
        super(FilterImageActivity.class);
        setName(name);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();


        activity = getActivity();
        assertNotNull(activity);

        imageContainer = (ImageView) activity.findViewById(R.id.image_container);
        Bitmap bMap = BitmapFactory.decodeFile(Uri.parse("android.resource://"+ getActivity().getPackageName() +"/raw/meme.jpg").toString());
        imageContainer.setImageBitmap(bMap);
        assertNotNull(imageContainer);

        filterGrayScale = (Button) activity.findViewById(R.id.filter_gray_scale);
        assertNotNull(filterGrayScale);

        filterSephia = (Button) activity.findViewById(R.id.filter_sephia);
        assertNotNull(filterSephia);

        buttonSend = (Button) activity.findViewById(R.id.button_send);
        assertNotNull(buttonSend);

        dummyImage =((BitmapDrawable)imageContainer.getDrawable()).getBitmap();

        filterGrayScale.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FilterImage.convertToGrayScale(dummyImage);
            }
        });
    }

    @SmallTest
    public void testComponentOnScreen() throws Exception {
        final View container = activity.getWindow().getDecorView();

        ViewAsserts.assertOnScreen(container, imageContainer);
        ViewAsserts.assertOnScreen(container, filterGrayScale);
        ViewAsserts.assertOnScreen(container, filterSephia);
        ViewAsserts.assertOnScreen(container, buttonSend);
    }

    @SmallTest
    public void testJustification() throws Exception {
        final int expectedButtonFilter = Gravity.LEFT|Gravity.CENTER_VERTICAL;
        final int expectedButtonSend = Gravity.RIGHT|Gravity.CENTER_VERTICAL;

        assertEquals(expectedButtonFilter, filterGrayScale.getGravity());
        assertEquals(expectedButtonFilter, filterSephia.getGravity());
        assertEquals(expectedButtonSend, buttonSend.getGravity());
    }

}
