package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.gae.GCache;
import jtweet.oauth.Utils;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class MobileHomeServlet extends JTweetServlet {
	static final Logger logger = Logger.getLogger(MobileHomeServlet.class.getName());
	protected String uri;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		uri = req.getRequestURI();
		String action = uri.substring(1);
		String page = req.getParameter("page");
		detectBrowser(req);
		logger.info("Browser:"+browser);
		
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

			if (action.equalsIgnoreCase("mhome")) {
				getHomeTimeline(resp);
			} else if (action.equalsIgnoreCase("mreply")) {
				getReplyTimeline(resp);
			} else if (action.equalsIgnoreCase("mfavor")) {
				getFavorTimeline(resp);
			} else if (action.equalsIgnoreCase("mmessage")) {
				getMsgTimeline(resp);
			} else if (action.equalsIgnoreCase("mpublic")) {
				getPubTimeline(resp);
			} else if (action.equalsIgnoreCase("moutbox")) {
				getOutboxTimeline(resp);
			} else {
				resp.sendRedirect("/mhome");
			}
		} else {
			redirectLogin(req, resp);
		}
	}

	protected void getHomeTimeline(HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			List<Status> status = twitter.getFriendsTimeline(paging);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("title", "时间线");
			root.put("browser", browser);
			root.put("addjs", "/js/home.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("status", status);
			Template t = config.getTemplate("m_home.ftl");
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

	protected void getReplyTimeline(HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			List<Status> status = twitter.getMentions(paging);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("title", "回复");
			root.put("browser", browser);
			root.put("addjs", "/js/reply.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("status", status);
			Template t = config.getTemplate("m_home.ftl");
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

	@SuppressWarnings("unchecked")
	protected void getPubTimeline(HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			List<Status> status = (List<Status>) GCache.get("pub," + paging.getPage());
			if (null == status) {
				//status = twitter.getPublicTimeline(paging);
				status = twitter.getFriendsTimeline(paging);
				GCache.put("pub," + paging.getPage(), status, 120);
			}

			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("title", "公共页面");
			root.put("browser", browser);
			root.put("addjs", "/js/public.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("status", status);
			Template t = config.getTemplate("m_home.ftl");
			t.process(root, resp.getWriter());

		} catch (TwitterException e) {
			logger.log(Level.SEVERE, e.getMessage());
			resp.sendError(e.getStatusCode());
		} catch (TemplateException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}

	protected void getFavorTimeline(HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			List<Status> status = twitter.getFavorites(paging.getPage());
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("title", "收藏");
			root.put("browser", browser);
			root.put("addjs", "/js/favor.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("status", status);
			Template t = config.getTemplate("m_home.ftl");
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

	protected void getMsgTimeline(HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			List<DirectMessage> msg = twitter.getDirectMessages(paging);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("title", "消息");
			root.put("browser", browser);
			root.put("addjs", "/js/message.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("msg", msg);
			Template t = config.getTemplate("mmessage.ftl");
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

	protected void getOutboxTimeline(HttpServletResponse resp) throws IOException {
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			List<DirectMessage> msg = twitter.getSentDirectMessages(paging);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("title", "发件箱");
			root.put("browser", browser);
			root.put("addjs", "/js/outbox.js");
			root.put("uri", uri);
			root.put("page", paging.getPage());
			root.put("msg", msg);
			Template t = config.getTemplate("mmessage.ftl");
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