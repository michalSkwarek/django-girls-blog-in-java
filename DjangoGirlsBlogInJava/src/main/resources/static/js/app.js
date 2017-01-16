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
        .when('/post/new', {
            templateUrl: '/views/blog/post_edit.html',
            controller: 'postsController'
        })
        .when('/post/:postId/edit', {
            templateUrl: '/views/blog/post_edit.html',
            controller: 'postController'
        })
        .when('/post/:postId/comment', {
            templateUrl: '/views/blog/add_comment_to_post.html',
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
