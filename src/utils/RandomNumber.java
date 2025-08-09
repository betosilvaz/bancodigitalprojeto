package utils;

public class RandomNumber {

    public static int generate(int min, int max) {
        return (int) (Math.random() * (max + 1) + min);
    }

}
