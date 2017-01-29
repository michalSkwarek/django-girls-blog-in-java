myApp.controller('postsController', function ($scope, $http, $location) {

    $scope.showPosts = function () {
        $http.get('/posts').then(function (response) {
            $scope.posts = response.data;
        });
    };

    $scope.showPosts();
});

myApp.controller('draftsController', function ($scope, $http) {

    $scope.showDrafts = function () {
        $http.get('/drafts').then(function (response) {
            $scope.drafts = response.data;
        });
    };

    $scope.showDrafts();
});

myApp.controller('postController', function ($scope, $http, $routeParams, $location) {
    var postId = $routeParams.postId;

    $scope.submitCommentForm = function (comment) {
        if ($scope.commentForm.$valid) {
            $http.post('/post/' + postId + '/comment', comment).then(function () {
                $location.path('/post/' + postId);
            });
        }
    };

    $scope.submitPostForm = function (post) {
        if ($scope.postForm.$valid) {
            if (typeof post.id === 'undefined') {
                $http.post('/post/new', post);
            } else {
                $http.put('/post/' + post.id + '/edit', post);
            }
            $location.path('/posts');
        }
        $scope.initFirst()
    };

    // $scope.addNewPost = function (post) {
    //     $http.post('/post/new', post).then(function () {
    //         $location.path('/posts');
    //     });
    // };
    //
    // $scope.editPost = function (post) {
    //     $http.put('/post/' + post.id + '/edit', post).then(function () {
    //         $location.path('/posts');
    //     });
    // };

    $scope.showPost = function () {
        $http.get('/post/' + postId).then(function (response) {
            $scope.post = response.data;
        });
    };

    $scope.publishPost = function () {
        $http.put('/post/' + postId + '/publish')
            .then(function () {
                $scope.showPost();
            })
    };

    $scope.removePost = function (postId) {
        $http.delete('/post/' + postId + '/remove')
            .then(function () {
                $location.path('/posts');
            });
    };

    $scope.approveComment = function (commentId) {
        $http.put('/comment/' + commentId + '/approve')
            .then(function () {
                $scope.showPost();
            })
    };

    $scope.removeComment = function (commentId) {
        $http.delete('/comment/' + commentId + '/remove')
            .then(function () {
                $scope.showPost();
            });
    };

    $scope.showPost();
});

myApp.controller('navigation', function ($rootScope, $scope, $http, $location) {

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {
            authorization: "Basic "
            + btoa(credentials.username + ":"
                + credentials.password)
        } : {};

        $http.get('/user', {
            headers: headers
        }).then(function (response) {
            if (response.data.name) {
                $rootScope.username = response.data.name;
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }).catch(function () {
            $rootScope.authenticated = false;
            callback && callback();
        });
    };

    authenticate();
    $scope.credentials = {};
    $scope.login = function () {
        authenticate($scope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/posts");
                $scope.error = false;
            } else {
                $location.path("/accounts/login");
                $scope.error = true;
            }
        });
    };

    $scope.logout = function () {
        $http.post('/logout', {}).then(function () {
            console.log("dupa jasio");
            $rootScope.authenticated = false;
            $location.path("/posts");
        }).catch(function () {
            console.log("error jasio");
            $rootScope.authenticated = false;
        });
    }
});