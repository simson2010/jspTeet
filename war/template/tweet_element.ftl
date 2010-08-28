<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list tweets as s>
	<div id="tweet_${s.id?c}" class="tweets">
	<div class="user_img_div"><img src="${s.profileImageUrl}" class="user_img" alt="${s.fromUser}" width ="48" height="48"/></div>
	<div class="tweet_content">
		<span class="tweet_user"><a href="/user?id=${s.fromUser}">${s.fromUser}</a></span>
		<span class="tweet_text">${s.html}</span>
		<div class="fixed"></div>
		<span class="tweet_info">
			<span class="tweet_source">通过${s.source}</span>
			<#if s.toUser?exists>
				<#if s.toUser?trim != "">
					<span class="tweet_reply_to">对<a href="/user?id=${s.toUser}">${s.toUser}</a>的回复</span>
				</#if>
			</#if>
			<span class="tweet_link"><a href="/status?id=${s.id?c}">发表于${s.createdAt?datetime}</a></span>
		</span>
		<span class="tweet_id">${s.id?c}</span>
		<div class="fixed"></div>
		<span class="tweet_action">
			<a href="javascript:void(0);" class="tweet_action_reply">回复</a>
			<a href="javascript:void(0);" class="tweet_action_quote">引用回复</a>
			<a href="javascript:void(0);" class="tweet_action_rt">锐推</a>			
			<a href="javascript:void(0);" class="tweet_action_favor">收藏</a>
			<#if user.screenName?lower_case == s.fromUser?lower_case>
				<a href="javascript:void(0);" class="tweet_action_del">删除</a>
			</#if>
		</span>
	</div>
	</div>
	<div class="fixed"></div>
</#list>