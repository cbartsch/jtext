"use strict";

var jstext = angular.module("jstext", ['ngSanitize']);

function formatText(text) {
    return text.replace(/%n/g, "<br/>");
}

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
        $scope.location = $scope.gameState ? {
            text: formatText($scope.gameState.location.text),
            image: $scope.gameState.location.imageUrl || "http://www.theodora.com/maps/new9/time_zones_4.jpg"
        } : {};
    };
    $scope.updateLocation();

    $scope.print = function (text) {
        $scope.log.push({
            index: $scope.log.length,
            text: formatText(text)
        });
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