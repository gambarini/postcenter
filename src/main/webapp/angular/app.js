"use strict";

var postCenter = angular.module('postCenter', ['postController','postService']);

postCenter.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'partials/main.html',
		controller : 'mainCtrl'
	}).when('/:postId', {
		templateUrl : 'partials/post.html',
		controller : 'postCtrl'
	}).otherwise({
		redirectTo : '/'
	})
} ]);




