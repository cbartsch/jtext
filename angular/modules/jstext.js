"use strict";
//= include ../node_modules/angular/angular.js
//= include game.js

var jstext = angular.module("jstext", []);

jstext.controller("GameController", ["$scope", "$http", function ($scope, $http) {
    $http.get("game/test.json").then(function (result) {
        $scope.game = new Game(result.data);
        $scope.gameState = $scope.game.start($scope.print);
        $scope.updateLocation();
    });

    $scope.updateLocation = function() {
        $scope.location = {
            name: $scope.gameState && $scope.gameState.location.name,
            image: "http://www.theodora.com/maps/new9/time_zones_4.jpg"
        };
    };
    $scope.updateLocation();
    $scope.log = [];
    $scope.print = function (text) {
        $scope.log.push({index: $scope.log.length, text: text});
    };
    $scope.input = "";

    $scope.command = function command() {
        $scope.gameState.enter($scope.input);
        $scope.updateLocation();
        $scope.input = "";
    };
}]);