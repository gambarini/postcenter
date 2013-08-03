var postController = angular.module('postController',
		[ 'services', 'ngCookies' ]);

postController.controller('mainCtrl', function($scope, $http, Post, UserPost,
		User) {

	$scope.post = {
		title : "",
		message : ""
	};

	$scope.fetchPosts = function() {
		$scope.posts = Post.query({
			top : 7
		});
	};

	$scope.fetchUser = function() {

		$scope.logedUser = User.get({});
	};

	$scope.postSubmit = function() {

		UserPost.save({
			userId : $scope.logedUser._id
		}, $scope.post, function() {
			$scope.fetchPosts();
		});
	};

	$scope.refreshView = function() {

		$scope.fetchPosts();
		$scope.fetchUser();
	};

	$scope.refreshView();

});

postController.controller('postCtrl', function($scope, $routeParams, $location,
		User, Post, PostReply) {

	$scope.fetchPost = function() {
		$scope.post = Post.get({
			postId : $routeParams.postId
		}, function() {

		});
	};

	$scope.fetchUser = function() {

		$scope.logedUser = User.get({});
	};

	$scope.replySubmit = function() {

		PostReply.save({
			userId : $scope.logedUser._id,
			postId : $routeParams.postId
		}, $scope.reply, function() {
			$scope.fetchPost();
		});
	};

	$scope.remove = function() {
		Post.remove({
			postId : $routeParams.postId
		}, function() {
			$location.path('/');
		});
	};

	$scope.refreshView = function() {
		$scope.fetchPost();
		$scope.fetchUser();
	}

	$scope.refreshView();

});

postController.controller('userCtrl', function($scope, $routeParams, $location,
		User) {

	$scope.userSubmit = function() {

		$scope.user = User.save([], $scope.user, function() {
			$location.path("/");
		});
	};

});

postController.controller('loginCtrl', function($scope, $location, Auth) {

	$scope.loginSubmit = function() {

		Auth.login("email=" + loginForm.email.value + "&password="
				+ loginForm.password.value, function(data) {

			$location.path('/');

		});

	};

});
