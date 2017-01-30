var myApp = angular.module('BlogApp', ['ngRoute','ngResource']);

myApp.config(function($routeProvider, $locationProvider, $httpProvider){
    $routeProvider
        .when('/accounts/login', {
            templateUrl: '/views/registration/login.html',
            controller: 'navigation'
        })
        .when('/posts', {
            templateUrl: '/views/blog/post_list.html',
            controller: 'publishedPostsController'
            // reloadOnSearch: true
        })
        .when('/drafts', {
            templateUrl: '/views/blog/post_draft_list.html',
            controller: 'draftsController'
        })
        .when('/post/new', {
            templateUrl: '/views/blog/post_edit.html',
            controller: 'newPostController'
        })
        .when('/post/:postId/edit', {
            templateUrl: '/views/blog/post_edit.html',
            controller: 'postController'
        })
        .when('/post/:postId', {
            templateUrl: '/views/blog/post_detail.html',
            controller: 'postController'
        })
        .when('/post/:postId/comment', {
            templateUrl: '/views/blog/add_comment_to_post.html',
            controller: 'postController'
        })
        .otherwise(
            { redirectTo: '/posts'}
        );

    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});