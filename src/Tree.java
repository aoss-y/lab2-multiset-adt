import java.util.ArrayList; // We could implement this class with basic arrays. But 1. Thats annoying (So many helper methods) and 2. Hard to extend. (New methods may need new helpers)
import java.util.List;
import java.util.Random;

public class Tree {
    private Integer root; // Could be null.
    private List<Tree> subtrees; // Always valid.

    public Tree(int item, Tree[] subtrees) {
        this.root = item;
        this.subtrees = new ArrayList<Tree>();

        for(int i = 0; i < subtrees.length; ++i)
            this.subtrees.add(subtrees[i]);
    }

    public Tree(int item, List<Tree> subtrees) {
        this.root = item;
        this.subtrees = new ArrayList<Tree>();

        this.subtrees.addAll(subtrees);
    }

    // Follow representation invariants such that subtrees is always a valid array.
    public Tree() {
        this.root = null;
        this.subtrees = new ArrayList<Tree>();
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public int len() {
        if (this.isEmpty()) return 0;
        if (this.subtrees.isEmpty()) return 1;

        int count = 1;
        for (Tree t : this.subtrees) 
            count += t.len();

        return count;
    }
    public int count(int item) {
        if (this.isEmpty()) return 0;

        int count = this.root == item ? 1 : 0; //Ternary Operator, to condense an if statement into a single line. Read as cond ? then : else.
        for (Tree t : this.subtrees)
            count += t.count(item);
        return count;
    }

    @Override
    public String toString() {
        // The python version calls a private helper. So we do the same.
        return strHelper(0);
    }

    private String strHelper(int depth) {
        if (this.isEmpty()) return "";

        String out = "";
        for(int i = 0; i < depth; ++i)
            out += " ";
        out += this.root.toString() + "\n";

        for (Tree t : this.subtrees)
            out += t.strHelper(depth + 1);
        return out;
    }

    public float average() {
        if (this.isEmpty()) return 0f;

        float[] data = averageHelper();
        return data[0] / data[1];
    }

    private float[] averageHelper() {
        if (this.isEmpty()) return new float[] {0f, 0f};

        float total = (float)this.root;
        float size = 1f;
        for (Tree t : this.subtrees) {
            float[] data = t.averageHelper();
            total += data[0];
            size += data[1];
        }

        return new float[] {total, size};
    }

    public boolean equals(Tree other) {
        if (this.isEmpty() && other.isEmpty()) return true;
        if (this.isEmpty() || other.isEmpty()) return false;

        if (this.root != other.root) return false;
        if (this.subtrees.size() != other.subtrees.size()) return false;

        return this.subtrees.equals(other.subtrees);
    }

    public boolean contains(int item) {
        if (this.isEmpty()) return false;
        if (this.root == item) return true;

        for (Tree t : this.subtrees) {
            if (t.contains(item)) return true;
        }
        return false;
    }


    public int[] leaves() {
        if (this.isEmpty()) return new int[0];
        if (this.subtrees.isEmpty()) return new int[] { this.root };

        int[] out = new int[0];
        for (Tree t : this.subtrees) {
            out = mergeArray(out, t.leaves());
        }
        return out;
    }

    private static int[] mergeArray(int[] a, int[] b) {
        int[] out = new int[a.length + b.length];

        for(int i = 0; i < a.length; ++i)
            out[i] = a[i];
        for(int j = 0; j < b.length; ++j) 
            out[j + a.length] = b[j];

        return out;
    }

    public boolean deleteItem(int item) {
        if (this.isEmpty()) return false;
        if (this.root == item) {
            this.deleteRoot();
           return true;
        }

        for (Tree t : this.subtrees) {
            boolean deleted = t.deleteItem(item);
            if (deleted && t.isEmpty()) {
                this.subtrees.remove(t);
                return true;
            } else if (deleted) {
                return true;
            }
        }
        return false;
    }

    private void deleteRoot() {
        if (this.subtrees.isEmpty()) {
            this.root = null;
            return; 
        }

        Tree chosenSubtree = this.subtrees.removeLast();

        this.root = chosenSubtree.root;
        this.subtrees.addAll(chosenSubtree.subtrees);
    }

    public void insert(int item) {
        if (this.isEmpty()) { 
            this.root = item; 
            return;
        }
        if (this.subtrees.isEmpty()) {
            this.subtrees.add(new Tree(item, new Tree[0]));
            return;
        }

        Random rand = new Random();
        if (rand.nextInt(4) == 3) {
            this.subtrees.add(new Tree(item, new Tree[0]));
        } else {
            int ridx = rand.nextInt(this.subtrees.size());
            this.subtrees.get(ridx).insert(item);
        }
    }

    public boolean insertChild(int child, int parent) {
        if (this.isEmpty()) return false;
        if (this.root == parent) {
            this.subtrees.add(new Tree(child, new Tree[0]));
            return true;
        }

        for (Tree t : this.subtrees) {
            if (t.insertChild(child, parent)) return true;
        }
        return false;
    }
}
