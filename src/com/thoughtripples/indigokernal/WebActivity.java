package com.thoughtripples.indigokernal;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.thoughtripples.indigokernal.utils.HtmlJSInterface;

public class WebActivity extends Activity implements Observer{

	WebView webview;
	ProgressBar prgbar;
	private String url;
	private String html;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_html);
		webview=(WebView) findViewById(R.id.webView1);
		prgbar=(ProgressBar) findViewById(R.id.progressBar1);
		prgbar.setVisibility(View.INVISIBLE);
		
		if(getIntent().hasExtra("url"));
			url=getIntent().getStringExtra("url");
		
//		webview.getSettings().setAppCacheMaxSize( 5 * 1024 * 1024 ); // 5MB
//		webview.getSettings().setAppCachePath( getApplicationContext().getCacheDir().getAbsolutePath() );
		webview.getSettings().setAllowFileAccess( true );
//		webview.getSettings().setAppCacheEnabled( true );
		webview.getSettings().setJavaScriptEnabled( true );
//		webview.getSettings().setCacheMode( WebSettings.LOAD_DEFAULT ); // load online by default

//		webview.getSettings().setCacheMode( WebSettings.LOAD_CACHE_ELSE_NETWORK );
		
		webview.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different scales.
				// The progress meter will automatically disappear when we reach 100%
				prgbar.setProgress(progress);
				if(progress==100){
					prgbar.setVisibility(View.GONE);
				}else{
					prgbar.setVisibility(View.VISIBLE);
				}
			
			}

		});
		webview.setWebViewClient(new MyWebViewClient());
		HtmlJSInterface htmlJSInterface = new HtmlJSInterface();
	    webview.addJavascriptInterface(htmlJSInterface, "HTMLOUT");
	    htmlJSInterface.addObserver(this);
		if(url!=null){
			String filename=url.substring(url.lastIndexOf("/")+1, url.lastIndexOf(".")).concat(".html");
//			try {
//				FileInputStream fis = openFileInput(filename);
//			
//				byte[] receivedata;
//				try {
//					receivedata = new byte[fis.available()];
//					String content = "<html><h1>Empty</h1></html>";
//				while (fis.read(receivedata) != -1) {
//					content = new String(receivedata);
//				}
//				Log.d("Nzm", "from file:"+content);
//				webview.loadData( content,  "text/html", null);					
//				} catch (IOException e) {
//					webview.loadUrl(url);
//				}
//			} catch (FileNotFoundException e1) {
//				webview.loadUrl(url);
//			}
			Log.d("Nzm", "read path:"+getFileStreamPath(filename).getAbsolutePath());
			File file= getFileStreamPath(filename);
//			webview.loadUrl("file:///"+file);
			webview.loadUrl("content://com.thoughtripples.indigokernal/"+filename);
		}
	}

	@Override
	public void update(Observable observable, Object observation) {
		if (observable instanceof HtmlJSInterface) {
	        html = (String) observation;
	        onHtmlChanged();
	    }
		
	}
	private void onHtmlChanged() {
		String filename=url.substring(url.lastIndexOf("/")+1, url.lastIndexOf("."));
	    Log.d("Nzm","file:"+filename);
	    try {
			FileOutputStream fos = openFileOutput(filename+".html", Context.MODE_PRIVATE);
			fos.write(html.getBytes());
			fos.close();
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class MyWebViewClient extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }

	    @Override
	    public void onPageFinished(WebView view, String url) {
	        // When each page is finished we're going to inject our custom
	        // JavaScript which allows us to
	        // communicate to the JS Interfaces. Responsible for sending full
	        // HTML over to the
	        // HtmlJSInterface...
	        view.loadUrl("javascript:(function() { "
	                + "window.HTMLOUT.setHtml('<html>'+"
	                + "document.getElementsByTagName('html')[0].innerHTML+'</html>');})();");
	        }
	    @Override
	    public void onReceivedError(WebView view, int errorCode,
	    		String description, String failingUrl) {
			webview.loadUrl(url);
	    }
	    }
}
