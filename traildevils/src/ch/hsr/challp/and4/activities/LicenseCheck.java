package ch.hsr.challp.and4.activities;

/**
 * @author Nick Eubanks
 * 
 * Copyright (C) 2010 Android Infinity (http://www.androidinfinity.com)
 *
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.widget.Toast;

import com.android.vending.licensing.AESObfuscator;
import com.android.vending.licensing.LicenseChecker;
import com.android.vending.licensing.LicenseCheckerCallback;
import com.android.vending.licensing.ServerManagedPolicy;



/**
 * NOTES ON USING THIS LICENSE FILE IN YOUR APPLICATION: 1. Define the package
 * of you application above 2. Be sure your public key is set properly @BASE64_PUBLIC_KEY
 * 3. Change your SALT using random digits 4. Under AllowAccess, Add your
 * previously used MainActivity 5. Add this activity to your manifest and set
 * intent filters to MAIN and LAUNCHER 6. Remove Intent Filters from previous
 * main activity
 */
public class LicenseCheck extends Activity {
	
	private class MyLicenseCheckerCallback implements LicenseCheckerCallback {
		public void allow() {
			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}
			// Should allow user access.
			startMainActivity();

		}

		public void applicationError(ApplicationErrorCode errorCode) {
			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}
			// This is a polite way of saying the developer made a mistake
			// while setting up or calling the license checker library.
			// Please examine the error code and fix the error.
			toast("Error: " + errorCode.name());
			startMainActivity();

		}

		public void dontAllow() {
			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}

			// Should not allow access. In most cases, the app should assume
			// the user has access unless it encounters this. If it does,
			// the app should inform the user of their unlicensed ways
			// and then either shut down the app or limit the user to a
			// restricted set of features.
			// In this example, we show a dialog that takes the user to Market.
			showDialog(0);
		}
	}

	private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlACy0E7liDT1UYG5FncfBzPNOQj3Qwo2IyFm8oY6Bi+4eonV3v6nfN3EOwaf5Quz2gfq6spN78UDGZU0QNYxSB1qtffk7XzTW0HMuyq+Dq347+tBtAOTf4oyUpa6PK4jlo2pSToj7abF2Z2w0YhPepLyErEySn1okZh2KswOPDyrK/WM3odi2Y+7SFLhV6d5FxgGaF6p3r1n60Fy4f0b6nOiFORJVrCjhtzDyr6UCXdnz9i17KWxshxBy3ALByW7m8L8zN0KhcqhoUGJkQh01x6ZeT37VqM2d19WzjHRurFxG2X2mCJOrvhgqoBy7COzKvrPqWMaqxz2OqCO5zGXnQIDAQAB";

	private static final byte[] SALT = new byte[] { 20, 30, 40, 50, 60, 10, 30,
			-40, -50, 12, 34, 45, 32, -2, 3, -45, 54, -34, -90, -83 };
	private LicenseChecker mChecker;

	// A handler on the UI thread.

	private LicenseCheckerCallback mLicenseCheckerCallback;

	private void doCheck() {

		mChecker.checkAccess(mLicenseCheckerCallback);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Try to use more data here. ANDROID_ID is a single point of attack.
		String deviceId = Secure.getString(getContentResolver(),
				Secure.ANDROID_ID);

		// Library calls this when it's done.
		mLicenseCheckerCallback = new MyLicenseCheckerCallback();
		// Construct the LicenseChecker with a policy.
		mChecker = new LicenseChecker(this, new ServerManagedPolicy(this,
				new AESObfuscator(SALT, getPackageName(), deviceId)),
				BASE64_PUBLIC_KEY);
		doCheck();

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// We have only one dialog.
		return new AlertDialog.Builder(this)
				.setTitle("Application Not Licensed")
				.setCancelable(false)
				.setMessage(
						"This application is not licensed. Please purchase it from Android Market")
				.setPositiveButton("Buy App",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent marketIntent = new Intent(
										Intent.ACTION_VIEW,
										Uri.parse("http://market.android.com/details?id="
												+ getPackageName()));
								startActivity(marketIntent);
								finish();
							}
						})
				.setNegativeButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						}).create();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mChecker.onDestroy();
	}

	private void startMainActivity() {
		
		Intent ac = new Intent(".activities.StartScreen");
		startActivity(ac);
		
		
//		startActivity(new Intent(this, StartScreen.class)); 	
		
															// REPLACE
															// MainActivity.class
															// WITH YOUR APPS
															// ORIGINAL LAUNCH
															// ACTIVITY
		finish();
	}

	public void toast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

}