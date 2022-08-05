package banking;

import java.util.Arrays;
import java.util.stream.IntStream;

public class LuhnAlgorithm {

    public static int calculateCheckSum(String number) {
        if (!number.matches("\\d{15}")) {
            return -1;
        }
        int[] digits = Arrays.stream(number.split(""))
                .mapToInt(Integer::parseInt).toArray();
        for (int i = 0; i < digits.length; i += 2) {
            digits[i] *= 2;
            if (digits[i] > 9) {
                digits[i] -= 9;
            }
        }
        int checkSum = IntStream.of(digits).sum();
        return checkSum % 10 == 0 ? 0 : 10 - (checkSum % 10);
    }

    public static boolean check(String number) {
        if (!number.matches("\\d{16}")) {
            return false;
        }
        return calculateCheckSum(number.substring(0, 15))
                == Integer.parseInt(number.substring(15, 16));
    }
}
