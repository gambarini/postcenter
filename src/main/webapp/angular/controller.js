var postController = angular.module('postController',
		[ 'services', 'ngCookies' ]);

postController.controller('mainCtrl', function($scope, $http, $cookieStore,
		Post, UserPost, User) {

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

		var cookieEmail = $cookieStore.get('email');

		if (cookieEmail !== undefined) {

			$scope.logedUser = User.get({
				email : cookieEmail
			});
		}
	};

	$scope.postSubmit = function() {

		UserPost.save({
			userId : $scope.logedUser._id
		}, $scope.post, function() {
			$scope.refreshView();
		});
	};

	$scope.refreshView = function() {

		$scope.fetchPosts();
		$scope.fetchUser();
	};

	$scope.refreshView();

});

postController.controller('postCtrl', function($scope, $routeParams, $location, $cookieStore, User, Post, PostReply) {

	$scope.fetchPost = function() {
		$scope.post = Post.get({
			postId : $routeParams.postId
		}, function() {

		});
	};

	$scope.fetchUser = function() {

		var cookieEmail = $cookieStore.get('email');

		if (cookieEmail !== undefined) {

			$scope.logedUser = User.get({
				email : cookieEmail
			});
		}
	};
	
	$scope.replySubmit = function() {

		PostReply.save({
			userId : $scope.logedUser._id,
			postId : $routeParams.postId
		}, $scope.reply, function() {
			$scope.refreshView();
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

postController.controller('loginCtrl', function($scope, $location,
		$cookieStore, Auth) {

	$scope.loginSubmit = function() {

		Auth.login("email=" + loginForm.email.value + "&password="
				+ loginForm.password.value, function(data) {
			$cookieStore.put('email', loginForm.email.value);
			$cookieStore.put('token', data);
			$location.path('/');

		});

	};

});
