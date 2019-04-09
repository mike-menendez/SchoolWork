import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
//        Scanner scan = new Scanner(System.in);
//        int iterations = scan.nextInt();
//        for(int i =0; i < iterations; i++){
//            a.add(scan.nextInt());
//        }
        Random rand = new Random();
        int j = 10000000;
        for (int i = 0; i < j; i++) {
            int temp = rand.nextInt();
            if ((temp >= 0)) {
                a.add(temp);
                b.add(temp);
            } else {
                a.add(temp * -1);
                b.add(temp);
            }
        }

        System.out.println("Sorting " + j + " number of elements");
        long start = System.currentTimeMillis();
        magic(a);
        long end = System.currentTimeMillis();
        System.out.println("Radish took: " + (end - start));
        start = System.currentTimeMillis();
        Collections.sort(b);
        end = System.currentTimeMillis();
        System.out.println("Collections.sort took: " + (end - start));
    }

    private static ArrayList<Integer> magic(ArrayList<Integer> a) {
        int max = a.get(0);
        ArrayList<ArrayList<Integer>> buckets = new ArrayList();
        for (int k = 0; k < 10; k++) {
            buckets.add(new ArrayList<>());
        }
        ArrayList<Integer> results = new ArrayList<>();

        for (int i = 0; i < a.size(); i++) {
            if (max < a.get(i)) {
                max = a.get(i);
            }
        }

        for (int i = 0; i < Integer.toString(max).length(); i++) {
            buckets.forEach(x -> x.clear());
            results.clear();
            int j = i + 1;
            a.forEach(n -> {
                if ((n.toString().length() - j) >= 0) {
                    buckets.get((Integer.parseInt(Character.toString((n.toString()).charAt(n.toString().length() - j))))).add(n);
                } else {
                    buckets.get(0).add(n);
                }
            });
            buckets.forEach(n -> n.forEach(x -> {
                results.add(x);
            }));
            a = results;
        }
        return a;
    }
}
