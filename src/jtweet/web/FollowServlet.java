package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.oauth.Utils;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class FollowServlet extends JTweetServlet {
	protected String uri;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String uid = req.getParameter("id");
		uri = req.getRequestURI();
		String action = uri.substring(1);
		String page = req.getParameter("page");

		if (isLogin(req)) {
			if (Utils.isEmptyOrNull(getPasswd())) {
				twitterOAuth(getAccessToken(), getAccessTokenSecret(), req);
			} else {
				init_twitter(getUsername(), getPasswd(), req);
			}
			if (page != null) {
				try {
					int p = Integer.parseInt(page);
					if (p > 0)
						paging.setPage(p);
					else {
						resp.sendRedirect(uri);
						return;
					}
				} catch (NumberFormatException e) {
					resp.sendRedirect(uri);
					return;
				}
			}
			if (action.equalsIgnoreCase("follower")) {
				getFollower(uid, resp);
			} else if (action.equalsIgnoreCase("following")) {
				getFollowing(uid, resp);
			} else if (action.equalsIgnoreCase("block")) {
				getBlock(uid, resp);
			}
		} else {
			redirectLogin(req, resp);
		}
	}

	protected void getFollower(String uid, HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<User> follower;
		try {
			root.put("title", "关注者");
			root.put("browser", browser);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			if (uid == null || uid.equalsIgnoreCase(getTuser().getScreenName())) {
				root.put("user_show", getTuser());
				follower = twitter.getFollowersStatuses();
				root.put("follow", follower);
			} else {
				User user = twitter.showUser(uid);
				root.put("user_show", user);
				if ((!user.isProtected()) || user.getFollowing()) {
					follower = twitter.getFollowersStatuses(uid);
					root.put("follow", follower);
				}
			}
			root.put("uri", uri);
			root.put("page", paging.getPage());

			Template t = config.getTemplate("follow.ftl");
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

	protected void getFollowing(String uid, HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<User> following;
		try {
			root.put("title", "朋友");
			root.put("browser", browser);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			if (uid == null || uid.equalsIgnoreCase(getTuser().getScreenName())) {
				root.put("user_show", getTuser());
				following = twitter.getFriendsStatuses();
				root.put("follow", following);
			} else {
				User user = twitter.showUser(uid);
				root.put("user_show", user);
				if ((!user.isProtected()) || user.getFollowing()) {
					following = twitter.getFriendsStatuses(uid);
					root.put("follow", following);
				}
			}
			root.put("uri", uri);
			root.put("page", paging.getPage());

			Template t = config.getTemplate("follow.ftl");
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

	protected void getBlock(String uid, HttpServletResponse resp) throws IOException {
		if (uid != null) {
			resp.sendRedirect("/block");
			return;
		}

		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		List<User> block;
		try {
			root.put("title", "屏蔽列表");
			root.put("browser", browser);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("user_show", getTuser());
			block = twitter.getBlockingUsers(paging.getPage());
			root.put("follow", block);
			root.put("uri", uri);
			root.put("page", paging.getPage());

			Template t = config.getTemplate("follow.ftl");
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
