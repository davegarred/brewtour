'use strict';
(function (angular) {
    var beertour = angular.module('beertour');

    beertour.directive('locationLargeMedalImg', function() {
    	return {
    		templateUrl: 'resources/html/template/locationLargeReviewMedal.html'
    	};
    });
    beertour.directive('locationMedalImg', function() {
    	return {
    		templateUrl: 'resources/html/template/locationReviewMedal.html'
    	};
    });
    beertour.directive('beerMedalImg', function() {
    	return {
    		templateUrl: 'resources/html/template/beerReviewMedal.html'
    	};
    });
    
    beertour.filter('orderObjectBy', function() {
    	return function(input, attribute) {
    		if (!angular.isObject(input)) return input;
    		var array = [];
    	    for(var objectKey in input) {
    	        array.push(input[objectKey]);
    	    }

    	    array.sort(function(a, b){
    	    	var a_attr = a[attribute];
    	    	var b_attr = b[attribute];
    	    	return a_attr.localeCompare(b_attr);
    	    });
    	    return array;
    	}
    });
})(window.angular, window.jQuery)