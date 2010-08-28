$(document).ready(function() {
			window.setInterval(function() {
						updateRate();
					}, 6000);
		});

function checkext(fname) {
	if (fname.length == 0) {
		alert("请选择要上传的文件");
		return false;
	}
	var accept = false;
	var exts = new Array(".gif", ".jpg", ".png", ".jpeg");
	var pos = fname.lastIndexOf(".");
	var ext = fname.substring(pos, fname.length);
	for (var i = 0; i < exts.length; i++) {
		if (ext.toLowerCase() == exts[i]) {
			accept = true;
			break;
		}
	}
	if (!accept) {
		alert("只允许上传gif，jpg，png格式的图片");
		return false;
	} else
		return true;
};
