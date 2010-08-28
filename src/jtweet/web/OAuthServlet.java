package jtweet.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.oauth.Configuration;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;

public class OAuthServlet extends JTweetServlet {
	private static final long serialVersionUID = 6214726194703668213L;
	static final Logger logger = Logger.getLogger(OAuthServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String oauth_verifier = req.getParameter("oauth_verifier");
		OAuthConsumer consumer = new DefaultOAuthConsumer(Configuration.getConsumerKey(), Configuration.getConsumerSecret());
		OAuthProvider provider = new DefaultOAuthProvider("https://twitter.com/oauth/request_token", "https://twitter.com/oauth/access_token", "https://twitter.com/oauth/authorize");
		Cookie[] cookies = req.getCookies();
		Cookie accountCookie = null;
		String[] accountString = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(JTweetServlet.ACCOUNT_COOKIE_NAME)) {
				accountCookie = cookie;
				break;
			}
		}
		if (accountCookie != null) {
			accountString = Encrypt.decodeAccount(accountCookie.getValue());
		}
		if (accountString != null) {
			String username = accountString[0];
			String token = accountString[2];
			String tokenSecret = accountString[3];
			consumer.setTokenWithSecret(token, tokenSecret);
			provider.setOAuth10a(true);
			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);
				String accessToken = consumer.getToken();
				String accessTokenSecret = consumer.getTokenSecret();
				accountCookie.setValue(Encrypt.encodeAccount(new String[] { username, "", accessToken, accessTokenSecret }));
				accountCookie.setMaxAge(7 * 24 * 3600);
				accountCookie.setPath("/");
				resp.addCookie(accountCookie);
				HttpSession session = req.getSession(true);
				session.setAttribute("username", username);
				session.setAttribute("accessToken", accessToken);
				session.setAttribute("accessTokenSecret", accessTokenSecret);

				resp.sendRedirect("/home");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
