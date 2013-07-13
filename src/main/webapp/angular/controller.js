var postController = angular.module('postController', ['services']);

postController.controller('mainCtrl', function ($scope, $http, Post, UserPost) {

	$scope.fetchPosts = function () {
		$scope.posts = Post.query({top:7});
	};

	$scope.fetchPostsCount = function () {
		$http.get('rest/post/count').success(function(data) {
			$scope.postsTotal = data;
		});
	};

	$scope.remove = function(id) {
		Post.remove({postId:id}, function() { $scope.refreshView(); });
	};
	
	$scope.postSubmit = function() {
		
		var postModel = {
			userId : $scope.post.userId,
			title : $scope.post.title,
			message : {
				text : $scope.post.message
			}
		};
		console.log(postModel);
		
		UserPost.save({userId:$scope.post.userId}, postModel, function() { $scope.refreshView(); });
	};
	
	$scope.refreshView = function () {
		console.log('refresh');
		$scope.fetchPostsCount();
		$scope.fetchPosts();
	};
	
	$scope.refreshView();

});

postController.controller('postCtrl', function ($scope, $routeParams, Post) {
	
	$scope.post = Post.get({postId:$routeParams.postId}, function() {});
	$scope.remove = function() { 
		Post.remove({postId:$routeParams.postId});
	};
	
});

postController.controller('userCtrl', function ($scope, $routeParams, $location, Auth) {
	
	$scope.userSubmit = function() {
		
		var authModel = {
			user : {
				name : $scope.user.name,
				email : $scope.user.email
			},
			password : $scope.password
		};		
		A
		$scope.user = Auth.save([], authModel, function() { $location.path( "/" ); });
	};
	
});

postController.controller('loginCtrl', function ($scope, Auth) {
	
	$scope.loginSubmit = function() {
		
		$scope.authReturn = Auth.login("email="+loginForm.email.value+"&password="+loginForm.password.value);
		
	};
	
});




