//package ch.hsr.challp.and4.technicalservices;
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.LinearLayout;
//
//import com.google.ads.AdRequest;
//import com.google.ads.AdSize;
//import com.google.ads.AdView;
//import com.google.ads.R;
//
//public class AdMobBanner extends Activity {
//  private AdView adView;
//
//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.main);
//
//    // Create the adView
//    adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
//
//    // Lookup your LinearLayout assuming it’s been given
//    // the attribute android:id="@+id/mainLayout"
//    LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
//
//    // Add the adView to it
//    layout.addView(adView);
//
//    // Initiate a generic request to load it with an ad
//    adView.loadAd(new AdRequest());
//  }
//
//  @Override
//  public void onDestroy() {
//    adView.destroy();
//    super.onDestroy();
//  }
//}
