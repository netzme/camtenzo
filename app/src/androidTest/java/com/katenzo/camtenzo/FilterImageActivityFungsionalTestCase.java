package com.katenzo.camtenzo;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ViewAsserts;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Gravity;
import android.view.LayoutInflater;
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
        assertNotNull(imageContainer);

        filterGrayScale = (Button) activity.findViewById(R.id.filter_gray_scale);
        assertNotNull(filterGrayScale);

        filterSephia = (Button) activity.findViewById(R.id.filter_sephia);
        assertNotNull(filterSephia);

        buttonSend = (Button) activity.findViewById(R.id.button_send);
        assertNotNull(buttonSend);

//        filterGrayScale.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FilterImage.convertToGrayScale(imageSrc);
//            }
//        });
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
