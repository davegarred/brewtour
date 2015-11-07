'use strict';
(function (angular) {
    var brewtour = angular.module('brewtour', [ ]);

    brewtour.controller('MapController', ['$scope', '$http', function ($scope, $http) {
    	
//    	$scope.focusedLocation = true;
    	
    	$http.get('locations')
    	.then(function successCallback(response) {
    		$scope.locale = response.data;
    		initMap();
    	}, function errorCallback(response) {
    		debugger;
    		
    	});
    	
        function initMap() {
        	var locale = $scope.locale;
        	$scope.map = new google.maps.Map(document.getElementById('map'), {
        	    center: {lat: locale.latitude, lng: locale.longitude},
        	    zoom: locale.zoom
        	  });

        	  for(var l in locale.locations) {
        	    var location = locale.locations[l];
        	    var locationPosition = {lat: location.latitude, lng: location.longitude};
        	    var details = {
        	        position: locationPosition,
        	        title: location.name
        	    };
        	    var marker = new google.maps.Marker(details);
        	    marker.setMap($scope.map);

        	    var infoWindowImage = location.images ? "<p><img src='" + location.images.medium + "'></p>" : "";
        	    var infoWindowContent = infoWindowImage + "<h4><center>" + location.name + "</center></h4>";
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