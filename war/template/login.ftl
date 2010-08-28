<?xml version="1.0" encoding="UTF-8" ?>
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>          
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="Twitter is without a doubt the best way to share and discover what is happening right now." name="description" />
<meta content="width = 780" name="viewport" />
<meta content="NOODP" name="robots" />
<meta content="n" name="session-loggedin" />
<title id="page_title">JTweet - 请登录</title>
<link href="/template/style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="/template/login.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
</head>
<body class="sessions ${browser}" id="new">
	<div id="container" class="subpage">
		<div id="header">
			<a href="/" title="Home" accesskey="1" id="logo"> <img alt="JTweet" height="36" src="/img/jtweet.gif" width="115" /> </a>
 			 <ul class="top-navigation round">
				<li><a href="/" accesskey="l">首页</a></li>
				<li class="signup-link"><a href="https://twitter.com/signup">加入Twitter!</a></li>
			</ul>
		</div>
  
		<div class="content-bubble-arrow"></div>
			<div id="login_warp" class="round">
				<div class="login_tip">
				<p>JTweet 为 Java 编写的 Twitter 在线客户端，运行于 GAE 之上。 OAuth处于测试阶段，支持OAuthProxy。密码留空为使用OAuth。 本程序为开源程序，关于安全性请前往<a href="http://code.google.com/p/javatweet/" target="_blank">项目主页</a>围观。</p><br />
				<p>JTweet 同时提供一个 API Proxy，可用于其他客户端。请在将客户端的 API BaseURL 设置为<b><a href="https://${server}/api" >http://${server}/api</a></b>。</p>
				</div>
				<form action="/login" id="loginform" method="post">
					<div class="input_div"><span class="login_text">用户名:</span><input type="text" id="username" name="username"/></div>
					<div class="input_div"><span class="login_text">密码:</span><input type="password" id="passwd" name="passwd"/></div>
				<div class="fixed"></div>
				<center>
					<label><input type="checkbox" id="stay" name="stayin" value="1"/>保持登录</label>
					<label><input type="checkbox" id="oauthproxy" name="oauthproxy" value="1"/>使用OAuthProxy</label>
				</center>
				<button type="submit" id="sub_button" class="login_button">登录</button>
				<button type="reset" id="reset_button" class="login_button">重置</button>
				</form>
			</div>
			<div class="fixed"></div>
		
		<div id="footer" class="round">
			<#include "foot.ftl" />
		</div>
		<hr />
	</div>
<div class="fixed"></div>
<#include "analytics.ftl" />
</body>
</html>
