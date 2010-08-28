<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title> Hello,
		Jtweet,Twitter API proxy in Java on GAE.
		</title>
	</head>
	<body>
		<div style="width:99%;">
		<h1>Jtweet,Twitter API proxy in Java on GAE.</h1>
		<p>This is a Twitter API proxy,and is not intend to be viewed in a browser.
		<br />
		Please use <b>http<%if(request.isSecure()) out.print("s"); %>://
		<% out.print(request.getServerName()); %>
		/api</b> as a Twitter API URI in your Twitter Client.
		</div>

	</body>
</html>