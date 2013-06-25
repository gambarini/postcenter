"use strict";

var postService = angular.module('postService', [ 'ngResource' ]);

postService.factory('Post', function($resource) {
	return $resource('rest/post/:postId');
});

