package utils;

public class Console {

    public enum PrintType {
        ERROR,
        INFO,
        WARNING
    }

    public static void print(String msg) {
        print(PrintType.INFO, msg);
    }

    public static void print(PrintType type, String msg) {
        long threadId = Thread.currentThread().getId();
        System.out.println("[" + threadId + "]" + "[" + type + "] " + msg);
    }
}
