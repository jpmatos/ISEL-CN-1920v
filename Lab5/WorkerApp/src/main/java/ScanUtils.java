import java.util.Scanner;

public class ScanUtils {
    private static Scanner sc = new Scanner(System.in);

    public static String getInputString(String title){
        System.out.println(title);
        String tmp = sc.nextLine();
        return tmp;
    }

    public static int getInputInt(String title){
        System.out.println(title);
        int tmp = sc.nextInt();
        sc.nextLine();
        return tmp;
    }

    public static double getInputDouble(String title){
        System.out.println(title);
        double tmp = sc.nextFloat();
        sc.nextLine();
        return tmp;
    }
}
