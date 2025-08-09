package utils;

public class TerminalCleaner {
    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
