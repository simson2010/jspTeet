package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jtweet.oauth.RequestToken;
import jtweet.oauth.Utils;
import twitter4j.TwitterException;

import com.google.appengine.repackaged.com.google.common.util.Base64;

import freemarker.template.Template;
import freemarker.template.TemplateException;

public class LoginServlet extends JTweetServlet {
	private static final long serialVersionUID = 3283053002737403576L;
	static final Logger logger = Logger.getLogger(LoginServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String action = req.getRequestURI().substring(1);

		if (action.equalsIgnoreCase("login")) {
			if (isLogin(req)) {
				resp.sendRedirect("/home");
				return;
			}
		} else if (action.equalsIgnoreCase("logout")) {
			redirectLogin(req, resp);
			return;
		} else {
			redirectLogin(req, resp);
			return;
		}

		String UA = req.getHeader("User-Agent");
		if (UA == null) {
			browser = "other";
		} else if (UA.contains("MSIE 6.0")) {
			browser = "ie6";
		} else if (UA.contains("Opera Mini")){
			browser = "operamini";
		}else if (UA.contains("MSIE 7.0")) {		
			browser = "ie7";
		} else {
			browser = "other";
		}

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");
		root.put("browser", browser);
		root.put("server", req.getServerName());
		String templateName = "login.ftl";
		if(browser.equalsIgnoreCase("operamini")){
			templateName = "mlogin.ftl";
		}
		logger.info("UA:"+UA);
		logger.info("browser:"+browser);
		logger.info("template Name:"+templateName);
		Template t = config.getTemplate(templateName);
		try {
			t.process(root, resp.getWriter());
		} catch (TemplateException e) {
						
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String action = req.getRequestURI().substring(1);
		String username = req.getParameter("username");
		String passwd = req.getParameter("passwd");
		String stayin = req.getParameter("stayin");
		String oauthproxy = req.getParameter("oauthproxy");

		if (action.equalsIgnoreCase("login")) {
			if (Utils.isEmptyOrNull(username))
				redirectLogin(req, resp);
			HttpSession session = req.getSession(true);
			session.setMaxInactiveInterval(3600);

			if (!Utils.isEmptyOrNull(passwd)) {// normal
				String passwd_en = Base64.encode(passwd.getBytes("UTF-8"));
				init_twitter(username, passwd, req);
				try {
					twitter.verifyCredentials();
					session.setAttribute("username", username);
					session.setAttribute("passwd", passwd_en);
					// 在cookie中存储加密账户信息
					if (null != stayin && stayin.equals("1")) {
						Cookie cookie = new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME, Encrypt.encodeAccount(username, passwd));
						cookie.setMaxAge(7 * 24 * 3600);
						cookie.setPath("/");
						resp.addCookie(cookie);
					}
					resp.sendRedirect("/home");
				} catch (TwitterException e) {
					redirectLogin(req, resp);
					e.printStackTrace();
				}
			} else {// oauth
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
					String accessToken = accountString[2];
					String accessTokenSecret = accountString[3];
					twitterOAuth(accessToken, accessTokenSecret, req);
					try {
						twitter.verifyCredentials();
						session.setAttribute("accessToken", accessToken);
						session.setAttribute("accessTokenSecret", accessTokenSecret);
						resp.sendRedirect("/home");
					} catch (TwitterException e) {
						logger.log(Level.SEVERE, e.getMessage());
						redirectLogin(req, resp);
					}
				} else {
					String callbackURL = Utils.getBaseURL(req) + "/oauth/";
					try {
						RequestToken requestToken = new RequestToken(callbackURL);
						String authUrl = requestToken.getAuthUrl();
						String token = requestToken.getToken();
						String tokenSecret = requestToken.getTokenSecret();
						Cookie cookie = new Cookie(JTweetServlet.ACCOUNT_COOKIE_NAME, Encrypt.encodeAccount(new String[] { username, "", token, tokenSecret }));
						cookie.setMaxAge(7 * 24 * 3600);
						cookie.setPath("/");
						resp.addCookie(cookie);
						if (null != oauthproxy && oauthproxy.equals("1"))
						{
							authUrl = authUrl.replaceFirst("https://twitter.com/oauth/authorize", "/oauth/authorize");
						}
						resp.sendRedirect(authUrl);
					} catch (Exception e) {
						logger.log(Level.SEVERE, e.getMessage());
						redirectLogin(req, resp);
					}
				}
			}
		}
	}
}
