<#setting time_zone="GMT+8">
<#setting datetime_format="yyyy.M.d,H:m:s">
<#list msg as m>
	<div id="msg_${m.id?c}" class="msgs<#if addclass?exists> ${addclass}<#if m.senderScreenName?lower_case != user.screenName?lower_case> unread</#if></#if>">
	<div class="user_img_div"><img src="${m.sender.profileImageURL}" class="user_img" alt="${m.senderScreenName}" width ="48" height="48"/></div>
	<div class="msg_content">
		<span class="msg_user"><a href="/user?id=${m.senderScreenName}">${m.senderScreenName}</a></span>
		<span class="msg_text">${m.html}</span>
		<div class="fixed"></div>
		<span class="msg_info">
			<#if m.senderScreenName?lower_case == user.screenName?lower_case><span class="msg_to">发送至:<a href="/user?id=${m.recipientScreenName}" class="msg_sendto">${m.recipientScreenName}</a> </span></#if>
			<span class="msg_time">发送于${m.createdAt?datetime}</span>
		</span>
		<span class="msg_id">${m.id?c}</span>
		<div class="fixed"></div>
		<span class="msg_action">
			<#if m.senderScreenName?lower_case != user.screenName?lower_case><a href="javascript:void(0);" class="msg_action_reply">回复</a></#if>
			<a href="javascript:void(0);" class="msg_action_del">删除</a>
		</span>
	</div>
	</div>
	<div class="fixed"></div>
</#list>