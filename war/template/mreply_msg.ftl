<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>JTweet - ${title} - ${user.screenName} - Reply</title>
	<link type="text/css" href="/template/style.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script> 
	
</head>
<body class="mobile ${browser}" id="new" style="width:99%;padding-left:1px;">
	<div id="mcontainer" class="subpage">
		<#include "mhead.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="mcontent" class="round-left column">
                                <div class="mwrapper">
		<div id="statuses" class="statuses stsmobile">
			<span class="">剩余：<span id="tweet_count" style="color:green">140</span>字</span><br />
			<div id="orgStatusText" class="tweet_text" >
				${status.html}
			</div>
			<form id="f1" name="f1" action="/maction" method="post" style="display:block;">
			<textarea id="mtweet_msg" name="tweet_msg" class="mtweet_msg"></textarea><br />
			<input type="hidden" id="reply_id" name="id" value="${reply_id}"></input>
			<input type="hidden" id="type_id" name="type" value="msg"></input>			
			<input type="hidden" id="senderId" name="senderId" value="${senderId}"></input>
			<input type="submit" id="submit" class="minput" value="我推" ></input>
			<input type="reset" id="reset" class="minput" value="清除"></input>
			</form>
		</div>		

	</div>
              </td>
            </tr>
          </tbody>
        </table>

</div>
<div>
	<#include "mfoot.ftl"/>
</div>
</body>
</html>