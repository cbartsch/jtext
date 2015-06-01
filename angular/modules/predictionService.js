"use strict";

var prediction = angular.module("jstext.prediction", []);
prediction.constant("HAMMING_THRESHOLD", 4);

var predictionService = prediction.factory("PredictionService", ["HAMMING_THRESHOLD", function(HAMMING_THRESHOLD) {
    var service = {};
    service.findClosestElement = function findClosestElement (element, haystack) {
        this.findHammingDistance = function findHammingDistance(a, b) {
            var firstIsShorter = a.length <= b.length;
            var shorter = (firstIsShorter ? a : b).toLowerCase();
            var longer = (!firstIsShorter ? a : b).toLowerCase();
            var hammingDistance = 0;

            for(var i = 0; i < longer.length; i++) {
                // Pad String so both have the same length, use 0 so the hamming distance increases for each additional character
                if(shorter.length === i) { shorter = shorter + ' '; }
                if (shorter.charAt(i) !== longer.charAt(i)) {
                    hammingDistance += 1;
                }
            }

            return hammingDistance;
        };

        var minDistance = Number.MAX_VALUE;
        var closestElement = null;
        for (var i in haystack) {
            var test = haystack[i];
            var currentDistance = this.findHammingDistance(element, test);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestElement = test;
            }
        }

        // If the distance is greater than the threshold, the closest element is too different
        // limit threshold to length of input - 1, to avoid finding completely different strings
        var threshold = Math.min(HAMMING_THRESHOLD, element.length - 1);
        return minDistance <= threshold ? closestElement : null;
    };

    return service;
}]);