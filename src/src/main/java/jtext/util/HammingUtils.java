package jtext.util;

import com.google.common.base.Strings;

import java.util.Collection;

/**
 * Created by Stefan on 18/01/2015.
 */
public class HammingUtils {
    private static final int HAMMING_THRESHOLD = 5;

    public static String findClosestElement(String element, Collection<String> haystack) {
        int minDistance = Integer.MAX_VALUE;
        String  closestElement = null;
        for (String test : haystack) {
            int currentDistance = findHammingDistance(element, test);
            if(currentDistance < minDistance) {
                minDistance = currentDistance;
                closestElement = test;
            }
        }

        // If the distance is greater than the threshold, the closest element is too different
        return minDistance < HAMMING_THRESHOLD ? closestElement : null;
    }

    public static int findHammingDistance(String a, String b) {
        String shorter = (a.length() <= b.length() ? a : b).toLowerCase();
        String longer = (shorter == a ? b : a).toLowerCase();
        int hammingDistance = 0;
        // Pad String so both have the same length, use 0 so the hamming distance increases for each additional character
        shorter = Strings.padEnd(shorter, longer.length(), (char) 0);
        for (int i = 0; i < longer.length(); i++) {
            if(shorter.charAt(i) != longer.charAt(i)) {
                hammingDistance += 1;
            }
        }

        return hammingDistance;
    }

    private HammingUtils() {}
}
