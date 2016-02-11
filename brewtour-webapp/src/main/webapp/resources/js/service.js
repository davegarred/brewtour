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
    		login : function(dto) {
            	$http.post('user/login', dto)
            	.then(function successCallback(response) {
            		currentUser = response.data;
            		$('#loginModal').modal('hide');
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
    	var locationId = null;
    	return {
    		set : function(newLocationId) {
    			this.locationId = newLocationId;
    		},
    		get : function() {
    			return this.locationId;
    		}
    	}
    	
    }]);

    function error(error) {
    	console.log(error);
    	debugger;
    }
})(window.angular, window.jQuery)