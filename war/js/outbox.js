var send_id = "";

$("a.msg_action_del").live("click", function() {
			del_id = $(this).parents("div.msg_content").children("span.msg_id")
					.text();
			function callback(param) {
				param.parents("div.msgs").slideUp("normal");
				// alert(del_id);
			};
			onDeleteMessage(del_id, callback, $(this));
		});
