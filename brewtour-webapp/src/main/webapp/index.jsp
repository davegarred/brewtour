<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Seattle Brewery Tour</title>
<script type="text/javascript" src="resources/lib/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="resources/lib/bootstrap.3.3.4.min.js"></script>
<script type="text/javascript" src="resources/lib/angular.1.4.0-rc.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="resources/lib/bootstrap.3.3.4.css">

<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByxWmpMEVn7YBBNR4XU22VI8NqxsW_RGc"></script>
<script type="text/javascript" src="resources/app.js"></script>

<style type="text/css">
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}

#map {
	height: 100%;
}
</style>

</head>
<body ng-app="brewtour">
	<div ng-controller="MapController">
		<div>
			<div class="hidden-md hidden-lg" ng-if="focusedLocation">{{focusedLocation}}</div>
			<div class="hidden-xs hidden-sm" ng-if="focusedLocation" style="float: left; width: 200px;">{{focusedLocation}}</div>
			<div id="map"></div>
		</div>
	</div>
</body>
</html>
