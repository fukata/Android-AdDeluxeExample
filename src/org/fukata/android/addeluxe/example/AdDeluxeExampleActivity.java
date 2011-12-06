package org.fukata.android.addeluxe.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class AdDeluxeExampleActivity extends Activity {
	
	public static final String ADDELUXE_SITE_ID = "919";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        AdDeluxeAdView adView = new AdDeluxeAdView(this, ADDELUXE_SITE_ID);
        RelativeLayout adLayout = (RelativeLayout) findViewById(R.id.ad);
        adLayout.addView(adView);
        adView.loadAd();
    }
}