"use strict";

var postService = angular.module('postService', [ 'ngResource' ]);

postService.factory('Post', function($resource) {
	return $resource('rest/post/:postId');
});

postService.factory('UserPost', function($resource) {
	return $resource('rest/user/:userId/post/:postId');
});

postService.factory('User', function($resource) {
	return $resource('rest/user/:userId');
});

