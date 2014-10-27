package com.thoughtripples.indigokernal.utils;

import java.util.Observable;

import android.webkit.JavascriptInterface;

public class HtmlJSInterface extends Observable {
	  private String html;

	  /**
	   * @return The most recent HTML received by the interface
	   */
	  public String getHtml() {
	    return this.html;
	  }

	  /**
	   * Sets most recent HTML and notifies observers.
	   * 
	   * @param html
	   *          The full HTML of a page
	   */
	  @JavascriptInterface
	  public void setHtml(String html) {
	    this.html = html;
	    setChanged();
	    notifyObservers(html);
	  }
	}
