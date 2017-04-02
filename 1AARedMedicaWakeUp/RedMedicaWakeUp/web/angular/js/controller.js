angular.module('mainApp', ['ngRoute'])
        .config(function ($routeProvider) {
            $routeProvider
                    .when('/', {
                        templateUrl: 'login.html'
                    })
                    .when('/dashboard', {
                        resolve: {
                            "check": function ($location, $rootScope) {
                                if (!$rootScope.loggedIn) {
                                    $location.path('/');
                                }
                            }
                        },
                        templateUrl:'dashboard.html'
                    })
                    .otherwise({
                        redirectto: '/'
                    });
        })
        .controller('loginCtrl', function ($scope, $location, $rootScope) {
            $scope.submit = function () {

                if ($scope.username === 'admin' && $scope.password === 'admin') {
                    $rootScope.loggedIn=true;
//                    $rootScope.uname = $scope.username;
//                    $rootScope.password = $scope.password;
                    $location.path('/dashboard');
                } else {
                    alert('wrong stuff');
                }
            };
        });
