tweet_length = 0;
unread_count = 0;
title = document.title;
t = false;

$("textarea#tweet_msg").keypress(function(e) {
			if (e.ctrlKey && e.which == 13 || e.which == 10) {
				$("button#tweet_submit").click();
				document.body.focus();
			} else if (e.shiftKey && e.which == 13 || e.which == 10) {
				$("button#tweet_submit").click();
				document.body.focus();
			}
		});

$("div.unread").live("click", function() {
			$(this).removeClass("unread");
			unread_count = unread_count - 1;
		});

$("button#tweet_clear").live("click", function() {
			reply_id = 0;
			// send_id = "";
			$("#tweet_msg").val("");
			$("#tweet_msg").focus();
		});

function playMsg() {
	var ring = $.cookie("ring");
	if (ring == null || ring == "true") {
		var ringswf = document.getElementById("MsgRing");
		ringswf.Rewind();
		ringswf.Play();
	}
};

function updateUnread() {
	unread_count = $("div.unread").length;
	if (unread_count > 0)
		playMsg();
};

function flash_title() {
	if (unread_count > 0) {
		if (t) {
			document.title = "【" + unread_count + "未读】 " + title;
			t = false;
		} else {
			document.title = title;
			t = true;
		}
	} else {
		document.title = title;
	}
	setTimeout(flash_title, 1000);
};

function updateRate() {
	$.get("/update", {
				type : "rate",
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("div#side_rate_div").html(data);
			});
};

function updateCount() {
	tweet_length = $("textarea#tweet_msg").val().length;
	$("span#tweet_count").text(140 - tweet_length);
	$("span#tweet_count").toggleClass("tweet_count_red", tweet_length >= 140);
};

function updateHome() {
	sinceid = $("div#tweet_warp div.tweets:first-child")
			.children("div.tweet_content").children("span.tweet_id").text();
	// alert(sinceid);
	$.get("/update", {
				type : "home",
				since : sinceid,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("#tweet_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
			});
};

function updatePublic() {
	sinceid = $("div#tweet_warp div.tweets:first-child")
			.children("div.tweet_content").children("span.tweet_id").text();
	// alert(sinceid);
	$.get("/update", {
				type : "public",
				since : sinceid,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("#tweet_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
			});
};

function updateReply() {
	sinceid = $("div#tweet_warp div.tweets:first-child")
			.children("div.tweet_content").children("span.tweet_id").text();
	// alert(sinceid);
	$.get("/update", {
				type : "reply",
				since : sinceid,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("#tweet_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
			});
};

function updateMessage() {
	sinceid = $("div#msg_warp div.msgs:first-child")
			.children("div.msg_content").children("span.msg_id").text();
	// alert(sinceid);
	$.get("/update", {
				type : "message",
				since : sinceid,
				timestamp : (new Date()).getTime()
			}, function(data) {
				$("#msg_warp").prepend(data);
				$("div.newcome").slideDown("normal");
				$("div.newcome").removeClass("newcome");
				updateUnread();
			});
};

function onPostStatus(reply_id, callback, param) {
	if (tweet_length > 0) {
		var postdata;
		if (reply_id == 0)
			postdata = {
				type : "post",
				tweet_msg : $("#tweet_msg").val()
			};
		else
			postdata = {
				type : "post",
				tweet_msg : $("#tweet_msg").val(),
				id : reply_id
			};
		$.ajax({
					url : "/action",
					type : "POST",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.result == "ok") {
							// alert("post ok");
							$("#tweet_msg").val("");
							if (callback)
								callback(param);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	} else {
		alert("请勿发送空消息！");
	}
};

function onDelete(id, callback, param) {
	msg = "确实要删除ID为：" + id + "的Tweet吗？";
	if (confirm(msg)) {
		postdata = {
			type : "delete",
			id : id
		};
		$.ajax({
					url : "/action",
					type : "POST",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.result == "ok") {
							// alert("del ok");
							if (callback)
								callback(param);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function onFavor(id, callback, param) {
	postdata = {
		type : "favor",
		id : id
	};
	$.ajax({
				url : "/action",
				type : "POST",
				dataType : "json",
				data : postdata,
				success : function(json) {
					if (json.result == "ok") {
						// alert("favor ok");
						if (callback)
							callback(param);
					} else {
						alert("出错啦！错误代码：" + json.info);
					}
				}
			});
};

function onUnFavor(id, callback, param) {
	msg = "确实要删除对ID为：" + id + "的Tweet的收藏吗？";
	if (confirm(msg)) {
		postdata = {
			type : "unfavor",
			id : id
		};
		$.ajax({
					url : "/action",
					type : "POST",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.result == "ok") {
							// alert("unfavor ok");
							if (callback)
								callback(param);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function onFollow(id, callback, param) {
	postdata = {
		type : "follow",
		id : id
	};

	$.ajax({
				url : "/action",
				type : "POST",
				dataType : "json",
				data : postdata,
				success : function(json) {
					if (json.result == "ok") {
						// alert("follow ok");
						if (callback)
							callback(param);
					} else {
						alert("出错啦！错误代码：" + json.info);
					}
				}
			});
};

function onUnFollow(id, callback, param) {
	msg = "确实要取消对" + id + "的跟踪吗？";

	if (confirm(msg)) {
		postdata = {
			type : "unfollow",
			id : id
		};
		$.ajax({
					url : "/action",
					type : "POST",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.result == "ok") {
							// alert("unfollow ok");
							if (callback)
								callback(param);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function onBlock(id, callback, param) {
	msg = "确实要屏蔽" + id + "吗？";

	if (confirm(msg)) {
		postdata = {
			type : "block",
			id : id
		};
		$.ajax({
					url : "/action",
					type : "POST",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.result == "ok") {
							// alert("block ok");
							if (callback)
								callback(param);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function onUnBlock(id, callback, param) {
	msg = "确实要取消对" + id + "的屏蔽吗？";

	if (confirm(msg)) {
		postdata = {
			type : "unblock",
			id : id
		};
		$.ajax({
					url : "/action",
					type : "POST",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.result == "ok") {
							// alert("unblock ok");
							if (callback)
								callback(param);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function onSendMessage(id, callback, param) {
	if (tweet_length > 0) {
		var postdata;
		if (send_id != "") {
			postdata = {
				type : "msg",
				tweet_msg : $("#tweet_msg").val(),
				id : send_id
			};
			$.ajax({
						url : "/action",
						type : "POST",
						dataType : "json",
						data : postdata,
						success : function(json) {
							if (json.result == "ok") {
								// alert("post ok");
								$("#tweet_msg").val("");
								if (callback)
									callback(param);
							} else {
								alert("出错啦！错误代码：" + json.info);
							}
						}
					});
		} else {
			alert("请指定收件人");
		}
	} else {
		alert("请勿发送空消息");
	}
};

function onDeleteMessage(id, callback, param) {
	msg = "确实要删除ID为：" + id + "的消息吗？";
	if (confirm(msg)) {
		postdata = {
			type : "delmsg",
			id : id
		};
		$.ajax({
					url : "/action",
					type : "POST",
					dataType : "json",
					data : postdata,
					success : function(json) {
						if (json.result == "ok") {
							// alert("del ok");
							if (callback)
								callback(param);
						} else {
							alert("出错啦！错误代码：" + json.info);
						}
					}
				});
	}
};

function markallread() {
	if (unread_count > 0) {
		$("div.unread").removeClass("unread");
		unread_count = 0;
	}
};