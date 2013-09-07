"use strict";

var postController = angular.module('postController', ['services']);

postController.controller('mainCtrl', function ($scope, $http, $location, Post, Auth,
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
        console.log($scope.login + 'logando...');
        Auth.login([], "email=" + $scope.login.email + "&password=" + $scope.login.password, function (data) {
            console.log('logado, buscando usuario');
            $scope.fetchUser();

        }, function (data) {
            console.log('erro logando.');
            $scope.logedUser = undefined;
        });
        
        return false;
    };

    $scope.refreshView = function () {

        $scope.fetchPosts();
        $scope.fetchUser();

        /*$('.showHideLink').each(function () {

            $(this).children('form').hide();

            $(this).children('h3').click(function (event) {
                event.preventDefault();
                $(this).siblings('form').toggle('blind');
            });
        });*/
    };

    $scope.refreshView();

});

postController.controller('postCtrl', function ($scope, $routeParams, $location,
    User, Post, PostReply, Auth) {

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

    $scope.refreshView = function () {
        $scope.fetchPost();
        $scope.fetchUser();
    }

    $scope.refreshView();

});