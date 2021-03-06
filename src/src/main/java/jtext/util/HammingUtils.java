package jtext.util;

import com.google.common.base.Strings;

import java.util.Collection;

/**
 * Created by Stefan on 18/01/2015.
 */
public class HammingUtils {
    private static final int HAMMING_THRESHOLD = 4;

    public static String findClosestElement(String element, Collection<String> haystack) {
        int minDistance = Integer.MAX_VALUE;
        String closestElement = null;
        for (String test : haystack) {
            int currentDistance = findHammingDistance(element, test);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                closestElement = test;
            }
        }

        // If the distance is greater than the threshold, the closest element is too different
        // limit threshold to length of input - 1, to avoid finding completely different strings
        int threshold = Math.min(HAMMING_THRESHOLD, element.length() - 1);
        return minDistance <= threshold ? closestElement : null;
    }

    public static int findHammingDistance(String a, String b) {
        boolean firstIsShorter = a.length() <= b.length();
        String shorter = (firstIsShorter ? a : b).toLowerCase();
        String longer = (!firstIsShorter ? a : b).toLowerCase();
        int hammingDistance = 0;
        // Pad String so both have the same length, use 0 so the hamming distance increases for each additional character
        shorter = Strings.padEnd(shorter, longer.length(), (char) 0);
        for (int i = 0; i < longer.length(); i++) {
            if (shorter.charAt(i) != longer.charAt(i)) {
                hammingDistance += 1;
            }
        }

        return hammingDistance;
    }

    private HammingUtils() {
    }
}
