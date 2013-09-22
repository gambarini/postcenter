"use strict";

var directivesModule = angular.module('Directives', [ 'Services' ]);

directivesModule.directive('recentPosts', function() {
	return {
		restrict : 'E',
		replace : true,
		link : function($scope, elem, attrs) {
			$scope.recentPosts = [];
			
		},
		template : '<div><h3>Recent Posts</h3>' + '<ul>'
				+ '<li ng-repeat="post in recentPosts">'
				+ '<a ng-href="#{{post.id}}" rel="bookmark">{{post.title}}</a>'
				+ '</li>' + '</ul></div>',
		scope : {},
		controller : function($scope, Post) {
			Post.query({
				top : 10
			}, function (data) {
				$scope.recentPosts = data;
			});

		}
	}
});