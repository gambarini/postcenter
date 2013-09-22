"use strict";

var postController = angular.module('Controllers', ['Services']);

postController.controller('mainController', function ($scope, $http, $location, Post, Auth,
    User) {

    $scope.post = {
        title: "",
        message: ""
    };

    $scope.sidePanel = 0;

    $scope.fetchPosts = function () {
        $scope.posts = Post.query({
            top: 7
        });
    };

    $scope.fetchUser = function () {

        $scope.logedUser = User.get({}, function () {}, function () {});
    };

    $scope.postSubmit = function () {

        Post.save({}, $scope.post, function () {
            $scope.fetchPosts();
        }, function () {
            $location.path('/authentication')
        });
    };

    $scope.logout = function () {
        Auth.delete({}, function () {
            $scope.logedUser = undefined;
        });
    };

    $scope.userSubmit = function () {

        $scope.user = User.save([], $scope.user, function (data) {

        }, function (data) {});
    };

    $scope.loginSubmit = function () {

        Auth.login("email=" + $scope.login.email + "&password=" + $scope.login.password, function (data) {

            $scope.fetchUser();

        }, function (data) { 
        	
        	$scope.logedUser = undefined;
        });

        return false;
    };

    $scope.refreshView = function () {

    	$scope.fetchPosts();
    	$scope.fetchUser();

    };	

    $scope.refreshView();

});

postController.controller('postController', function ($scope, $routeParams, $location,
    User, Post, PostReply, Auth) {
	
    $scope.sidePanel = 0;

    $scope.fetchPost = function () {
        $scope.post = Post.get({
            postId: $routeParams.postId
        }, function () {

        });
    };

    $scope.fetchUser = function () {

        $scope.logedUser = User.get({}, function () {}, function () {});
    };

    $scope.replySubmit = function () {

        PostReply.save({
            postId: $routeParams.postId
        }, $scope.reply, function () {
            $scope.fetchPost();
        }, function () {
            $location.path('/authentication');
        });
    };

    $scope.logout = function () {
        Auth.delete({}, {}, function () {
            $scope.logedUser = undefined;
        });
    };


    $scope.remove = function () {
        Post.remove({
            postId: $routeParams.postId
        }, function () {
            $location.path('/');
        }, function () {
            $location.path('/authentication')
        });
    };
    
    $scope.userSubmit = function () {

        $scope.user = User.save([], $scope.user, function (data) {

        }, function (data) {});
    };
    
    $scope.logout = function () {
        Auth.delete({}, function () {
            $scope.logedUser = undefined;
        });
    };


    $scope.loginSubmit = function () {

        Auth.login("email=" + $scope.login.email + "&password=" + $scope.login.password, function (data) {

            $scope.fetchUser();

        }, function (data) { 
        	
        	$scope.logedUser = undefined;
        });

        return false;
    };
    
    $scope.fetchPosts = function () {
        $scope.posts = Post.query({
            top: 5
        });
    };


    $scope.refreshView = function () {
        $scope.fetchPost();
        $scope.fetchUser();
        $scope.fetchPosts();
    }

    $scope.refreshView();

});