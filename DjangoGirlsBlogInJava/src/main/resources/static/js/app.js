var myApp = angular.module('BlogApp', ['ngRoute','ngResource']);

myApp.config(function($routeProvider, $locationProvider){
    $routeProvider
        .when('/posts', {
            templateUrl: '/views/blog/post_list.html',
            controller: 'postsController'
        })
        .when('/drafts', {
            templateUrl: '/views/blog/post_draft_list.html',
            controller: 'draftsController'
        })
        .when('/post/:postId', {
            templateUrl: '/views/blog/post_detail.html',
            controller: 'postController'
        })
        .otherwise(
            { redirectTo: '/'}
        );

    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});
