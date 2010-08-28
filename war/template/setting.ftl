<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>JTweet - 设置 - ${user.screenName}</title>
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
		<#if msg?exists><center><div id="setting_rst">${msg}</div></center></#if>
		<div id="setting_img">
			<img src="${user.biggerImageURL}" alt="${user.screenName}" class="user_img_big" width ="73" height="73"/>
			<form action="/setimg" method="post" enctype="multipart/form-data" id="img_form">
				<div class="img_tip">必须为GIF，JPG或PNG格式，大小小于700KB。 </div>
				<div class="img_tip">由于GAE URLFetch的缓存机制，新上传的头像并不会在JTweet上马上显示。 </div>
				<input type="file" name="image"/>
				<button type="submit" onclick="javascript:return checkext(this.form.image.value);">上传图片</button>
			</form>
		</div>
		<div id="setting_info">
			<form action="/setting" method="post" id="info_form">
				<div class="setting_div">昵称：<input type="text" id="name" name="name" class="info_input" value="${user.name}" maxlength="20" /><span class="setting_tip">最多20字</span></div>
				<div class="setting_div">网址：<input type="text" id="url" name="url" class="info_input" value="${user.URL?default("")}" maxlength="100" /><span class="setting_tip">最多100字</span></div>
				<div class="setting_div">位置：<input type="text" id="location" name="location" class="info_input" value="${user.location}" maxlength="30" /><span class="setting_tip">最多30字</span></div>
				<div class="setting_div">简介：<textarea id="description" name="description" class="info_text" >${user.description}</textarea><span class="setting_tip">最多160字</span></div>
				<center><button type="submit" id="setting_sub">修改</button></center>
			</form>
		</div>
		<div id="jtweet_setting">
			<div class="setting_div">
				新消息声音提醒:
				<input type="radio" value="true" name="ring">是</input>
				<input type="radio" value="false" name="ring">否</input>
				<span class="setting_tip">Cookie保存，有效期7天。</span>
			</div>
			<center><button id="jtweet_sub" onclick="javascript:onJTweetSetting();">保存</button></center>
		</div>
	</div>
	</td>
              
                <td id="side_base" class="column round-right">
                                  
                  <div id="side">
		<#include "side.ftl" />
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
<script type="text/javascript" src="/js/func.js" ></script>
<script type="text/javascript" src="/js/setting.js" ></script>
<script type="text/javascript" src="/js/checkext.js" ></script>
<div class="fixed"></div>
<#include "analytics.ftl" />
</body>
</html>