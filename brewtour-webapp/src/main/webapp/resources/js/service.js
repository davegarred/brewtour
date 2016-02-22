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
    
    services.factory('LocationService', ['$http', function ($http) {
    	var currentLocation = null;
    	var ibu = false;
    	var abv = false;
    	var currentBeer = null;
    	var beers = [];
    	return {
    		set : function(response) {
    			beers = response.beers; 
    			currentLocation = response.location;
    			ibu = hasIbu(beers);
    			abv = hasAbv(beers);
    			currentBeer = null;
    		},
    		update : function(location) {
    			currentLocation = location;
    			currentBeer = null;
    		},
    		updateBeer : function(beer) {
    			beers[beer.id] = beer;
    		},
    		beers : function() {
    			return beers;
    		},
    		clear : function() {
    			currentLocation = null;
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
    	debugger;
    }
})(window.angular, window.jQuery)