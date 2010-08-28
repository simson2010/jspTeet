<?xml version="1.0" encoding="UTF-8" ?>
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>          
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta content="Twitter is without a doubt the best way to share and discover what is happening right now." name="description" />
<meta content="NOODP" name="robots" />
<meta content="n" name="session-loggedin" />
<title id="page_title">JTweet - 请登录</title>
<link href="/template/style.css" media="screen" rel="stylesheet" type="text/css" />
<link href="/template/mlogin.css" media="screen" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/js/jquery-1.3.2.min.js" ></script>
</head>
<body class="sessions ${browser}" id="new">
	<div id="container" class="subpage">
		  
		<div class="content-bubble-arrow"></div>
			<div id="login_warp" class="round">				
				<form action="/login" id="loginform" method="post">
					<div class="input_div"><span class="login_text">用户名:</span><input type="text" id="username" name="username"/></div>
					<div class="input_div"><span class="login_text">密码:</span><input type="password" id="passwd" name="passwd"/></div>
				<div class="fixed"></div>
				<center>
					<label><input type="checkbox" id="stay" name="stayin" value="1"/>保持登录</label>
					<label><input type="checkbox" id="oauthproxy" name="oauthproxy" value="1"/>使用OAuthProxy</label>
				</center>
				<input type="submit" id="sub_button" class="login_button" value='登录'></input>
				<input type="reset" id="reset_button" class="login_button" value='重置'></input>
				</form>
			</div>
			
		
		<hr />
	</div>
<div class="fixed"></div>
</body>
</html>
