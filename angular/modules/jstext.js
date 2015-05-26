"use strict";
//= include ../node_modules/angular/angular.js

var jstext = angular.module("jstext", []);

jstext.controller("GameController", ["$scope", function ($scope) {
    $scope.location = {
        name: "Test Location",
        image: "http://www.theodora.com/maps/new9/time_zones_4.jpg"
    };
    $scope.log = ["Some action", "Another action"];
    $scope.input = "";

    $scope.command = function command() {
        $scope.log.push($scope.input);
        $scope.input = "";
    };
}]);