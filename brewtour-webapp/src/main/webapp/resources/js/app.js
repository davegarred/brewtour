'use strict';
(function (angular) {
    var beertour = angular.module('beertour', ['services' ]);

    beertour.controller('MapController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
    	$scope.removedBeers = [];
   		$scope.user = {};
   		$scope.$watch(function(){ return UserService.user() }, function(newVal,oldVal) {
   			return $scope.user = newVal
   		}, true);
   		UserService.find();
    	
    	function setLocationDetails(details) {
    		$scope.locationDetails = details;
    		$scope.hasIbu = hasIbu(details.beers);
    		$scope.hasAbv = hasAbv(details.beers);
    		LocationService.set(details.locationId);
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

        	    var infoWindowContent = "<strong>" + location.name + "</strong>";
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
        
        
//        $scope.login = function() {
//        	UserService.login(this.loginDto);
//        	this.loginDto = {};
//        }
        $scope.logout = function() {
        	UserService.logout();
        }
        $scope.beerRemoved = function(beerName) {
        	for(var i in $scope.removedBeers) {
        		var removed = $scope.removedBeers[i];
        		if(removed.locationId == $scope.locationDetails.identifier
        				&& removed.beerName == beerName) {
        			return true;
        		}
        	}
        	return false;
        }
        $scope.removeBeer = function(beerName) {
        	var dto = {
        			locationId : $scope.locationDetails.identifier,
        			beerName : beerName
        	};
        	$scope.removedBeers.push(dto);
        	$scope.debug = dto;
        }
        $scope.reinstateBeer = function(beerName) {
        	var dto = {
        			locationId : $scope.locationDetails.identifier,
        			beerName : beerName
        	};
        	for(var i in $scope.removedBeers) {
        		var removed = $scope.removedBeers[i];
        		if(removed.locationId == $scope.locationDetails.identifier
        				&& removed.beerName == beerName) {
        			$scope.removedBeers.splice(i,1);
        		}
        	}
        	$scope.debug = dto;
        }
        
        $http.get('location/locations')
        .then(function successCallback(response) {
        	$scope.locale = response.data;
        	initMap();
        }, function errorCallback(response) {
        	error(response);
        });
        

    }]);
    
    beertour.controller('LoginController', ['$scope', '$http', 'UserService', function ($scope, $http, UserService) {
        $scope.login = function() {
        	UserService.login(this.loginDto);
        	this.loginDto = {};
        }
    }]);
    
    beertour.controller('LocationCommentController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
    	$scope.sendComment = function() {
    		var dto = {
    				locationId : LocationService.get(),
    				comment : $scope.locationDetailsComment
    		};
    		$http.post('location/AddLocationComment', dto)
    		.then(function successCallback(response) {
    			$('#locationCommentModal').modal('hide');
    			$scope.locationDetailsComment = "";
    		}, function errorCallback(response) {
    			error(response);
    		})
    	}
    	
    }]);
    
    function error(error) {
    	console.log(error);
    	debugger;
    }
})(window.angular, window.jQuery)