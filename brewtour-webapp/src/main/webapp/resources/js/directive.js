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
})(window.angular, window.jQuery)