'use strict';
(function (angular) {
    var brewtour = angular.module('brewtour', [ ]);

    brewtour.controller('MapController', ['$scope', '$http', function ($scope, $http) {
    	
//    	$scope.focusedLocation = true;
    	
    	$http.get('resources/locations.json')
    	.then(function successCallback(response) {
    		$scope.locations = response.data;
    		initMap();
    	}, function errorCallback(response) {
    		debugger;
    		
    	});
    	
        function initMap() {
        	$scope.map = new google.maps.Map(document.getElementById('map'), {
        	    center: {lat: 47.61, lng: -122.333},
        	    zoom: 12
        	  });

        	  for(var l in $scope.locations) {
        	    var location = $scope.locations[l];
        	    var locationPosition = {lat: location.latitude, lng: location.longitude};
        	    var details = {
        	        position: locationPosition,
        	        title: location.breweryName
        	    };
        	    var marker = new google.maps.Marker(details);
        	    marker.setMap($scope.map);

        	    var infoWindowImage = location.images ? "<p><img src='" + location.images.large + "'></p>" : "";
        	    var infoWindowDescription = location.breweryDescription ? "<p>" + location.breweryDescription + "</p>" : "";
        	    var infoWindowContent = infoWindowImage + "<h3>" + location.breweryName + "</h3>" + infoWindowDescription;
        	    bindInfoWindow(marker, $scope.map, infoWindowContent, location); 
        	  }

        }
        var openInfoWindow = null;
        function bindInfoWindow(marker, map, html, location) {
        	google.maps.event.addListener(marker, 'click', function() {
        		if(openInfoWindow) {
        			openInfoWindow.close();
        		}
        		var infowindow = new google.maps.InfoWindow({ content: html });
        		infowindow.open(map, marker);
        		$scope.focusedLocation = location;
        		$scope.$apply();
        		openInfoWindow = infowindow;
        	});
        } 
        
        
    }]);
})(window.angular, window.jQuery)