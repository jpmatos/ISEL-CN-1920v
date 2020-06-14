package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;

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
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        long threadId = Thread.currentThread().getId();

        System.out.println("[" + dtf.format(now) + "]" + "[Thread " + threadId + "]" + "[" + type + "] " + msg);
    }
}
