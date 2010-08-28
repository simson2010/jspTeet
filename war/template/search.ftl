<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet - 搜索 ${search} - ${user.screenName}</title>
	<link type="text/css" href="/template/style.css" rel="stylesheet" />
	<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
	<script type="text/javascript" src="/js/jquery.cookie.js" ></script>
</head>
<body class="sessions ${browser}" id="new">
	<div id="container" class="subpage">
		<#include "head.ftl" /> 
	
	<table cellspacing="0" class="columns">
          <tbody>
            <tr>
              <td id="content" class="round-left column">
                                <div class="wrapper">
	<div id="statuses" class="statuses hide">
			<span class="tweet_tip">你在做什么？ 按Ctrl+Enter快捷发布</span><span class="tweet_count_info">剩余：<span id="tweet_count" class="tweet_count_green">140</span>字</span><br />
			<textarea id="tweet_msg" name="tweet_msg"></textarea><br />
			<button id="tweet_submit">我推！</button>
			<button id="tweet_clear">清除</button>
		</div>
		<div class="fixed"></div>
		<div id="tweet_warp">
			<#include "tweet_element.ftl" /> 
		</div>
		<div class="fixed"></div>
		<div id="tweet_page">
			<#if page gt 1><a href="/search?s=${search}&page=${page - 1}" class="pre_page">上一页</a></#if>
			<#if tweets?size gt 14><a href="/search?s=${search}&page=${page + 1}" class="next_page">下一页</a></#if>
		</div>
	</div>
	</td>
              
                <td id="side_base" class="column round-right">
                                  
                  <div id="side">
		<#include "side.ftl" />
	</div>
	</td>

              
            </tr>
          </tbody>
        </table>


  <div id="footer" class="round">
		<#include "foot.ftl" />
	</div>
	<div class="fixed"></div>
</div>
<script type="text/javascript" src="/js/func.js"></script>
<#if addjs?exists>
	<script type="text/javascript" src="${addjs}"></script>
</#if>
<div class="fixed"></div>
<#include "analytics.ftl" />
</body>
</html>