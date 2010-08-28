package jtweet.apiproxy;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.*;
import com.google.appengine.api.urlfetch.*;


@SuppressWarnings("serial")
public class ApiServlet extends HttpServlet {
	
	protected String twbase = "https://twitter.com";
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String req_url = req.getRequestURI().substring(4);
		String query_string = req.getQueryString();

		if(req_url.isEmpty() || req_url.equalsIgnoreCase("/"))
		{
			resp.sendRedirect("/api.jsp");
		}
		else if(TRequest.getRequestType(req_url) == TRequest.NOTSUPPORT)
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
		else
		{
			
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest httpreq = new HTTPRequest(query_string == null ? new URL(twbase + req_url) : new URL(twbase + req_url + "?" + query_string), HTTPMethod.GET);
			
			Enumeration<String> ReqHeadersEnum = req.getHeaderNames();
			while(ReqHeadersEnum.hasMoreElements())
			{
				String HeaderName = ReqHeadersEnum.nextElement();
				String Header = req.getHeader(HeaderName);
				if(Header != null)
				{
					httpreq.addHeader(new HTTPHeader(HeaderName, Header));
				}
			}
			
			try
			{
				HTTPResponse httpresp = urlFetch.fetch(httpreq);
				if(httpresp.getResponseCode() == 401)
				{
					resp.setHeader("WWW-Authenticate","BASIC realm=\"Twitter.com realm\"");
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
				else
				{
					resp.getOutputStream().write(httpresp.getContent());
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
			}
			catch(IOException e)
			{
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				//e.printStackTrace();
			}
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String req_url = req.getRequestURI().substring(4);

		if(TRequest.getRequestType(req_url) == TRequest.NOTSUPPORT)
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
		else
		{
			URLFetchService urlFetch = URLFetchServiceFactory.getURLFetchService();
			HTTPRequest httpreq = new HTTPRequest(new URL(twbase + req_url), HTTPMethod.POST);
			
			Enumeration<String> ReqHeadersEnum = req.getHeaderNames();
			while(ReqHeadersEnum.hasMoreElements())
			{
				String HeaderName = ReqHeadersEnum.nextElement();
				String Header = req.getHeader(HeaderName);
				if(Header != null)
				{
					httpreq.addHeader(new HTTPHeader(HeaderName, Header));
				}
			}
			
			byte[] buf = new byte[req.getContentLength()];
			req.getInputStream().read(buf);
			httpreq.setPayload(buf);
			try
			{
				HTTPResponse httpresp = urlFetch.fetch(httpreq);
				
				if(httpresp.getResponseCode() == 401)
				{
					resp.setHeader("WWW-Authenticate","BASIC realm=\"Twitter.com realm\"");
					resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
				}
				else
				{
					resp.getOutputStream().write(httpresp.getContent());
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
			}
			catch(IOException e)
			{
				resp.sendError(HttpServletResponse.SC_NOT_FOUND);
				//e.printStackTrace();
			}
		}
	}
}
