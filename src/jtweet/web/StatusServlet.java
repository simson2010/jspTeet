package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.oauth.Utils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import twitter4j.Status;
import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class StatusServlet extends JTweetServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		String sid = req.getParameter("id");

		if (isLogin(req) && sid != null) {
			if (Utils.isEmptyOrNull(getPasswd())) {
				twitterOAuth(getAccessToken(), getAccessTokenSecret(), req);
			} else {
				init_twitter(getUsername(), getPasswd(), req);
			}
			getStatus(sid, resp);
		} else {
			redirectLogin(req, resp);
		}
	}

	protected void getStatus(String sid, HttpServletResponse resp) throws IOException {
		try {
			long id = Long.parseLong(sid);
			HashMap<String, Object> root = new HashMap<String, Object>();
			freemarker.template.Configuration config = new freemarker.template.Configuration();
			config.setDirectoryForTemplateLoading(new File("template"));
			config.setDefaultEncoding("UTF-8");

			Status status = twitter.showStatus(id);
			root.put("status", status);
			root.put("browser", browser);

			Template t = config.getTemplate("status.ftl");
			t.process(root, resp.getWriter());
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			e.printStackTrace();
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
