'use strict';
(function (angular) {
    var brewtour = angular.module('brewtour', [ ]);

    brewtour.controller('MapController', ['$scope', '$http', function ($scope, $http) {
    	
    	var favoriteLocationIds = [];
    	$scope.isFavorite = function(id) {
    		for(var i in favoriteLocationIds) {
    			if(favoriteLocationIds[i] == id) {
    				return true;
    			}
    		}
    		return false;
    	}
    	$scope.removeFavorite = function(id) {
    		for(var i in favoriteLocationIds) {
    			if(favoriteLocationIds[i] == id) {
    				favoriteLocationIds.splice(i,1);
    			}
    		}
    		$http.post('removeFavorite', { locationId : id })
            .then(function successCallback(response) {
            	favoriteLocationIds = response.data.favoriteLocations;
            }, function errorCallback(response) {
            	error(response);
            });
    	}
    	$scope.addFavorite = function(id) {
    		favoriteLocationIds.push(id);
    		$http.post('addFavorite', { locationId : id } )
            .then(function successCallback(response) {
            	favoriteLocationIds = response.data.favoriteLocations;
            }, function errorCallback(response) {
            	error(response);
            });
    	}
    	function setLocationDetails(details) {
    		$scope.locationDetails = details;
    		$scope.hasIbu = hasIbu(details.beers);
    		$scope.hasAbv = hasAbv(details.beers);
    	}
    	function hasIbu(beerList) {
    		for(var i in beerList) {
    			if(beerList[i].ibu) {
    				return true;
    			}
    		}
    		return false;
    	}
    	function hasAbv(beerList) {
    		for(var i in beerList) {
    			if(beerList[i].abv) {
    				return true;
    			}
    		}
    		return false;
    	}
    	
    	
        function initMap() {
        	var locale = $scope.locale;
        	$scope.map = new google.maps.Map(document.getElementById('map'), locale.googleMapsParameters);

        	  for(var l in locale.locations) {
        	    var location = locale.locations[l];
        	    var locationPosition = {lat: location.latitude, lng: location.longitude};
        	    var details = {
        	        position: locationPosition,
        	        title: location.name
        	    };
        	    var marker = new google.maps.Marker(details);
        	    marker.setMap($scope.map);

        	    var infoWindowContent = "<h4><center>" + location.name + "</center></h4>";
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
        		
        		$http.get('location/' + location.locationId)
            	.then(function successCallback(response) {
            		setLocationDetails(response.data);
            	}, function errorCallback(response) {
            		error(response);
            	});
        	});
        } 
        
        $http.get('user')
    	.then(function successCallback(response) {
    		favoriteLocationIds = response.data ? response.data.favoriteLocations : [];
    	}, function errorCallback(response) {
    		error(response);
    	});
        
        $http.get('locations')
        .then(function successCallback(response) {
        	$scope.locale = response.data;
        	initMap();
        }, function errorCallback(response) {
        	error(response);
        });
        
        function error(error) {
        	console.log(error);
        }
    }]);
})(window.angular, window.jQuery)