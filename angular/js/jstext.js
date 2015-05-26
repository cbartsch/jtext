"use strict";

var jstext = angular.module("jstext", []);

var consolePrinter = function(text) {
    console.log(text);
};

jstext.run(["$http", "$rootScope", function(http, scope) {
    scope.text = "";
    var req = http.get("game/test.json");

    req.then(function(obj) {
        console.log("game loaded");

        scope.game = new Game(obj.data);

        scope.gameState = scope.game.start(consolePrinter);
    });

    scope.enter = function() {
        scope.gameState.enter(scope.text);
    }
}]);