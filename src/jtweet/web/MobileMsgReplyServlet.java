package jtweet.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.oauth.Utils;

import twitter4j.Status;
import twitter4j.TwitterException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@SuppressWarnings("serial")
public class MobileMsgReplyServlet extends JTweetServlet
{
	static final Logger logger = Logger.getLogger(MobileMsgReplyServlet.class.getName());
	String reply_id = "0";
	String with_quote = "0";
	String senderId = "0";
	String dmId = "0";
	
	Status reply_Status = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		reply_id = req.getParameter("id"); // in here == screen_name
		senderId = req.getParameter("senderId"); // in here == sender userId as int
		dmId = req.getParameter("dmId"); // as directMessage Id as int
		long intReply_Id = Long.valueOf(dmId);
		logger.info("reply_id:"+intReply_Id);
		if (isLogin(req) && reply_id != null) {
			if (Utils.isEmptyOrNull(getPasswd())) {
				twitterOAuth(getAccessToken(), getAccessTokenSecret(), req);
			} else {
				init_twitter(getUsername(), getPasswd(), req);
			}
			getStatus(dmId, resp);
			logger.info("Finished getStatus();");
		} else {
			redirectLogin(req, resp);
		}
		
		with_quote = req.getParameter("quote");
		ReplyTo(resp);
		
	}

	protected void ReplyTo(HttpServletResponse resp) throws IOException
	{
		HashMap<String, Object> root = new HashMap<String, Object>();
		freemarker.template.Configuration config = new freemarker.template.Configuration();
		config.setDirectoryForTemplateLoading(new File("template"));
		config.setDefaultEncoding("UTF-8");

		try {
			//List<Status> status = twitter.getFriendsTimeline(paging);
			root.put("user", getTuser());
			root.put("rate", twitter.rateLimitStatus());
			root.put("title", "回复"+reply_id);
			root.put("browser", browser);
			root.put("quote",with_quote);
			root.put("page", paging.getPage());
			root.put("status", reply_Status);
			root.put("reply_id", reply_id);
			root.put("senderId", senderId);
			Template t = config.getTemplate("mreply_msg.ftl");
			t.process(root, resp.getWriter());

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			resp.sendError(e.getStatusCode());
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			logger.warning(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doGet(req,resp);
	}

	protected void getStatus(String sid, HttpServletResponse resp) throws IOException {
		try {
			long id = Long.parseLong(sid);
			
			reply_Status = twitter.showStatus(id);
			
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			resp.sendError(e.getStatusCode());
			e.printStackTrace();
		} 
	}
}
