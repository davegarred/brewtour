'use strict';
(function (angular) {
    var services = angular.module('services', [ ]);

    services.factory('UserService', ['$http', function ($http) {
    	var currentUser = null;
    	return {
    		set : function(updatedUser) {
    			currentUser = updatedUser;
    		},
    		find : function() {
    	        $http.get('user')
    	    	.then(function successCallback(response) {
    	    		currentUser = response.data;
    	    	}, function errorCallback(response) {
    	    		error(response);
    	    	});
    		},
    		logout : function(dto) {
            	$http.get('user/logout')
            	.then(function successCallback(response) {
            		currentUser = response.data;
            	}, function errorCallback(response) {
            		error(response);
            	});
    			
    		},
    		user : function() {
    			return currentUser;
    		}
    	}
    	
    }]);
    
    services.factory('LocationService', ['$http', '$location', function ($http, $location) {
    	var currentLocation = null;
    	var ibu = false;
    	var abv = false;
    	var currentBeer = null;
    	var beers = [];
//    	var visitedLocations = [];

    	var locationIdentifiers = $location.search();
    	if(locationIdentifiers) {
    		if(locationIdentifiers.l && locationIdentifiers.l.length == 7) {
    			var initialLocation = locationIdentifiers.l;
    			loadLocation(initialLocation);
    		}
//    		if(locationIdentifiers.v) {
//    			for(var i in locationIdentifiers.v) {
//    				visitedLocations.push(locationIdentifiers.v[i]);
//    			}
//    		}
    	}
    	
    	return {
    		goTo : function(locationId) {
    			loadLocation(locationId);
    		},
//    		visited : function(locationId) {
//    			if(!locationId || locationId.length != 7) {
//    				return false;
//    			}
//    			for(var i in visitedLocations) {
//    				if(locationId == visitedLocations[i]) {
//    					return true;
//    				}
//    			}
//    			return false;
//    		},
    		update : function(location) {
    			currentLocation = location;
    			currentBeer = null;
    			$location.search('l',currentLocation.locationId);
    		},
    		updateBeer : function(beer) {
    			beers[beer.id] = beer;
    		},
    		beers : function() {
    			return beers;
    		},
    		clear : function() {
    			currentLocation = null;
    			$location.search('l',null);
    		},
    		location : function() {
    			return currentLocation;
    		},
    		hasIbu : function() {
    			return ibu;
    		},
    		hasAbv : function() {
    			return abv;
    		},
    		setBeer : function(beer) {
    			currentBeer = beer;
    		},
    		beer : function() {
    			return currentBeer;
    		} 

    	}
    	
    	function loadLocation(locationId) {
			$http.get('location/' + locationId)
			.then(function successCallback(response) {
    			beers = response.data.beers; 
    			currentLocation = response.data.location;
    			ibu = hasIbu(beers);
    			abv = hasAbv(beers);
    			currentBeer = null;
//    			visitedLocations.push(currentLocation.locationId);
//    			$location.search('v',visitedLocations);
    			$location.search('l',currentLocation.locationId);
			}, function errorCallback(response) {
				error(response);
			});
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
    	

    }]);

    function error(error) {
    	console.log(error);
    }
})(window.angular, window.jQuery)