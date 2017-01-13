var myApp = angular.module('BlogApp', ['ngRoute','ngResource']);

myApp.config(function($routeProvider, $locationProvider){
    $routeProvider
        .when('/posts',{
            templateUrl: '/views/blog/post_list.html',
            controller: 'postController'
        })
        .when('/roles',{
            templateUrl: '/views/blog/roles.html',
            controller: 'rolesController'
        })
        .otherwise(
            { redirectTo: '/'}
        );

    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
});
