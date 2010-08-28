<div class="side_user_info">
	<div class="side_user_img_div">
		<img alt="${user.screenName}" src="${user.profileImageURL}" class="usr_img" width ="48" height="48" />
	</div>
	<div class="side_user_name">
		<span id="side_user_screenname">${user.screenName}</span><br />
		<span id="side_user_name">(${user.name})</span>
	</div>

</div>
<div class="fixed"></div>
<div class="side_user_count">
	<a href="/following" class="side_user_link"><span class="side_count">${user.friendsCount}</span><br/><span class="side_tip">朋友</span></a>
	<a href="/follower" class="side_user_link"><span class="side_count">${user.followersCount}</span><br/><span class="side_tip">关注者</span></a>
	<a href="/user?id=${user.screenName}" class="side_user_link side_user_link_last"><span class="side_count">${user.statusesCount}</span><br/><span class="side_tip">推</span></a>
</div>
<div class="fixed"></div>
<div class="side_link_content">
	<a href="/home" class="side_link">时间线</a>
	<a href="/user?id=${user.screenName}" class="side_link">我的推</a>
	<a href="/reply" class="side_link">@${user.screenName}</a>
	<a href="/message" class="side_link">消息</a>
	<a href="/outbox" class="side_link">发件箱</a>
	<a href="/favor" class="side_link">我的收藏</a>
	<a href="/block" class="side_link side_link_last">屏蔽列表</a>
</div>
<div class="fixed"></div>
<form action="/search" method="get" id="searchform">
	<input type="text" name="s" class="searchtext"/>
	<button type="submit">搜索</button>
</form>
<div class="fixed"></div>
<#if rate?exists>
	<div id="side_rate_div">
		<#include "rate.ftl" />
	</div>
	<div class="fixed"></div>
	<a href="javascript:void((function(){var n=navigator.userAgent.toLowerCase();ie=n.indexOf('msie')!=-1?1:0;if(document.documentMode)ie=0;charset='';if(ie)charset=document.charset;src=ie&&charset=='utf-8'?'http://web.pinyin.sogou.com/web_ime/init2_utf8.php':'http://web.pinyin.sogou.com/web_ime/init2.php';element=document.createElement('script');element.setAttribute('src',src);document.body.appendChild(element);})());" title="开启搜狗云输入法" id="sogou_ime"><img src="/img/sogou.png" alt="开启搜狗云输入法" /></a>
</#if>
