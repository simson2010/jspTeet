package jtweet.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class ShortURL {

	public static String getLongURL(String url) {
		String short_url = null;
		String url_en;
		try {
			url_en = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			url_en = url;
			e.printStackTrace();
		}
		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq;
		try {
			httpreq = new HTTPRequest(new URL("http://untiny.me/api/1.0/extract/?format=text&url=" + url_en), HTTPMethod.GET);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			if (httpresp.getResponseCode() == 200) {
				String rst = new String(httpresp.getContent(), "UTF-8");
				if (!rst.startsWith("error")) {
					short_url = rst;
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return short_url;

	}

	public static String getRealURL(String url) {
		String short_url = null;
		String url_en;
		try {
			url_en = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			url_en = url;
			e.printStackTrace();
		}
		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq;
		try {
			httpreq = new HTTPRequest(new URL("http://Realurl.net/api/?Url=" + url_en), HTTPMethod.GET);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			if (httpresp.getResponseCode() == 200) {
				String rst = new String(httpresp.getContent(), "UTF-8");
				if (!(rst.startsWith("Error") || rst.startsWith("Bad Url"))) {
					short_url = rst;
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return short_url;

	}

	public static String getIsgdURL(String url) {
		String short_url = null;
		String url_en;
		try {
			url_en = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			url_en = url;
			e.printStackTrace();
		}
		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq;
		try {
			httpreq = new HTTPRequest(new URL("http://is.gd/api.php?longurl=" + url_en), HTTPMethod.GET);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			if (httpresp.getResponseCode() == 200) {
				short_url = new String(httpresp.getContent(), "UTF-8");
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return short_url;

	}

	public static String getTinyURL(String url) {
		String short_url = null;
		String url_en;
		try {
			url_en = URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			url_en = url;
			e.printStackTrace();
		}
		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq;
		try {
			httpreq = new HTTPRequest(new URL("http://tinyurl.com/api-create.php?url=" + url_en), HTTPMethod.GET);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			if (httpresp.getResponseCode() == 200) {
				String rst = new String(httpresp.getContent(), "UTF-8");
				if (!rst.equalsIgnoreCase("Error"))
					short_url = rst;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return short_url;

	}

}
