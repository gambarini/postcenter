"use strict";

var postCenter = angular.module('postCenter', ['postController','services']);

postCenter.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'partials/main.html',
		controller : 'mainCtrl'
	}).when('/user/:userId', {
		templateUrl : 'partials/user.html',
		controller : 'userCtrl'
	}).when('/:postId', {
		templateUrl : 'partials/post.html',
		controller : 'postCtrl'
	}).otherwise({
		redirectTo : '/'
	})
} ]);




