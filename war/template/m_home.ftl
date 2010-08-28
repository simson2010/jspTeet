<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>JTweet - ${title} - ${user.screenName}</title>
	<link type="text/css" href="/template/style.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script> 
	<script type="text/javascript">
	<!--
	<#if page == 1>
		autofresh = true;
	<#else>
		autofresh = false;
	</#if>
	-->
	</script>
</head>
<body class="mobile ${browser}" id="new" >
	<div id="mcontainer" class="subpage">
		<#include "mhead.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="mcontent" class="round-left column">
                                <div class="mwrapper">
		<div id="statuses" class="statuses stsmobile">
			<span class="">你在做什么？ </span><span class="">剩余：<span id="tweet_count" style="color:green">140</span>字</span><br />
			<form id="f1" name="f1" action="/maction" method="post" style="display:block;">
			<textarea id="mtweet_msg" name="tweet_msg" class="mtweet_msg"></textarea><br />
			<input type="hidden" id="reply_id" name="id" value="0"></input>
			<input type="hidden" id="type_id" name="type" value="post"></input>
			<input type="submit" id="submit" class="minput" value="我推" ></input>
			<input type="reset" id="reset" class="minput" value="清除"></input>
			</form>
		</div>		
		<div id="tweet_warp">
			<#include "mstatus_element.ftl" /> 
		</div>
		<div id="mtweet_page">
			<#if page gt 1><a href="${uri}?page=${page - 1}" class="m_pre_page">上一页</a></#if>
			<#if status?size gt 1><a href="${uri}?page=${page + 1}" class="m_next_page">下一页</a></#if>
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