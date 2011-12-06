package org.fukata.android.addeluxe.example;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class AdDeluxeAdView extends RelativeLayout {
	public static final String TAG = AdDeluxeAdView.class.getSimpleName();

	public Activity mActivity;
	public String mSiteId;
	
	public AdDeluxeAdView(Activity activity, String siteId) {
		super(activity);
		mActivity = activity;
		mSiteId = siteId;
		setVisibility(View.GONE);
		Log.d(TAG, "SiteId: " + mSiteId);
	}

	protected void onWindowVisibilityChanged(int visibility) {
		super.onWindowVisibilityChanged(visibility);
		if (visibility == View.VISIBLE) {
			loadAd();
		}
	}
	
	public void loadAd() {
		removeAllViewsInLayout();
		
		WebView view = new WebView(getContext());
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setGeolocationEnabled(true);
		view.getSettings().setSupportZoom(false);
		view.setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);
		view.setWebViewClient(new AdDeluxeWebViewClient());
		view.loadData(createAdHtml(), "text/html", "UTF-8");
		addView(view);
		setVisibility(View.VISIBLE);
	}
	
	String createAdHtml() {
		StringBuilder html = new StringBuilder();

		html.append("<!DOCTYPE HTML>");
		html.append("<html lang=\"ja\"><head>");
		html.append("<meta charset=\"UTF-8\">");
		html.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		html.append("<title>adDeluxe AD</title>");
		html.append("<style> * { padding:0;margin:0; } </style>");
		html.append("</head>");
		html.append("<body>");
		html.append("<script type=\"text/javascript\" language=\"javascript\">");
		html.append("var addeluxue_conf = {");
		html.append("site:\"").append(mSiteId).append("\"");
		html.append(",frame:2,width:730,height:132,color:[\"999999\",\"FFFFFF\",\"2200CC\",\"F25D5D\",\"671F28\"],host:'adv.addeluxe.jp',ver:1.4};</script>");
		html.append("<script type=\"text/javascript\" language=\"javascript\" src=\"http://adv.addeluxe.jp/js/iframe/adv.js\" charset=\"utf-8\"></script>");
		html.append("</body></html>");

		return html.toString();
	}
	
	class AdDeluxeWebViewClient extends WebViewClient {
		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			super.onReceivedError(view, errorCode, description, failingUrl);
			Log.w(TAG, description);
			setVisibility(View.GONE);
		}

		/**
		 * クリックされた場合に、遷移先URLをブラウザで開く
		 */
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "url=" + url);
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			mActivity.startActivity(intent);
			return true;
		}
	}
}
