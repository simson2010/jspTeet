var reply_id = 0;
var send_id = "";

$(document).ready(function() {
			window.setInterval(function() {
						updateCount();
					}, 300);
			window.setInterval(function() {
						updateRate();
					}, 6000);
		});

$("a.tweet_action_reply").live("click", function() {
	reply_id = $(this).parents("div.tweets").children("span.tweet_id").text();
	$("button#tweet_submit").text("我推！");
	oringal_msg = $("#tweet_msg").val();
	$("#tweet_msg").val("@" + $("span#user_name").text() + " " + oringal_msg);
	$("button#tweet_submit").click(function() {
				function callback() {
					$("div.user_form").slideUp("normal");
					reply_id = 0;
					send_id = "";
				}
				onPostStatus(reply_id, callback);
			});
	$("div.user_form").slideDown("normal");
	$("#tweet_msg").focus();
		// alert(reply_id);
	});

$("a.tweet_action_quote").live("click", function() {
	reply_id = $(this).parents("div.tweets").children("span.tweet_id").text();
	$("button#tweet_submit").text("我推！");
	$("#tweet_msg").val("RT @" + $("span#user_name").text() + " "
			+ $(this).parents("div.tweets").children("span.tweet_text").text()
			+ "");
	$("button#tweet_submit").click(function() {
				function callback() {
					$("div.user_form").slideUp("normal");
					reply_id = 0;
					send_id = "";
				}
				onPostStatus(reply_id, callback);
			});
	$("div.user_form").slideDown("normal");
	$("#tweet_msg").focus();
		// alert(reply_id);
});

$("a.tweet_action_rt").live("click", function() {
	reply_id = 0;
	$("button#tweet_submit").text("我推！");
	$("#tweet_msg").val("RT @" + $("span#user_name").text() + " "
			+ $(this).parents("div.tweets").children("span.tweet_text").text()
			+ "");
	$("button#tweet_submit").click(function() {
				function callback() {
					$("div.user_form").slideUp("normal");
					reply_id = 0;
					send_id = "";
				}
				onPostStatus(reply_id, callback);
			});
	$("div.user_form").slideDown("normal");
	$("#tweet_msg").focus();
		// alert(reply_id);
});

$("a.tweet_action_del").live("click", function() {
			del_id = $(this).parents("div.tweets").children("span.tweet_id")
					.text();
			function callback(param) {
				param.parents("div.tweets").slideUp("normal");
				// alert(del_id);
			};
			onDelete(del_id, callback, $(this));
		});

$("a.tweet_action_favor").live("click", function() {
			favor_id = $(this).parents("div.tweets").children("span.tweet_id")
					.text();
			function callback(param) {
				param.text("取消收藏");
				param.attr("class", "tweet_action_unfavor");
				// alert(favor_id);
			};
			onFavor(favor_id, callback, $(this));
		});

$("a.tweet_action_unfavor").live("click", function() {
			favor_id = $(this).parents("div.tweets").children("span.tweet_id")
					.text();
			function callback(param) {
				param.text("收藏");
				param.attr("class", "tweet_action_favor");
				// alert(favor_id);
			};
			onUnFavor(favor_id, callback, $(this));
		});

$("a.user_action_msg").live("click", function() {
	send_id = $("span#user_name").text();
	$("button#tweet_submit").text("发送！");
	$("#tweet_msg").val("d " + $("span#user_name").text() + " ");
	$("button#tweet_submit").click(function() {
				function callback() {
					$("div.user_form").slideUp("normal");
				}
				onSendMessage(send_id, callback);
			});
	$("div.user_form").slideDown("normal");
	$("#tweet_msg").focus();
		// alert(send_id);
	});

$("a.user_action_follow").live("click", function() {
			send_id = $("span#user_name").text();
			function callback(param) {
				param.text("取消关注");
				param.attr("class", "user_action_unfollow");
				// alert(send_id);
			};
			onFollow(send_id, callback, $(this));
		});

$("a.user_action_unfollow").live("click", function() {
			send_id = $("span#user_name").text();
			function callback(param) {
				param.text("关注");
				param.attr("class", "user_action_follow");
				// alert(send_id);
			};
			onUnFollow(send_id, callback, $(this));
		});

$("a.user_action_block").live("click", function() {
			send_id = $("span#user_name").text();
			function callback(param) {
				param.text("取消屏蔽");
				param.attr("class", "user_action_unblock");
				// alert(send_id);
			};
			onBlock(send_id, callback, $(this));
		});

$("a.user_action_unblock").live("click", function() {
			send_id = $("span#user_name").text();
			function callback(param) {
				param.text("屏蔽");
				param.attr("class", "user_action_block");
				// alert(send_id);
			};
			onUnBlock(send_id, callback, $(this));
		});
