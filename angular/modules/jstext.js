"use strict";

var jstext = angular.module("jstext", []);

jstext.controller("GameController", ["$scope", "$http", function ($scope, $http) {

    $scope.restart = function () {
        $scope.log = [];
        $scope.gameState = $scope.game.start($scope.print, $scope.onWin);
        $scope.inventory = $scope.gameState.inventory.objects;
    };

    $scope.onWin = function () {
        $scope.restart();
    };

    $http.get("game/test.json").then(function (result) {
        $scope.game = new Game(result.data);
        $scope.restart();
        $scope.updateLocation();
    });

    $scope.updateLocation = function () {
        $scope.location = {
            name: $scope.gameState && $scope.gameState.location.name,
            image: "http://www.theodora.com/maps/new9/time_zones_4.jpg"
        };
    };
    $scope.updateLocation();

    $scope.print = function (text) {
        $scope.log.push({index: $scope.log.length, text: text});
    };

    $scope.input = "";
    $scope.command = function command() {
        if ($scope.input === "restart") {
            $scope.restart();
        } else {
            $scope.gameState.enter($scope.input);
            $scope.updateLocation();
        }
        $scope.input = "";
    };
}]);