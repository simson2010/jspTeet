package jtweet.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import jtweet.oauth.Utils;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.org.json.JSONException;

public class RetweetByTwitterServlet extends JTweetServlet
{
	static final Logger logger = Logger.getLogger(RetweetByTwitterServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		String strId = req.getParameter("id");
		String strHome = req.getParameter("home");
		Status status = null;
		if (isLogin(req)) {
			if (Utils.isEmptyOrNull(getPasswd())) {
				twitterOAuth(getAccessToken(), getAccessTokenSecret(), req);
			} else {
				init_twitter(getUsername(), getPasswd(), req);
			}
		}else{
			resp.sendRedirect("/login");
		}
		logger.info("before retweet:"+strId);
		try
		{
			status = twitterRetweets(strId);
		}
		catch (TwitterException e)
		{	
			logger.warning(e.getMessage());
			
		}
		logger.info("after retweet:");
		if(status!=null){
			logger.info(""+status.getId());
		}
		
		resp.sendRedirect(strHome);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req,resp);
	}

	private Status twitterRetweets(String id) throws TwitterException{
		Long longId = Long.valueOf(id);
		Status status = null;
		try
		{
			status = twitter.retweetStatus(longId);
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
}
