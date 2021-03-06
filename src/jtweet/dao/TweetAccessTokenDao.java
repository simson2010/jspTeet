package jtweet.dao;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;

import jtweet.dto.TweetAccessToken;
import jtweet.factory.PMF;

public class TweetAccessTokenDao {

	private PersistenceManagerFactory pmf = null;
	private PersistenceManager pm = null;
	
	public TweetAccessTokenDao(){
		pmf = PMF.get();
		pm = pmf.getPersistenceManager();
	}
	
	public void save(TweetAccessToken obj){
		try{
			pm.makePersistent(obj);
		} finally{
			pm.close();
		}
		
	}
	
	public TweetAccessToken getByKey(String key){
		String query = "select from " + TweetAccessToken.class.getName();
		query += " where key =='" + key + "' ";
		List<TweetAccessToken> list = (List<TweetAccessToken>)pm.newQuery(query).execute();
		
		if(list.isEmpty()){
			return null;
		}
		TweetAccessToken o = list.get(0);
		
		return o ;
				
	}
	
	public void deleteObject(TweetAccessToken obj){
		try{
			pm.deletePersistent(obj);
			
		}finally{
			pm.close();
		}
	}
	
}
