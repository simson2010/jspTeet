var send_id = "";

$(document).ready(function() {
			window.setInterval(function() {
						// alert("update");
						if (autofresh)
							updateMessage();
						updateRate();
					}, 60000);
			window.setInterval(function() {
						updateCount();
					}, 300);
			flash_title();
		});

$("button#tweet_submit").live("click", function() {
			function callback(param) {
				$("div#statuses").slideUp("normal");
				send_id = "";
			}
			onSendMessage(send_id, callback);
		});

$("a.msg_action_reply").live("click", function() {
	send_id = $(this).parents("div.msg_content").children("span.msg_user")
			.text();
	$("div#statuses").slideDown("normal");
	$("#tweet_msg").val("d " + send_id + " ");
		// alert(send_id);
});

$("a.msg_action_del").live("click", function() {
			del_id = $(this).parents("div.msg_content").children("span.msg_id")
					.text();
			function callback(param) {
				param.parents("div.msgs").slideUp("normal");
				// alert(del_id);
			};
			onDeleteMessage(del_id, callback, $(this));
		});
