import java.util.*;

public class Main {

    final static String ALPHA = "alphabetical";
    final static String INFO = "info";
    final static String INSERT = "insert";
    final static String DELETE = "remove";
    final static String LOOKUP = "lookup";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Trie t = new Trie();

        int numIterations = scan.nextInt();
        for (int i = 0; i < numIterations; i++) {
            String task = scan.next();
            if (task.equals(ALPHA)) {
                t.alphabetical();
            } else if (task.equals(INFO)) {
                System.out.println(t.numNodes + " " + t.numWords);
            } else if (task.equals(INSERT)) {
                String insertWord = scan.next();
                t.insert(insertWord);
            } else if (task.equals(DELETE)) {
                String deleteWord = scan.next();
                t.delete(deleteWord);
            } else if (task.equals(LOOKUP)) {
                String wordToLookup = scan.next();
                System.out.println(t.lookup(wordToLookup) ? "1"  : "0");
            }
        }
    }
}

class Trie {

    Node root = new Node();
    int numWords = 0;
    int numNodes = 1;

    public void insert(String s) {
        Node n = root;
        for (int i = 0; i < s.length(); i++) {
            if (n.children.containsKey(s.charAt(i))) {
                n = n.children.get(s.charAt(i));
            } else {
                Node t = new Node();
                t.letter = s.charAt(i);
                n.children.put(s.charAt(i), t);
                numNodes++;
                n = t;
            }
        }
        n.term = true;
        numWords++;
    }

    public void delete(String s) {
        Stack<Node> stack = new Stack<>();
        Node n = root;
        for (int i = 0; i < s.length(); i++) {
            if (!n.children.containsKey(s.charAt(i))) {
                return;
            } else {
                stack.push(n);
                n = n.children.get(s.charAt(i));
            }
        }

        Node temp = stack.pop();
        if (n.term == true) {
            if (n.children.size() == 0) {
                while (!stack.empty()) {
                    if (n.children.size() > 0) {
                        stack.clear();
                        numWords--;
                        return;
                    } else {
                        temp.children.remove(n.letter);
                        numNodes--;
                    }
                    n = temp;
                    temp = stack.pop();
                }
            } else {
                n.term = false;
                numWords--;
            }
        } else {
            return;
        }
    }

    public boolean lookup(String s) {

        if (root == null) {
            return false;
        } else {
            Node temp = root;
            for (int i = 0; i < s.length(); i++) {
                if (temp.children.containsKey(s.charAt(i))) {
                    temp = temp.children.get(s.charAt(i));
                } else {
                    return false;
                }
            }
            return temp.term;
        }
    }

    public void alphabetical() {
        ArrayList<String> output = new ArrayList<>();
        Arrays.stream(root.children.keySet().toArray()).sorted().forEach(x -> alphaHelper("", root.children.get(x), output));
        output.forEach(x -> System.out.println(x));
    }

    public void alphaHelper(String s, Node n, ArrayList<String> output) {
        if (n.term == true) output.add(s + n.letter);
        Arrays.stream(n.children.keySet().toArray()).sorted().forEach(x -> alphaHelper(s + n.letter, n.children.get(x), output));
    }

}

class Node {
    Character letter;
    HashMap<Character, Node> children;
    boolean term;

    Node() {
        term = false;
        letter = null;
        children = new HashMap<>();
    }
}
