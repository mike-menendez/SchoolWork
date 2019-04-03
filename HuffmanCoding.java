import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String english = scan.nextLine();
        String matrix = scan.nextLine();
        System.out.println(Huffy.encode(english));
        System.out.print(Huffy.decode(matrix));
    }
}

class Huffy {
    private static HashMap<Character, String> valMap = new HashMap<>();
    private static HashMap<String, Character> decodeMap = new HashMap<>();

    public static String encode(String s) {

        HashMap<String, Integer> freqMap = new HashMap<>();
        MinHeap huffy = new MinHeap();
        ArrayList<String> sortedKeys = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        
        Arrays.stream(s.split("")).forEach(c -> freqMap.put(c, (freqMap.containsKey(c)) ? freqMap.get(c) + 1 : 1));
        freqMap.keySet().iterator().forEachRemaining(x -> sortedKeys.add(x));
        Collections.sort(sortedKeys);
        sortedKeys.forEach((c -> huffy.push(new HNode(c.charAt(0), freqMap.get(c)))));

        while (huffy.data.size() > 1) {
            HNode temp = huffy.pop();
            HNode temp2 = huffy.pop();
            HNode temp3 = new HNode('$', temp.freq + temp2.freq);
            temp3.left = temp;
            temp3.right = temp2;
            huffy.insert(temp3);
        }

        valMap = dfs(huffy.data.get(0), "", new HashMap<>());
        Arrays.stream(s.split("")).forEach(x -> result.append(valMap.get(x.charAt(0))));

        return result.toString();
    }

    static HashMap<Character, String> dfs(HNode n, String s, HashMap<Character, String> map) {
        if (n.left != null) dfs(n.left, s + ("0"), map);
        if (n.right != null) dfs(n.right, s + ('1'), map);
        if (n.left == null && n.right == null) map.put(n.c, s);
        return map;
    }

    public static String decode(String s) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        
        valMap.keySet().iterator().forEachRemaining(x -> decodeMap.put(valMap.get(x), x));

        for (int i = 0; i < s.length(); i++) {
            
            temp.append(s.charAt(i));
            
            if (decodeMap.containsKey(temp.toString())) {
                
                sb.append(decodeMap.get(temp.toString()));
                temp = new StringBuilder();
                
            }
        }
        return sb.toString();
    }
}

class HNode {
    char c;
    int freq;
    HNode left;
    HNode right;

    public HNode(char c, int freq) {
        this.c = c;
        this.freq = freq;
        this.left = null;
        this.right = null;
    }
};

class MinHeap {
    ArrayList<HNode> data;

    public MinHeap() {
        data = new ArrayList<>();
    }

    public HNode extractMin() {
        HNode temp = data.get(0);
        swap(0, data.size() - 1);
        data.remove(temp);
        heapifyDown(0);
        return temp;
    }

    public void insert(HNode val) {
        data.add(val);
        heapifyUp(data.size() - 1);
    }

    public int size() {
        return data.size();
    }

    public Boolean empty() {
        return data.isEmpty();
    }

    public void print() {
        for (HNode n : data) {
            System.out.println(n.c + ": " + n.freq);
        }
    }

    public HNode pop() {
        return extractMin();
    }

    public void push(HNode val) {
        insert(val);
    }

    private void heapifyUp(int idx) {
        if (idx > 0) {
            int parent = (idx - 1) / 2;
            if (data.get(parent).freq > data.get(idx).freq) {
                swap(parent, idx);
                heapifyUp(parent);
            }
        }
    }

    private void heapifyDown(int i) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int mini = i;
        if (left < data.size() && data.get(left).freq < data.get(mini).freq) {
            mini = left;
        }
        if (right < data.size() && data.get(right).freq < data.get(mini).freq) {
            mini = right;
        }

        if (mini != i) {
            swap(mini, i);
            heapifyDown(mini);
        }
    }

    private void swap(int i, int j) {
        HNode temp = data.get(i);
        data.set(i, data.get(j));
        data.set(j, temp);
    }

}
