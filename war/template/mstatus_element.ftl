<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list status as s>
	<div id="tweet_${s.id?c}" class="mtweets<#if addclass?exists> ${addclass}<#if s.user.screenName?lower_case != user.screenName?lower_case> unread</#if></#if>">
	<div class="muser_img_div"><img src="${s.user.profileImageURL}" class="user_img" alt="${s.user.screenName}" width ="48" height="48"/></div>
	<div class="mtweet_content">
		<span class="tweet_user"><a href="/user?id=${s.user.screenName}">${s.user.screenName}</a></span>
		<span class="tweet_text">${s.html}</span>
		<div class="fixed"></div>
		<span class="tweet_info">
			<span class="tweet_source">通过${s.source}</span>
			<#if s.inReplyToScreenName?trim != "">
				<span class="tweet_reply_to"><a href="/status?id=${s.inReplyToStatusId?c}">对${s.inReplyToScreenName}的回复</a></span>
			</#if>
			<span class="tweet_link"><a href="/status?id=${s.id?c}">发表于${s.createdAt?datetime}</a></span>
		</span>
		<span class="tweet_id">${s.id?c}</span>
		<div class="fixed"></div>
		<span class="tweet_action">
			<a href="/mat?id=${s.id?c}&quote=0" class="tweet_action_reply">回复</a>
			<a href="/mat?id=${s.id?c}&quote=1" class="tweet_action_quote">引用回复</a>
			<a href="/mtrt?id=${s.id?c}&home=/mhome" class="tweet_action_rt">锐推</a>
			<#if s.favorited>
				<a href="/maction?type=unfavor&id=${s.id?c}" class="tweet_action_unfavor">取消收藏</a>			
			<#else>
				<a href="/maction?type=favor&id=${s.id?c}" class="tweet_action_favor">收藏</a>
			</#if>
			<#if user.screenName?lower_case == s.user.screenName?lower_case>
				<a href="/maction?type=delete&id=${s.id?c}" class="tweet_action_del">删除</a>
			</#if>
		</span>
	</div>
	</div>
	<div class="fixed"></div>
</#list>