package jtweet.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.oauth.Configuration;
import jtweet.gae.GCache;
import jtweet.oauth.Utils;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.google.appengine.repackaged.com.google.common.util.Base64;

@SuppressWarnings("serial")
public class JTweetServlet extends HttpServlet {
	protected Twitter twitter;
	protected Paging paging;
	protected String browser;
	private String username = null;
	private String passwd = null;
	private String accessToken = null;
	private String accessTokenSecret = null;
	public static final String ACCOUNT_COOKIE_NAME = "stay";

	public void init_twitter(String id, String passwd, HttpServletRequest req) {
		twitter = new Twitter(id, passwd);
		if (APIURL.useproxy) {
			twitter.setBaseURL(APIURL.url);
		}
		if (APIURL.usesearchproxy) {
			twitter.setSearchBaseURL(APIURL.search_url);
		}
		twitter.setSource("JTweet");
		twitter.setClientURL("http://code.google.com/p/javatweet/");
		twitter.setClientVersion("r52");
		twitter.setUserAgent(twitter.getSource() + " " + twitter.getClientURL() + " " + twitter.getClientVersion());
		paging = new Paging(1, 20);
		detectBrowser(req);
		
	}

	public void twitterOAuth(String accessToken, String accessTokenSecret, HttpServletRequest req) {
		twitter = new Twitter();
		twitter.setOAuthConsumer(Configuration.getConsumerKey(), Configuration.getConsumerSecret());
		twitter.setOAuthAccessToken(accessToken, accessTokenSecret);
		if (APIURL.useproxy) {
			twitter.setBaseURL(APIURL.url);
		}
		if (APIURL.usesearchproxy) {
			twitter.setSearchBaseURL(APIURL.search_url);
		}
		twitter.setSource("JTweet");
		twitter.setClientURL("http://code.google.com/p/javatweet/");
		twitter.setClientVersion("r52");
		twitter.setUserAgent(twitter.getSource() + " " + twitter.getClientURL() + " " + twitter.getClientVersion());
		paging = new Paging(1, 20);
		detectBrowser(req);
	}

	protected boolean isLogin(HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(3600);
		username = (String) session.getAttribute("username");
		passwd = (String) session.getAttribute("passwd");
		accessToken = (String) session.getAttribute("accessToken");
		accessTokenSecret = (String) session.getAttribute("accessTokenSecret");

		if (!Utils.isEmptyOrNull(accessToken) && !Utils.isEmptyOrNull(accessTokenSecret))
			return true;

		if (!Utils.isEmptyOrNull(passwd)) {
			try {
				passwd = new String(Base64.decode(passwd), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Cookie[] cookies = req.getCookies();
			Cookie accountCookie = null;
			if (cookies == null)
				return false;

			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(JTweetServlet.ACCOUNT_COOKIE_NAME)) {
					accountCookie = cookie;
					break;
				}
			}

			if (accountCookie == null || accountCookie.getValue() == null)
				return false;

			String[] accountString = Encrypt.decodeAccount(accountCookie.getValue());

			if (accountString == null)
				return false;
			username = accountString[0];
			passwd = accountString[1];
			accessToken = accountString[2];
			accessTokenSecret = accountString[3];
			String passwd_en;
			try {
				passwd_en = Base64.encode(passwd.getBytes("UTF-8"));
				session.setAttribute("username", username);
				session.setAttribute("passwd", passwd_en);
				session.setAttribute("accessToken", accessToken);
				session.setAttribute("accessTokenSecret", accessTokenSecret);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			session.setAttribute("username", username);
		}

		return true;
	}

	protected void redirectLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession(true);
		session.invalidate();
		Cookie cookie = new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		resp.addCookie(cookie);
		resp.sendRedirect("/login");
	}

	public String getUsername() {
		return username;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public User getTuser() throws TwitterException {
		User tuser = (User) GCache.get("user:" + getUsername());
		if (null == tuser) {
			tuser = twitter.verifyCredentials();
			GCache.put("user:" + getUsername(), tuser, 3600);
		}
		return tuser;
	}

	public String getBrowser(){
		return browser;
	}
	
	protected void detectBrowser(HttpServletRequest req){
		String _browser = "other";
		String UA = req.getHeader("User-Agent");
		if (UA == null) {
			browser = "NULL";
		} else if (UA.contains("MSIE 6.0")) {
			browser = "ie6";
		} else if (UA.contains("MSIE 7.0")) {
			browser = "ie7";		
		} else if (UA.contains("MSIE 8.0")){
			browser = "ie8";
		} else if (UA.contains("Opera Mini")){
			browser = "operamini";
		} else {
			browser = "other";
		}
				
	}
}
