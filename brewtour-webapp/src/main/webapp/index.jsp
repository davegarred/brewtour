<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width" />
<title>Seattle Beer Tour</title>
<script type="text/javascript" src="resources/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="resources/lib/angular/angular.min.js"></script>
<script type="text/javascript" src="resources/lib/bootstrap-3.3.5/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/lib/bootstrap-3.3.5/css/bootstrap.min.css">

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByxWmpMEVn7YBBNR4XU22VI8NqxsW_RGc"></script>

<script type="text/javascript" src="resources/js/app.js"></script>
<script type="text/javascript" src="resources/js/service.js"></script>
<script type="text/javascript" src="resources/js/directive.js"></script>

<link rel="stylesheet" type="text/css" href="resources/css/beertour.css">

<style>
html, body {
	height: 95%;
	margin: 0;
	padding: 0;
}
@media (max-width: 768px) {
    html, body {
    	height: 90%;
    }
}
#map {
	height: 100%;
}
</style>
</head>
<body ng-app="beertour">
	<ng-include src="'resources/html/main.html'"></ng-include>
</body>
</html>
