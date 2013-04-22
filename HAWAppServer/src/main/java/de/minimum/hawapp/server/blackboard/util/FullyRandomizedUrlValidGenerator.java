package de.minimum.hawapp.server.blackboard.util;

import java.util.Random;

import de.minimum.hawapp.server.blackboard.api.Offer;

public class FullyRandomizedUrlValidGenerator implements KeyGenerator {
    public static final char[] USED_CHARS;
    private static final int NUM_CHARS;
    private static final Random RANDOMIZER = new Random();

    static {
        int i = 0;
        USED_CHARS = new char[('9' - '0' + 1) + ('Z' - 'A' + 1) + ('z' - 'a' + 1)];
        for(char c = '0'; c <= '9'; c++) {
            FullyRandomizedUrlValidGenerator.USED_CHARS[i] = c;
            i++;
        }
        for(char c = 'A'; c <= 'Z'; c++) {
            FullyRandomizedUrlValidGenerator.USED_CHARS[i] = c;
            i++;
        }
        for(char c = 'a'; c <= 'z'; c++) {
            FullyRandomizedUrlValidGenerator.USED_CHARS[i] = c;
            i++;
        }
        NUM_CHARS = FullyRandomizedUrlValidGenerator.USED_CHARS.length;
    }

    @Override
    public String generate(Offer offer, int length) {
        String key = "";
        for(int i = 0; i < length; i++) {
            key += FullyRandomizedUrlValidGenerator.USED_CHARS[FullyRandomizedUrlValidGenerator.RANDOMIZER
                            .nextInt(FullyRandomizedUrlValidGenerator.NUM_CHARS)];
        }
        return key;
    }
}
