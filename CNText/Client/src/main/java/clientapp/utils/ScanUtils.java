package clientapp.utils;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Scanner;

public class ScanUtils {
    private static Scanner sc = new Scanner(System.in);

    public static String getInputString(String title, String def) {
        System.out.println(title + " <" + def + ">");
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

    public static int getInputInt(String title, int def) {
        String res = "placeholder";
        while (!isNumeric(res)){
            System.out.println(title + " <" + def + ">");
            res = sc.nextLine();
            if(res.equals("")){
                System.out.println("Default Value Selected <" + def + ">");
                return def;
            }
        }
        return Integer.parseInt(res);
    }

    private static boolean isNumeric(String str) {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
    }
}
