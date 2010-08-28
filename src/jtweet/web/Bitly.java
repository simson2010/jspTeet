package jtweet.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class Bitly {
	public static String URLregex = "((https?|ftp|gopher|telnet|file|notes|ms-help):((//)|(\\\\\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
	public static String[][] loginKeyPairs = { { "yulei666", "R_a210b36d1e8c8c549b6ef32a5ed67950" }, { "bitlybot", "R_d618b34d2d3944dc76e1c4e88b09fd08" }, { "bitlybot2", "R_788978474267b52019da5cceba23b349" } };

	public static String process(String message) throws JSONException, IOException {
		Pattern pattern = null;
		Matcher matcher = null;
		String longurl = null;
		String bitlyurl = null;
		boolean matches = false;
		String replacement = null;
		StringBuffer sb = new StringBuffer();
		pattern = Pattern.compile(URLregex, Pattern.DOTALL);
		matcher = pattern.matcher(message);
		while (matcher.find()) {
			matches = true;
			longurl = matcher.group(1);
			bitlyurl = getBitlyURL(longurl);
			if (bitlyurl == null) {
				// return replacement;
				bitlyurl = longurl;
				// matches=false;
				// break;
			}
			matcher.appendReplacement(sb, bitlyurl);
		}
		matcher.appendTail(sb);
		replacement = matches ? sb.toString() : null;
		return replacement;
	}

	public static String getBitlyURL(String longurl) throws JSONException, IOException {
		String bitlyurl = null;
		String RESTurl = null;
		JSONObject json = null;
		try {

			int id = 0;
			while (id < 3) {
				RESTurl = "http://api.j.mp/shorten?version=2.0.1" + "&login=" + loginKeyPairs[id][0] + "&apiKey=" + loginKeyPairs[id][1] + "&longUrl=" + URLEncoder.encode(longurl, "UTF-8");
				json = new JSONObject(fetchUrl(RESTurl));
				if ("OK".equals(json.getString("statusCode"))) {
					JSONObject output = json.getJSONObject("results").getJSONObject(longurl);
					if (output.has("shortUrl"))
						bitlyurl = output.getString("shortUrl");
					else
						System.err.println("Some Error:" + json.toString());
					return bitlyurl;
				} else {
					++id;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			if (json != null)
				System.err.println(json.toString());
			throw e;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
		return bitlyurl;

	}

	public static String fetchUrl(String url) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		StringBuilder result = new StringBuilder();
		try {
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				result.append(inputLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
		}
		return result.toString();
	}
}
