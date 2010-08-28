package jtweet.web;

import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

@SuppressWarnings("serial")
public class PicThumb extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		
		String id = req.getParameter("id");
		if(id != null)
		{
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest httpreq = new HTTPRequest(new URL("http://twitpic.com/show/thumb/" + id), HTTPMethod.GET);
			HTTPResponse httpresp = urlFetch.fetch(httpreq);
			if(httpresp.getResponseCode() == 200)
			{
				for(HTTPHeader h : httpresp.getHeaders())
				{
					resp.setHeader(h.getName(), h.getValue());
				}
				resp.getOutputStream().write(httpresp.getContent());
			}
			else
			{
				resp.sendError(httpresp.getResponseCode());
				return;
			}
		}
		else
		{
			resp.sendError(404);
			return;
		}
	}

}
