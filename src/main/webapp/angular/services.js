"use strict";

var service = angular.module('services', [ 'ngResource' ]);

service.factory('Post', function($resource) {
	return $resource('rest/post/:postId');
});

service.factory('UserPost', function($resource) {
	return $resource('rest/user/:userId/post/:postId');
});

service.factory('User', function($resource) {
	return $resource('rest/user/:userId');
});

service.factory('Auth', function($resource) {
	return $resource('rest/authentication', {}, {
		login : {
			method : 'POST',
			headers : {'Content-Type': 'application/x-www-form-urlencoded'}
		}
	});
});
