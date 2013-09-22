"use strict";

var postCenter = angular.module('PostCenter', ['Controllers','Services','Directives']);

postCenter.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'partials/main.html',
		controller : 'mainController'
	}).when('/user/:userId', {
		templateUrl : 'partials/user.html',
		controller : 'userController'
	}).when('/:postId', {
		templateUrl : 'partials/post.html',
		controller : 'postController'
	}).otherwise({
		redirectTo : '/'
	})
} ]);




