var send_id = "";

$(document).ready(function() {
			window.setInterval(function() {
						updateCount();
					}, 300);
			window.setInterval(function() {
						updateRate();
					}, 6000);
		});

$("button#tweet_submit").live("click", function() {
			function callback(param) {
				$("div#statuses").slideUp("normal");
				send_id = "";
			}
			onSendMessage(send_id, callback);
		});

$("a.follow_action_msg").live("click", function() {
	send_id = $(this).parents("div.follow_content")
			.children("span.follow_name").text();
	$("div#statuses").slideDown("normal");
	$("#tweet_msg").val("d " + send_id + " ");
	$("#tweet_msg").focus();
		// alert(send_id);
});
$("a.user_action_follow").live("click", function() {
	send_id = $(this).parents("div.follow_content")
			.children("span.follow_name").text();
	function callback(param) {
		param.text("取消关注");
		param.attr("class", "user_action_unfollow");
		// alert(send_id);
	};
	onFollow(send_id, callback, $(this));
});

$("a.user_action_unfollow").live("click", function() {
	send_id = $(this).parents("div.follow_content")
			.children("span.follow_name").text();
	function callback(param) {
		param.text("关注");
		param.attr("class", "user_action_follow");
		// alert(send_id);
	};
	onUnFollow(send_id, callback, $(this));
});

$("a.user_action_block").live("click", function() {
	send_id = $(this).parents("div.follow_content")
			.children("span.follow_name").text();
	function callback(param) {
		param.text("取消屏蔽");
		param.attr("class", "user_action_unblock");
		// alert(send_id);
	};
	onBlock(send_id, callback, $(this));
});

$("a.user_action_unblock").live("click", function() {
	send_id = $(this).parents("div.follow_content")
			.children("span.follow_name").text();
	function callback(param) {
		param.text("屏蔽");
		param.attr("class", "user_action_block");
		// alert(send_id);
	};
	onUnBlock(send_id, callback, $(this));
});