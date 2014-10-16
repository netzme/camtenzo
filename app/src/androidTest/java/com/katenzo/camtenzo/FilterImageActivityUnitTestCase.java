package com.katenzo.camtenzo;

import android.content.Intent;
import android.net.Uri;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by daori on 10/8/14.
 */
public class FilterImageActivityUnitTestCase extends ActivityUnitTestCase<FilterImageActivity> {

    private static final String extra = "com.katenzo.camtenzo.EXTRA_FILTER";
    private static final String uriMessage = "wowz";

    private Intent intent;

    private FilterImageActivity activity;


    public FilterImageActivityUnitTestCase(String name) {
        super(FilterImageActivity.class);
        this.setName(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        intent = new Intent();
        intent.putExtra(extra, uriMessage);

        startActivity(intent, null, null);
        activity = getActivity();

    }

    @SmallTest
    public void testIntentShouldNotEmpty() throws Exception {
        Intent intent = activity.getIntent();
        assertNotNull("Intent was NULL", intent);

        String message = intent.getExtras().getString(extra);
        assertEquals("Incorrect uri passed via Intent",uriMessage, message);
    }

    @SmallTest
    public void testFilterImageActivityShouldHaveSameImageWithProvidedUriFromIntent() throws Exception {
        ImageView imageView = (ImageView) activity.findViewById(R.id.image_container);
        imageView.setImageURI(Uri.parse(uriMessage));
        assertNotNull("Image View Cannot null",imageView);

        Intent intent = activity.getIntent();
        final Uri expected = Uri.parse(intent.getExtras().getString(extra));
        assertEquals("Image Uri Not Same", expected, Uri.parse(uriMessage));
    }
}
