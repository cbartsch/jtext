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

    var addLog = function(text, type) {
        $scope.log.push({
            index: $scope.log.length,
            text: text,
            type: type
        });
    };

    $scope.print = function (text) {
        addLog(formatText(text), "output");
    };

    $scope.input = "";
    $scope.command = function command() {
        addLog($scope.input, "input");
        if ($scope.input === "restart") {
            $scope.restart();
        } else {
            $scope.gameState.enter($scope.input);
            $scope.updateLocation();
        }
        $scope.input = "";
    };
}]);