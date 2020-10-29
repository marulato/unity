package org.legion.unity.common.utils;


import java.security.SecureRandom;

public class MiscGenerator {

    private static final String SEQ_STAFF_ID = "STAFF_ID";
    private static final char[] NUMBER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] UPPER_CASE = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                                            'N', 'O', 'P', 'Q', 'R', 'S','T', 'U', 'V', 'W', 'X', 'Y','Z'};
    private static final char[] LOWER_CASE = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                                            'n', 'o', 'p', 'q', 'r', 's','t', 'u', 'v', 'w', 'x', 'y','z'};
    private static final char[] SYMBOL = {'!', '@', '$', '&', '/', '~', '*', '?', '^', '%', '#'};

    private static final char[] RANDOM_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                                                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                                                'N', 'O', 'P', 'Q', 'R', 'S','T', 'U', 'V', 'W', 'X', 'Y','Z',
                                                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                                                'n', 'o', 'p', 'q', 'r', 's','t', 'u', 'v', 'w', 'x', 'y','z',
                                                '!', '@', '$', '&', '/', '~', '*', '?', '^', '%', '#'};




    public static String generateInitialPassword() {
        return generateInitialPassword(10);
    }

    public static String generateInitialPassword(int length) {
        if (length <= 0) {
            length = 10;
        }
        SecureRandom random = new SecureRandom();
        StringBuilder pwd = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = RANDOM_CHAR[random.nextInt(RANDOM_CHAR.length)];
            pwd.append(ch);
        }
        return pwd.toString();
    }

    public static String generateCodeNumber(int length) {
        char[] codes = ArrayUtils.joint(UPPER_CASE, NUMBER, LOWER_CASE);
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char ch = codes[random.nextInt(codes.length)];
            code.append(ch);
        }
        return code.toString();
    }

    public static String generateVerificationCode() {
        return generateCodeNumber(6).toUpperCase();
    }

    public static String generateToken() {
        return generateCodeNumber(24);
    }

}
