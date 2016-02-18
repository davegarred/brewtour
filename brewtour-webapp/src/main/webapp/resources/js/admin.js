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
    	
    	$scope.focusLocation = function(location) {
    		$scope.focusedBeer = null;
    		$http.get('../location/' + location.locationId)
    		.then(function successCallback(response) {
    			$scope.focusedLocation = response.data;
    		}, function errorCallback(response) {
    			error(response);
    		});
    	}
    	
    	$scope.focusBeer = function(beer) {
    		$scope.focusedBeer = beer;
    	}
    	
    }]);
    
    
    function error(error) {
    	console.log(error);
    }
})(window.angular, window.jQuery)
