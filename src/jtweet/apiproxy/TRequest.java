package jtweet.apiproxy;

public class TRequest {
	
	static public int JSON = 1;
	static public int XML = 2;
	static public int NOTSUPPORT = -1;
	
	//request type
	public static int getRequestType(String req_url)
	{
		if(req_url.endsWith(".json")) return JSON;
		else if(req_url.endsWith(".xml") ||
				req_url.endsWith(".rss") ||
				req_url.endsWith(".atom"))
			return XML;
		else return NOTSUPPORT;
	}
	
	//search?
	public static Boolean isSearch(String req_url)
	{
		if(req_url.startsWith("/search") || req_url.startsWith("/trends")) return true;
		else return false;
	}
}
