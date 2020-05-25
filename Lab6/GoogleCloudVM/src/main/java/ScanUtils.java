import java.util.Scanner;

public class ScanUtils {
    private static Scanner sc = new Scanner(System.in);

    public static String getInputString(String title, String def){
        if(def == null || def.equals(""))
            System.out.println(title);
        else
            System.out.println(title + "<" + def + ">");
        String tmp = sc.nextLine();
        if(tmp.equals("") && (def != null || !def.equals(""))) {
            tmp = def;
            System.out.println("Default Value Selected <" + tmp + ">");
        }
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
