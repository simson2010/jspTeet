package jtweet.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class Twitpic {
	
	protected String mediaid = null;
	protected int errcode = 0;
	protected String errmsg = null;
	
	public Twitpic(byte[] resp)
	{
        DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder dombuilder = domfac.newDocumentBuilder();
			ByteArrayInputStream bis = new ByteArrayInputStream(resp);
			Document doc = dombuilder.parse(bis);
			Element root = doc.getDocumentElement();
			String rsp = root.getAttribute("status");
			if(rsp.equalsIgnoreCase("ok"))
			{
				if(root.getElementsByTagName("mediaid").getLength() > 0)
				{
					mediaid = root.getElementsByTagName("mediaid").item(0).getTextContent();
				}
				else
				{
					errcode = -1;
					errmsg = "Other Error.";
				}
			}
			else
			{
				if(root.getElementsByTagName("err").getLength() > 0)
				{
					Node errnode = root.getElementsByTagName("err").item(0);
					errcode = Integer.parseInt(errnode.getAttributes().getNamedItem("code").getNodeValue());
					errmsg = errnode.getAttributes().getNamedItem("msg").getNodeValue();
				}
				else
				{
					errcode = -1;
					errmsg = "Other Error.";
				}
				
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			errcode = -1;
			errmsg = "Other Error.";
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			errcode = -1;
			errmsg = "Other Error.";
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errcode = -1;
			errmsg = "Other Error.";
			e.printStackTrace();
		}
	}
	
	public String getMediaid()
	{
		return mediaid;
	}
	
	public int getErrcode()
	{
		return errcode;
	}
	
	public String getErrmsg()
	{
		return errmsg;
	}
	
	public boolean isok()
	{
		if(errcode == 0) return true;
		else return false;
	}

}
