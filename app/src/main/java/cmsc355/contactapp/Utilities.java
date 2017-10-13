package cmsc355.contactapp;

import java.util.Random;

class Utilities {
    static String GenerateRandomString(int length) {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for(int i =0; i<stringBuilder.capacity();i++) {
            stringBuilder.append((char) ('a' + random.nextInt(26)));
        }
        return stringBuilder.toString();
    }
}
