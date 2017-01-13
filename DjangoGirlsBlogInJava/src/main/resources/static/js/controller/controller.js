myApp.controller('postController', function ($scope, $http) {

    $http({
        method: 'GET',
        url: '/posts'
    }).then(function successCallback(response) {
        $scope.posts = response.data;
    });
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
