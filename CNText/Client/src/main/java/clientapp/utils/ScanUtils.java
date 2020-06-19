package clientapp.utils;

import java.util.Scanner;

public class ScanUtils {
    private static Scanner sc = new Scanner(System.in);

    public static String getInputString(String title, String def) {
        System.out.println(title + "<" + def + ">");
        String res = sc.nextLine();
        if (res.equals("")) {
            res = def;
            System.out.println("Default Value Selected <" + res + ">");
        }
        return res;
    }

    public static String getInputString(String title) {
        System.out.println(title);
        String res = sc.nextLine();
        return res;
    }

    public static int getInputInt(String title) {
        System.out.println(title);
        int tmp = sc.nextInt();
        sc.nextLine();
        return tmp;
    }

    public static double getInputDouble(String title) {
        System.out.println(title);
        double tmp = sc.nextFloat();
        sc.nextLine();
        return tmp;
    }
}
