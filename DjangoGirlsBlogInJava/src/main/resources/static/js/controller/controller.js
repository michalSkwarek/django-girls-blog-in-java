myApp.controller('postsController', function ($scope, $http) {

    $scope.showPosts = function() {
        $http.get('/posts').then(function(response){
            $scope.posts = response.data;
        });
    };

    $scope.showPosts();
});

myApp.controller('draftsController', function ($scope, $http) {

    $scope.showDrafts = function() {
        $http.get('/drafts').then(function(response){
            $scope.drafts = response.data;
        });
    };

    $scope.showDrafts();
});

myApp.controller('postController', function ($scope, $http, $routeParams) {
    var postId = $routeParams.postId;

    $scope.showPost = function() {
        $http.get('/post/' + postId).then(function(response){
            $scope.post = response.data;
        });
    };

    $scope.publishPost = function () {
        $http.put('/post/' + postId + '/publish')
            .then(function () {
                $scope.showPost($http.get('/post/' + postId));
            })
    };

    $scope.approveComment = function (commentId) {
        $http.put('/comment/' + commentId + '/approve')
            .then(function () {
                $scope.showPost($http.get('/post/' + postId));
            })
    };

    $scope.removeComment = function(commentId) {
        $http.delete('/comment/' + commentId + '/remove')
            .then(function () {
                $scope.showPost($http.get('/post/' + postId));
            });
    };

    $scope.showPost();
});







//
// myApp.controller('rolesController', function($scope) {
//     $scope.headingTitle = "Roles List";
// });



// var PostController = function($scope, $http) {
//     $scope.fetchPostList = function() {
//         $http.get('/posts').success(function(postList){
//             // $scope.posts = postList;
//             $scope.posts = "asdasda";
//         });
//     };
//
//     // $scope.addNewCar = function(newCar) {
//     //     $http.post('posts/addCar/' + newCar).success(function() {
//     //         $scope.fetchPostList();
//     //     });
//     //     $scope.carName = '';
//     // };
//     //
//     // $scope.removeCar = function(car) {
//     //     $http.delete('posts/removeCar/' + car).success(function() {
//     //         $scope.fetchPostList();
//     //     });
//     // };
//     //
//     // $scope.removeAllCars = function() {
//     //     $http.delete('posts/removeAllCars').success(function() {
//     //         $scope.fetchPostList();
//     //     });
//     //
//     // };
//
//     $scope.fetchPostList();
// };
//
// var PostController = function($scope, $http) {
//     $scope.fetchCarsList = function() {
//         $http.get('/posts').success(function(carList){
//             $scope.cars = "asdasdasdasd";
//         });
//     };
//
//     // $scope.addNewCar = function(newCar) {
//     //     $http.post('cars/addCar/' + newCar).success(function() {
//     //         $scope.fetchCarsList();
//     //     });
//     //     $scope.carName = '';
//     // };
//     //
//     // $scope.removeCar = function(car) {
//     //     $http.delete('cars/removeCar/' + car).success(function() {
//     //         $scope.fetchCarsList();
//     //     });
//     // };
//     //
//     // $scope.removeAllCars = function() {
//     //     $http.delete('cars/removeAllCars').success(function() {
//     //         $scope.fetchCarsList();
//     //     });
//     //
//     // };
//
//     $scope.fetchCarsList();
// };
