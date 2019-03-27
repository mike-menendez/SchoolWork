import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int iterations = scan.nextInt();
        for(int i =0; i < iterations; i++){
            a.add(scan.nextInt());
        }
        System.out.println(magic(a));
    }

    private static ArrayList<Integer> magic(ArrayList<Integer> a) {
        int max = a.get(0);
        for (int i =0; i < a.size(); i++){if (max < a.get(i)) {max = a.get(i);}}
        for (int i = 0; i < Integer.toString(max).length(); i++) {
            ArrayList<ArrayList<Integer>> map = new ArrayList();
            for(int k =0; k < 10; k++) {map.add(new ArrayList<>());}
            ArrayList<Integer> list = new ArrayList<>(); int j = i;
            a.forEach((n) -> { if (((n.toString().length() - j - 1) >= 0)) {
                    map.get((Integer.parseInt(Character.toString((n.toString()).charAt(n.toString().length() - j - 1))))).add(n);}
                else {map.get(0).add(n);} });
            map.forEach(n -> n.forEach(x -> {list.add(x);}));
            a = list;}
        return a;}}
