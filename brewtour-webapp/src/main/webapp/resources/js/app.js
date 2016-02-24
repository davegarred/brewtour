'use strict';
(function (angular) {
    var beertour = angular.module('beertour', ['services', 'ui.bootstrap']);

    beertour.controller('MainController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
   		$scope.user = null;
   		$scope.$watch(function(){ return UserService.user() }, function(newVal,oldVal) {
   			return $scope.user = newVal
   		}, true);
   		UserService.find();

   		$scope.locationDetails = null;
   		$scope.$watch(function(){ return LocationService.location() }, function(newVal,oldVal) {
    		$scope.hasIbu = LocationService.hasIbu();
    		$scope.hasAbv = LocationService.hasAbv();
    		$scope.beers = LocationService.beers();
   			return $scope.locationDetails = newVal
   		}, true);
        
        $scope.logout = function() {
        	UserService.logout();
        }
        
    }]);
    beertour.controller('LocationController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
    	$scope.removedBeers = [];
    	$scope.user = null;
    	$scope.$watch(function(){ return UserService.user() }, function(newVal,oldVal) {
    		return $scope.user = newVal
    	}, true);
    	UserService.find();
    	
    	$scope.locationDetails = null;
    	$scope.$watch(function(){ return LocationService.location() }, function(newVal,oldVal) {
    		$scope.hasIbu = LocationService.hasIbu();
    		$scope.hasAbv = LocationService.hasAbv();
    		$scope.beers = LocationService.beers();
    		return $scope.locationDetails = newVal
    	}, true);
    	
    	$scope.closeLocation = function() {
    		LocationService.clear();
    	}
    	
    	$scope.showLocationComments = false;
    	$scope.toggleLocationComments = function() {
    		$scope.showLocationComments = !$scope.showLocationComments;
    	}
    	
    	$scope.reviewBeer = function(beer) {
    		LocationService.setBeer(beer);
    	}

    	$scope.isFavoriteLocation = function(locationId) {
    		for(var i in $scope.user.favoriteLocations) {
    			if(locationId == $scope.user.favoriteLocations[i]) {
    				return true;
    			}
    		}
    		return false;
    	}
    	$scope.isFavoriteBeer = function(beerId) {
    		for(var i in $scope.user.favoriteBeers) {
    			if(beerId == $scope.user.favoriteBeers[i]) {
    				return true;
    			}
    		}
    		return false;
    	}
    	function submitUserCommand(commandName, dto) {
        	$http.post('user/' + commandName, dto)
        	.then(function successCallback(response) {
        		UserService.set(response.data);
        	}, function errorCallback(response) {
        		error(response);
        	});
    	}
    	$scope.addFavoriteLocation = function(locationId) {
    		submitUserCommand('AddFavoriteLocation',{locationId : locationId});
    	}
    	$scope.removeFavoriteLocation = function(locationId) {
    		submitUserCommand('RemoveFavoriteLocation',{locationId : locationId});
    	}
    	$scope.addFavoriteBeer = function(beerId) {
    		submitUserCommand('AddFavoriteBeer',{beerId : beerId});
    	}
    	$scope.removeFavoriteBeer = function(beerId) {
    		submitUserCommand('RemoveFavoriteBeer',{beerId : beerId});
    	}
    	
    	$scope.isUserAndHasNotReviewedLocation = function() {
    		if(!$scope.user) {
    			return false;
    		}
    		var locationId = LocationService.location().locationId;
    		return $scope.user.locationReviews[locationId] == null;
    	}
    	$scope.isUserAndHasNotReviewedBeer = function(beerId) {
    		if(!$scope.user) {
    			return false;
    		}
    		return $scope.user.beerReviews[beerId] == null;
    	}
    	
    }]);
    beertour.controller('MapController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
    	
    	function initMap() {
    		var locale = $scope.locale;
    		$scope.map = new google.maps.Map(document.getElementById('map'), locale.googleMapsParameters);
    		
    		for(var l in locale.locations) {
    			var location = locale.locations[l];
    			var locationPosition = {lat: location.latitude, lng: location.longitude};
//    			var opacity = 0.0;
//    			if(LocationService.visited(location.locationId)) {
//    				opacity = 0.15;
//    			}
    			var details = {
    					position: locationPosition,
    					title: location.name,
    					icon: "http://maps.google.com/mapfiles/ms/icons/red-dot.png"
//    					opacity: opacity
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
    			marker.setIcon("http://maps.google.com/mapfiles/ms/icons/green-dot.png");
    			var infowindow = new google.maps.InfoWindow({ content: html });
    			infowindow.open(map, marker);
    			$scope.focusedLocation = location;
    			$scope.$apply();
    			openInfoWindow = infowindow;
    			LocationService.goTo(location.locationId);
    		});
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
    	$scope.userNotFound = false;
        $scope.login = function() {
        	$scope.userNotFound = false;
        	$http.post('user/login', this.loginDto)
        	.then(function successCallback(response) {
        		UserService.set(response.data);
        		$('#loginModal').modal('hide');
        		$scope.loginDto = {};
        	}, function errorCallback(response) {
        		if(response.status == 404) {
        			$scope.userNotFound = true;
        		} else {
        			error(response);
        		}
        	});
        }
    }]);
    
    beertour.controller('LocationCommentController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
    	$scope.sendComment = function() {
    		var dto = {
    				locationId : LocationService.location().locationId,
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
    beertour.controller('LocationReviewController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
    	$('#locationReviewModal').on('show.bs.modal', function(e) {
    		$scope.locationName = LocationService.location().name;
    		$scope.medal = null;
    		$scope.locationReview = "";
    		$scope.$apply();
    	});
    	
    	$scope.sendReview = function() {
    		var dto = {
    				locationId : LocationService.location().locationId,
    				medal : $scope.medal,
    				review : $scope.locationReview
    		};
    		$http.post('location/AddLocationReview', dto)
    		.then(function successCallback(response) {
    			$('#locationReviewModal').modal('hide');
    			$scope.medal = null;
    			$scope.locationReview = "";
    			UserService.set(response.data.user);
    			LocationService.update(response.data.location);
    		}, function errorCallback(response) {
    			error(response);
    		})
    	}
    	
    }]);
    beertour.controller('BeerReviewController', ['$scope', '$http', 'UserService', 'LocationService', function ($scope, $http, UserService, LocationService) {
   		$scope.location = null;
   		$scope.$watch(function(){ return LocationService.location() }, function(newVal,oldVal) {
    		$scope.beers = LocationService.beers();
   			return $scope.location = newVal
   		}, true);
   		$scope.reviewingBeer = null;
   		$scope.$watch(function(){ return LocationService.beer() }, function(newVal,oldVal) {
   			return $scope.reviewingBeer = newVal
   		}, true);
   		
    	$('#beerReviewModal').on('show.bs.modal', function(e) {
    		$scope.locationName = LocationService.location().name;
    		$scope.medal = null;
    		$scope.beerReview = "";
    		$scope.$apply();
    	});

    	$scope.sendReview = function() {
    		var dto = {
    				beerId : $scope.reviewingBeer.id,
    				medal : $scope.medal,
    				review : $scope.beerReview
    		};
    		$http.post('beer/AddBeerReview', dto)
    		.then(function successCallback(response) {
    			LocationService.updateBeer(response.data.beer);
    			UserService.set(response.data.user);
    			$('#beerReviewModal').modal('hide');
    			$scope.medal = null;
    			$scope.beerReview = "";
    		}, function errorCallback(response) {
    			error(response);
    		})
    	}
    	
    }]);
    
    function error(error) {
    	console.log(error);
    }
})(window.angular, window.jQuery)
