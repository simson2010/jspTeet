package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.oauth.Utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class SearchServlet extends JTweetServlet {
	protected String s;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		s = req.getParameter("s");
		if (isLogin(req)) {
			if (Utils.isEmptyOrNull(getPasswd())) {
				twitterOAuth(getAccessToken(), getAccessTokenSecret(), req);
			} else {
				init_twitter(getUsername(), getPasswd(), req);
			}
			if (s.length() > 0) {
				getSearch(req, resp);
			} else {
				resp.sendRedirect("/home");
				return;
			}
		} else {
			redirectLogin(req, resp);
		}
	}

	protected void getSearch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		String p = req.getParameter("page");
		int page = 1;
		if (p != null) {
			try {
				page = Integer.parseInt(p);
			} catch (NumberFormatException e) {
				page = 1;
			}
		}

		Query query = new Query(s);
		query.setPage(page);

		try {
			QueryResult result = twitter.search(query);
			List<Tweet> tweets = result.getTweets();
			root.put("user", getTuser());
			root.put("search", s);
			root.put("browser", browser);
			root.put("addjs", "/js/search.js");
			root.put("rate", twitter.rateLimitStatus());
			root.put("page", page);
			root.put("tweets", tweets);

			Template t = config.getTemplate("search.ftl");
			t.process(root, resp.getWriter());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			resp.sendError(e.getStatusCode());
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
