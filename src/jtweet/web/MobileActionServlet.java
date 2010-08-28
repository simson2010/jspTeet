package jtweet.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jtweet.oauth.Utils;

import org.apache.oro.text.perl.Perl5Util; //import org.json.JSONException;
import org.json.simple.JSONObject;

import twitter4j.TwitterException;

@SuppressWarnings("serial")
public class MobileActionServlet extends JTweetServlet {
	protected boolean rst = false;

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doAction(req, resp);
	}

	public void doAction(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/x-javascript; charset=UTF-8");
		String action = req.getParameter("type");
		String id = req.getParameter("id");
		JSONObject json = new JSONObject();

		if (isLogin(req)) {
			if (Utils.isEmptyOrNull(getPasswd())) {
				twitterOAuth(getAccessToken(), getAccessTokenSecret(), req);
			} else {
				init_twitter(getUsername(), getPasswd(), req);
			}
			try {
				if (action.equalsIgnoreCase("post")) {
					String tweet = req.getParameter("tweet_msg");
					tweet = ShortURL(tweet);
					if (tweet.length() > 140) {
						tweet = tweet.substring(0, 139) + "…";
					}
					if (id != null) {
						try {
							long sid = Long.parseLong(id);
							twitter.updateStatus(tweet, sid);
						} catch (NumberFormatException e) {
							twitter.updateStatus(tweet);
						}
					} else {
						twitter.updateStatus(tweet);
					}
					rst = true;
					resp.sendRedirect("/mhome");
					return;
				} else if (action.equalsIgnoreCase("msg")) {
					String tweet = req.getParameter("tweet_msg");
					tweet = ShortURL(tweet);
					if (tweet.length() > 140) {
						tweet = tweet.substring(0, 139) + "…";
					}
					if (id != null) {
						twitter.sendDirectMessage(id, tweet);
						rst = true;
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("delmsg")) {
					if (id != null) {
						try {
							int sid = Integer.parseInt(id);
							twitter.destroyDirectMessage(sid);
							rst = true;
						} catch (NumberFormatException e) {
							json.put("info", "ID err");
						}
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("delete")) {
					if (id != null) {
						try {
							long sid = Long.parseLong(id);
							twitter.destroyStatus(sid);
							rst = true;
						} catch (NumberFormatException e) {
							json.put("info", "ID err");
						}
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("favor")) {
					if (id != null) {
						try {
							long sid = Long.parseLong(id);
							twitter.createFavorite(sid);
							rst = true;
						} catch (NumberFormatException e) {
							json.put("info", "ID err");
						}
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("unfavor")) {
					if (id != null) {
						try {
							long sid = Long.parseLong(id);
							twitter.destroyFavorite(sid);
							rst = true;
						} catch (NumberFormatException e) {
							json.put("info", "ID err");
						}
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("follow")) {
					if (id != null) {
						twitter.createFriendship(id);
						rst = true;
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("unfollow")) {
					if (id != null) {
						twitter.destroyFriendship(id);
						rst = true;
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("block")) {
					if (id != null) {
						twitter.createBlock(id);
						rst = true;
					} else {
						json.put("info", "ID err");
					}
				} else if (action.equalsIgnoreCase("unblock")) {
					if (id != null) {
						twitter.destroyBlock(id);
						rst = true;
					} else {
						json.put("info", "ID err");
					}
				} else {
					json.put("info", "Action type err.");
				}

			} catch (TwitterException e) {
				rst = false;
				// TODO Auto-generated catch block
				if (e.getStatusCode() == 400) {
					if (action.equalsIgnoreCase("delete")) {
						rst = true;
					} else {
						json.put("info", e.getStatusCode());
						e.printStackTrace();
					}
				} else {
					json.put("info", e.getStatusCode());
					e.printStackTrace();
				}
			}
		} else {
			json.put("info", "No login.");
		}

		if (rst) {
			json.put("result", "ok");
		} else {
			json.put("result", "err");
		}
		resp.getWriter().print(json.toJSONString());
	}

	protected String ShortURL(String text) {
		String rst = text;
		String url_reg = "m/\\b[a-zA-Z]+:\\/\\/[\\w_.\\-]+\\.[a-zA-Z]{2,6}[\\/\\w\\-~.?=&%#+$*!:;]*\\b/i";
		String temp = text;

		Perl5Util perl = new Perl5Util();
		while (perl.match(url_reg, temp)) {
			String url = perl.group(0);
			if (url.length() > 30) {
				String short_url = ShortURL.getIsgdURL(url);
				if (short_url != null)
					rst = rst.replace(url, short_url);
				/*
				 * try { rst = rst.replace(url, Bitly.getBitlyURL(url)); } catch
				 * (JSONException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); } catch (IOException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
			}
			temp = perl.postMatch();
		}

		return rst;
	}
}
