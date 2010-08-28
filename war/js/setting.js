$(document).ready(function() {
			var ring_radio = $(":radio[name='ring']");
			var ring = $.cookie("ring");
			// alert(ring);
			if (ring == null || ring == "true")
				ring_radio[0].checked = "true";
			else
				ring_radio[1].checked = "true";
		});

function onJTweetSetting() {
	var ring = $(":radio:checked[name='ring']").val();
	$.cookie("ring", ring, {
				expires : 7
			});
	alert("设置保存成功");
};
