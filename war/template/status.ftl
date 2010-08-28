<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet - Status - ${status.id}</title>
	<link type="text/css" href="template/style.css" rel="stylesheet" />
</head>
<body class="sessions ${browser}" id="new">
	<div id="container" class="subpage">
		<#include "head.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="status_content" class="round column">
		<div id="status_warp">
			<span class="status_text">${status.html}</span>	
			<div class="fixed"></div>
			<span class="status_source">通过${status.source}</span>
			<#if status.inReplyToScreenName?trim != "">
				<a href="/status?id=${status.inReplyToStatusId?c}" class="status_reply_to">对${status.inReplyToScreenName}的回复</a>
			</#if>
			<span class="status_time">发表于${status.createdAt?datetime}</span>
		</div>
		<div id="status_user_warp">
			<img src="${status.user.biggerImageURL}" alt="${status.user.screenName}" class="user_img_big" />
			<div class="user_name_div">
				<a href="/user?id=${status.user.screenName}" class="status_user_screenname">${status.user.screenName}</a>
				<span class="status_user_name">${status.user.name}</span>
			</div>
		</div>
		<div class="fixed"></div>
	</td>

              
            </tr>
          </tbody>
        </table>


  <div id="footer" class="round">
		<#include "foot.ftl" />
	</div>
	<div class="fixed"></div>
</div>
<div class="fixed"></div>
<#include "analytics.ftl" />
</body>
</html>