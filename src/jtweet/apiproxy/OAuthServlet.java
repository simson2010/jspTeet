package jtweet.apiproxy;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.*;
import com.google.appengine.api.urlfetch.*;


@SuppressWarnings("serial")
public class OAuthServlet extends HttpServlet {
	
	protected String twurl = "https://twitter.com/oauth/authorize";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String query_string = req.getQueryString();

		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq = new HTTPRequest(query_string == null ? new URL(twurl) : new URL(twurl + "?" + query_string), HTTPMethod.GET);
		
		try
		{
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			String respcon = new String(httpresp.getContent(), "UTF-8");
			respcon = respcon.replaceAll(twurl, "/oauth/authorize");
			String reg = "<body(.)+?>";
			Pattern p=Pattern.compile(reg);
			Matcher m=p.matcher(respcon);
			if(m.find())
			{
				String bodystart = m.group();
				String alertstr = bodystart + "<div style=\"font-size:24px;color:red;background:white;text-align:center;font-family:simsun,WenQuanYi Zen Hei,sans-serif;\">！注意！：您正在使用OAuthProxy，请确认搭建者为您信任的人！</div>";
				respcon = respcon.replace(bodystart, alertstr);
			}
			resp.getOutputStream().write(respcon.getBytes("UTF-8"));
			for (HTTPHeader h : httpresp.getHeaders())
			{
				if(h.getName().equalsIgnoreCase("Set-Cookie"))
				{
					resp.setHeader("Set-Cookie", h.getValue().replaceAll(".twitter.com", req.getServerName()));
				}
				else if(!h.getName().equalsIgnoreCase("Content-length"))
				{
					resp.setHeader(h.getName(), h.getValue());
				}
			}
			//return err code
			if(httpresp.getResponseCode() != 200) resp.sendError(httpresp.getResponseCode());
		}
		catch(IOException e)
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			//e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String query_string = req.getQueryString();

		URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
		HTTPRequest httpreq = new HTTPRequest(query_string == null ? new URL(twurl) : new URL(twurl + "?" + query_string), HTTPMethod.POST);
		
		byte[] postpayload = new byte[req.getContentLength()];
		req.getInputStream().read(postpayload);
		httpreq.setPayload(postpayload);
		
		try
		{
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			String respcon = new String(httpresp.getContent(), "UTF-8");
			respcon = respcon.replaceAll(twurl, "/oauth/authorize");
			String reg = "<body(.)+?>";
			Pattern p=Pattern.compile(reg);
			Matcher m=p.matcher(respcon);
			if(m.find())
			{
				String bodystart = m.group();
				String alertstr = bodystart + "<div style=\"font-size:24px;color:red;background:white;text-align:center;font-family:simsun,WenQuanYi Zen Hei,sans-serif;\">！注意！：您正在使用OAuthProxy，请确认搭建者为您信任的人！</div>";
				respcon = respcon.replace(bodystart, alertstr);
			}
			resp.getOutputStream().write(respcon.getBytes("UTF-8"));
			for (HTTPHeader h : httpresp.getHeaders())
			{
				if(h.getName().equalsIgnoreCase("Set-Cookie"))
				{
					resp.setHeader("Set-Cookie", h.getValue().replaceAll(".twitter.com", req.getServerName()));
				}
				else if(!h.getName().equalsIgnoreCase("Content-length"))
				{
					resp.setHeader(h.getName(), h.getValue());
				}
			}
			//return err code
			if(httpresp.getResponseCode() != 200) resp.sendError(httpresp.getResponseCode());
		}
		catch(IOException e)
		{
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			//e.printStackTrace();
		}
	}
}

