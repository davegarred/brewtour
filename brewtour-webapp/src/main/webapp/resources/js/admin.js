'use strict';
(function (angular) {
    var admin = angular.module('admin', []);

    admin.controller('AdminController', ['$scope', '$http', function ($scope, $http) {

    	$http.get('../location/locations')
    	.then(function successCallback(response) {
    		$scope.locale = response.data;
    	}, function errorCallback(response) {
    		error(response);
    	});
    	
    	$scope.updateLocation = function(command,dto) {
    		if(!dto || !command) {
    			return;
    		}
    		dto.locationId = $scope.focusedLocation.locationId;
    		$http.post('../location/' + command, dto)
    		.then(function successCallback(response) {
    			setLocation(response.data);
    		}, function errorCallback(response) {
    			error(response);
    		})
    	}
    	$scope.updateBeer = function(command,dto) {
    		if(!dto || !command) {
    			return;
    		}
    		$http.post('../beer/' + command, dto)
    		.then(function successCallback(response) {
    			setBeer(response.data.beer);
    		}, function errorCallback(response) {
    			error(response);
    		})
    	}
    	
    	$scope.focusLocation = function(location) {
    		$scope.focusedBeer = null;
    		$http.get('../location/' + location.locationId)
    		.then(function successCallback(response) {
    			setLocation(response.data.location);
    			$scope.beers = response.data.beers;

    		}, function errorCallback(response) {
    			error(response);
    		});
    	}
    	
    	function setLocation(location) {
    		$scope.focusedLocation = location;
			$scope.descriptionDto = {};
			$scope.descriptionDto.description = location.description;
    	}
    	
    	$scope.focusBeer = function(beer) {
    		setBeer(beer);
    	}
    	
    	function setBeer(beer) {
    		$scope.focusedBeer = beer;
			$scope.beerDto = {};
			$scope.beerDto.beerId = beer.id;
//			$scope.beerDto.beerName = beer.beerName;
			$scope.beerDto.description = beer.description;
			$scope.beerDto.style = beer.style;
			$scope.beerDto.category = beer.category;
			$scope.beerDto.abv = beer.abv;
			$scope.beerDto.ibu = beer.ibu;
    	}
    	
    }]);
    
    
    function error(error) {
    	console.log(error);
    }
})(window.angular, window.jQuery)
