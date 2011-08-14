package nl.erikjan.generators.testdata.framework;

import java.util.Random;

public class RandomUtil {
    private static final char[] CHARS = { 'q', 'w', 'e', 'r', 't', 'z', 'u', 'i',
            'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'y', 'x', 'c', 'v',
            'b', 'n', 'm', 'ü', 'ï', 'ø', 'ö' };

    private static final Random random = new Random();

    /**
     * Instantiates random long between given min and max
     *
     * @param min
     *            the minimal value (including)
     * @param max
     *            the maximal value (excluding)
     * @return
     */
    public static long randomBetween(long min, long max) {
        long rl = random.nextLong();
        if (rl >= min && rl < max) {
            return rl;
        } else {
            long between = Math.max(max - min, 1);
            return min + (Math.abs(rl % between));
        }
    }

    /**
     * Instantiates random long between given min and max
     *
     * @param min
     *            the minimal value
     * @param max
     *            the maximal value
     * @return the random long created
     */
    public static int randomBetween(int min, int max) {
        int ri = random.nextInt(Math.max(max - min, 1));
        return ri + min;
    }

    /**
     * Instantiates random double given min and max
     *
     * @param min
     *            the minimal value
     * @param max
     *            the maximal value
     * @return the random double created
     */
    public static double randomBetween(double min, double max) {
        double randomDouble = Math.random();
        double r = ((randomDouble) * (max - min));
        return r + min;
    }

    /**
     * Instantiates random char
     */
    public static char randomChar() {
        long positiveLong = randomBetween(0, CHARS.length);
        return CHARS[(int)positiveLong];
    }

    /**
     * Method initRandomInt.
     *
     * @return int
     */
    public static int nextInt() {
        return random.nextInt();
    }

    /**
     * Method initRandomLong.
     *
     * @return long
     */
    public static long nextLong() {
        return random.nextLong();
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
