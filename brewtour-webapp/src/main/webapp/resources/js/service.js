'use strict';
(function (angular) {
    var services = angular.module('services', [ ]);

    services.factory('UserService', ['$http', function ($http) {
    	var currentUser = null;
    	return {
    		set : function(updatedUser) {
    			currentUser = updatedUser;
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

})(window.angular, window.jQuery)