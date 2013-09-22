"use strict";

var service = angular.module('Services', [ 'ngResource' ]);

service.factory('Post', function($resource) {
	return $resource('rest/post/:postId');
});


service.factory('PostReply', function($resource) {
	return $resource('rest/post/:postId/reply');
});


service.factory('User', function($resource) {
	return $resource('rest/user/:userId');
});


service.factory('Auth', function($resource) {
	return $resource('rest/authentication', {}, {
		login : {
			method : 'POST',
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded',
				'Accept' : 'text/plain'
			}
		}
	});
});
