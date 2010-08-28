package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.oauth.Utils;

import twitter4j.DirectMessage;
import twitter4j.Status;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class UpdateServlet extends JTweetServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String type = req.getParameter("type");
		String since = req.getParameter("since");

		if (isLogin(req)) {
			if (Utils.isEmptyOrNull(getPasswd())) {
				twitterOAuth(getAccessToken(), getAccessTokenSecret(), req);
			} else {
				init_twitter(getUsername(), getPasswd(), req);
			}

			if (since != null) {
				try {
					long sinceid = Long.parseLong(since);
					paging.setSinceId(sinceid);
				} catch (NumberFormatException e) {
				}
			}

			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("template"));
			config.setDefaultEncoding("UTF-8");
			root.put("addclass", "newcome");

			try {
				root.put("user", getTuser());
				Template t = null;
				if (type.equalsIgnoreCase("home")) {
					List<Status> status = twitter.getFriendsTimeline(paging);
					root.put("status", status);
					t = config.getTemplate("status_element.ftl");
				} else if (type.equalsIgnoreCase("reply")) {
					List<Status> status = twitter.getMentions(paging);
					root.put("status", status);
					t = config.getTemplate("status_element.ftl");
				} else if (type.equalsIgnoreCase("message")) {
					List<DirectMessage> msg = twitter.getDirectMessages(paging);
					root.put("msg", msg);
					t = config.getTemplate("message_element.ftl");
				} else if (type.equalsIgnoreCase("public")) {
					//List<Status> status = twitter.getPublicTimeline(paging);
					List<Status> status = twitter.getFriendsTimeline(paging);
					root.put("status", status);
					t = config.getTemplate("status_element.ftl");
				} else if (type.equalsIgnoreCase("rate")) {
					root.put("rate", twitter.rateLimitStatus());
					t = config.getTemplate("rate.ftl");
				}

				if (t != null)
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
}
