var reply_id = 0;

$(document).ready(function() {
			window.setInterval(function() {
						updateCount();
					}, 300);
			window.setInterval(function() {
						updateRate();
					}, 6000);
		});

$("button#tweet_submit").live("click", function() {
			onPostStatus(reply_id);
		});

$("a.tweet_action_reply").live("click", function() {
	reply_id = $(this).parents("div.tweet_content").children("span.tweet_id")
			.text();
	oringal_msg = $("#tweet_msg").val();
	$("#tweet_msg").val("@"
			+ $(this).parents("div.tweet_content").children("span.tweet_user")
					.text() + " " + oringal_msg);
	$("#tweet_msg").focus();
		// alert(reply_id);
});

$("a.tweet_action_quote").live("click", function() {
	reply_id = $(this).parents("div.tweet_content").children("span.tweet_id")
			.text();
	$("#tweet_msg").val("RT @"
			+ $(this).parents("div.tweet_content").children("span.tweet_user")
					.text()
			+ " "
			+ $(this).parents("div.tweet_content").children("span.tweet_text")
					.text() + "");
	$("#tweet_msg").focus();
		// alert(reply_id);
});

$("a.tweet_action_rt").live("click", function() {
	reply_id = 0;
	$("#tweet_msg").val("RT @"
			+ $(this).parents("div.tweet_content").children("span.tweet_user")
					.text()
			+ " "
			+ $(this).parents("div.tweet_content").children("span.tweet_text")
					.text() + "");
	$("#tweet_msg").focus();
		// alert(reply_id);
});

$("a.tweet_action_del").live("click", function() {
	del_id = $(this).parents("div.tweet_content").children("span.tweet_id")
			.text();
	function callback(param) {
		param.parents("div.tweets").slideUp("normal");
		// alert(del_id);
	};
	onDelete(del_id, callback, $(this));
});

$("a.tweet_action_unfavor").live("click", function() {
	favor_id = $(this).parents("div.tweet_content").children("span.tweet_id")
			.text();
	function callback(param) {
		param.parents("div.tweets").slideUp("normal");
		// alert(favor_id);
	};
	onUnFavor(favor_id, callback, $(this));
});
