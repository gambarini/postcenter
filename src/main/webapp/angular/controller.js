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

	$scope.fetchPostsCount = function() {
		$http.get('rest/post/count').success(function(data) {
			$scope.postsTotal = data;
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

	$scope.remove = function(id) {
		Post.remove({
			postId : id
		}, function() {
			$scope.refreshView();
		});
	};

	$scope.postSubmit = function() {

		UserPost.save({
			userId : $scope.logedUser._id
		}, $scope.post, function() {
			$scope.refreshView();
		});
	};

	$scope.refreshView = function() {
		$scope.fetchPostsCount();
		$scope.fetchPosts();
		$scope.fetchUser();
	};

	$scope.refreshView();

});

postController.controller('postCtrl', function($scope, $routeParams, Post) {

	$scope.post = Post.get({
		postId : $routeParams.postId
	}, function() {
	});

	$scope.remove = function() {
		Post.remove({
			postId : $routeParams.postId
		});
	};

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
