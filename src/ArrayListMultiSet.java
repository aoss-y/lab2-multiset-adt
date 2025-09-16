import java.util.ArrayList;

public class ArrayListMultiSet extends MultiSet {

    // this ArrayList object is a private instance variable for this class.
    private final ArrayList<Integer> lst = new ArrayList<>();

    /**
     * Add the given item to this multiset.
     *
     * @param item the item to add
     */
    @Override
    void add(int item) {
        this.lst.add(item);
    }

    /**
     * Remove the given item from this multiset.
     * If the item isn't in the multiset, do nothing.
     *
     * @param item the item to remove
     */
    @Override
    void remove(int item) {
        if (!this.lst.contains(item)) return;

        for (int i = 0; i < this.lst.size(); ++i) {
            if (this.lst.get(i) == item) {
                this.lst.remove(i);
                return; // Copying the behaviour from the .py file, this should only remove a single item at a time.
            }
        }
    }

    /**
     * Check whether item is in this multiset.
     *
     * @param item the item to check if in this multiset
     * @return True if item is in this multiset and False otherwise.
     */
    @Override
    boolean contains(int item) {
        return this.lst.contains(item);
    }

    /**
     * @return True if this multiset is empty.
     */
    @Override
    boolean isEmpty() {
        return this.lst.isEmpty();
    }

    /**
     * Count how many times the given item appears in this multiset.
     *
     * @param item the item to count
     * @return How many times item appears in this multiset.
     */
    @Override
    int count(int item) {
        int c = 0;
        for (int x : this.lst) {
            if (x == item) c++;
        }
        return c;
    }

    /**
     * @return How many items are in this multiset.
     */
    @Override
    int size() {
        return this.lst.size();
    }
}
