package heap;
/*
 * Author: Huy Nhat Ngo
 * Date: 5/22/2020
 * Purpose: implement heap by using array list in AList.java and HashTable for efficient
 * time searching element in binary tree
 */

import java.util.NoSuchElementException;

/**
 * An instance is a min-heap of distinct values of type V with
 * priorities of type P. Since it's a min-heap, the value
 * with the smallest priority is at the root of the heap.
 */
public final class Heap<V, P extends Comparable<P>> {

    // TODO 1.0: Read and understand the class invariants given in the
    // following comment:

    /**
     * The contents of c represent a complete binary tree. We use square-bracket
     * shorthand to denote indexing into the AList (which is actually
     * accomplished using its get method. In the complete tree,
     * c[0] is the root; c[2i+1] is the left child of c[i] and c[2i+2] is the
     * right child of i.  If c[i] is not the root, then c[(i-1)/2] (using
     * integer division) is the parent of c[i].
     * <p>
     * Class Invariants:
     * <p>
     * The tree is complete:
     * 1. `c[0..c.size()-1]` are non-null
     * <p>
     * The tree satisfies the heap property:
     * 2. if `c[i]` has a parent, then `c[i]`'s parent's priority
     * is smaller than `c[i]`'s priority
     * <p>
     * In Phase 3, the following class invariant also must be maintained:
     * 3. The tree cannot contain duplicate *values*; note that dupliate
     * *priorities* are still allowed.
     * 4. map contains one entry for each element of the heap, so
     * map.size() == c.size()
     * 5. For each value v in the heap, its map entry contains in the
     * the index of v in c. Thus: map.get(b[i]) = i.
     */
    protected AList<Entry> c;
    protected HashTable<V, Integer> map;

    /**
     * Constructor: an empty heap with capacity 10.
     */
    public Heap() {
        c = new AList<Entry>(10);
        map = new HashTable<V, Integer>();
    }

    /**
     * An Entry contains a value and a priority.
     */
    class Entry {
        public V value;
        public P priority;

        /**
         * An Entry with value v and priority p
         */
        Entry(V v, P p) {
            value = v;
            priority = p;
        }

        public String toString() {
            return value.toString();
        }
    }

    /**
     * Add v with priority p to the heap.
     * The expected time is logarithmic and the worst-case time is linear
     * in the size of the heap. Precondition: p is not null.
     * In Phase 3 only:
     *
     * @throws IllegalArgumentException if v is already in the heap.
     */
    public void add(V v, P p) throws IllegalArgumentException {
        // TODO 1.1: Write this whole method. Note that bubbleUp is not implemented,
        // so calling it will have no effect. The first tests of add, using
        // test00Add, ensure that this method maintains the class invariant in
        // cases where no bubbling up is needed.
        // When done, this should pass test100Add.
        // TODO 3.1: Update this method to maintain class invariants 3-5.

        if (map.containsKey(v))
            throw new IllegalArgumentException();

        c.append(new Entry(v, p));
        int i = c.size - 1;
        map.put(c.get(i).value, i);
        if (p.compareTo(c.get((i - 1) / 2).priority) > 0) {
            return;
        } else {
            bubbleUp(i);
        }
    }

    /**
     * Return the number of values in this heap.
     * This operation takes constant time.
     */
    public int size() {
        return c.size();
    }

    /**
     * Swap c[h] and c[k].
     * precondition: h and k are >= 0 and < c.size()
     */
    protected void swap(int h, int k) {
        //TODO 1.2: When bubbling values up and down (later on), two values,
        // c[h] and c[k], will have to be swapped. In order to always get this right,
        // write this helper method to perform the swap.
        // When done, this should pass test110Swap.
        //
        // TODO 3.2 Change this method to additionally maintain class
        //  invariants 3-5 by updating the map field.

        Entry temp = c.get(h);
        c.put(h, c.get(k));
        map.put(c.get(k).value, h);
        c.put(k, temp);
        map.put(temp.value, k);
    }

    /**
     * Bubble c[k] up in heap to its right place.
     * Precondition: Priority of every c[i] >= its parent's priority
     * except perhaps for c[k]
     */
    protected void bubbleUp(int k) {
        // TODO 1.3 As you know, this method should be called within add in order
        // to bubble a value up to its proper place, based on its priority.
        // When done, this should pass test115Add_BubbleUp
        while (c.get(k).priority.compareTo(c.get((k - 1) / 2).priority) < 0) {
            swap(k, (k - 1) / 2);
            k = (k - 1) / 2;
        }
    }

    /**
     * Return the value of this heap with lowest priority. Do not
     * change the heap. This operation takes constant time.
     *
     * @throws NoSuchElementException if the heap is empty.
     */
    public V peek() throws NoSuchElementException {
        // TODO 1.4: Do peek. This is an easy one.
        //         test120Peek will not find errors if this is correct.
        if (c.size == 0)
            throw new NoSuchElementException();
        return c.get(0).value;
    }

    /**
     * Remove and return the element of this heap with lowest priority.
     * The expected time is logarithmic and the worst-case time is linear
     * in the size of the heap.
     *
     * @throws NoSuchElementException if the heap is empty.
     */
    public V poll() throws NoSuchElementException {
        // TODO 1.5: Do poll (1.5) and bubbleDown (1.6) together. When they
        // TODO 3.3: Update poll() to maintain class invariants 3-5.
        // are written correctly, testing procedures
        // test30Poll_BubbleDown_NoDups and test140testDuplicatePriorities
        // should pass. The second of these checks that entries with equal
        // priority are not swapped.
        //
        if (c.size == 0)
            throw new NoSuchElementException();
        else if (c.size == 1) {
            map.remove(c.get(0).value);
            return c.pop().value;
        } else if (c.size == 2) {
            V temp = c.get(0).value;
            map.remove(c.get(0).value);
            c.put(0, c.pop());
            map.put(peek(), 0);
            return temp;
        } else {
            V temp = c.get(0).value;
            map.remove(c.get(0).value);
            c.put(0, c.pop());
            map.put(peek(), 0);
            bubbleDown(0);
            return temp;
        }
    }

    /**
     * Bubble c[k] down in heap until it finds the right place.
     * If there is a choice to bubble down to both the left and
     * right children (because their priorities are equal), choose
     * the right child.
     * Precondition: Each c[i]'s priority <= its childrens' priorities
     * except perhaps for c[k]
     */
    protected void bubbleDown(int k) {
        // TODO 1.6: Do poll (1.5) and bubbleDown together.  We also suggest
        //         implementing and using smallerChild, though you don't
        //         have to.

        while (c.get(k).priority.compareTo(c.get(smallerChild(k)).priority) > 0) {
            int smallerElement = smallerChild(k);
            swap(k, smallerElement);
            k = smallerElement;
            if (2 * k + 1 >= c.size)
                break;
        }
    }


    // Recommended helper method spec:
    /* Return the index of the child of k with smaller priority.
     * if only one child exists, return that child's index
     * Precondition: at least one child exists.*/
    private int smallerChild(int k) {
        if (c.get(2 * k + 1) != null && (2 * k + 2) >= c.size) {
            return 2 * k + 1;
        } else {
            if (c.get(2 * k + 1).priority.compareTo(c.get(2 * k + 2).priority) < 0)
                return 2 * k + 1;
            else
                return 2 * k + 2;
        }
    }


    /**
     * Return true if the value v is in the heap, false otherwise.
     * The average case runtime is O(1).
     */
    public boolean contains(V v) {
        // TODO 3.4: Use map to check whether the value is in the heap.
        return map.containsKey(v);
    }

    /**
     * Change the priority of value v to p.
     * The expected time is logarithmic and the worst-case time is linear
     * in the size of the heap.
     *
     * @throws IllegalArgumentException if v is not in the heap.
     */
    public void changePriority(V v, P p) throws IllegalArgumentException {
        // TODO 3.5: Implement this method to change the priority of node in
        // the heap.
        if (!map.containsKey(v))
            throw new IllegalArgumentException();
        else {
            int i = map.get(v);
            Entry temp = c.get(i);
            temp.priority = p;
            if (i * 2 + 1 >= c.size) {
                //if changed priority element is a leaf, bubble up only
                if (temp.priority.compareTo(c.get((i - 1) / 2).priority) < 0) {
                    bubbleUp(i);
                }
            } else if (temp.priority.compareTo(c.get((i-1)/2).priority) < 0) {
                //if changed priority element is has child, bubble up if c[i] priority is smaller than its
                //parent's priority
                bubbleUp(i);
            } else if (temp.priority.compareTo(c.get(2 * i + 1).priority) > 0
            || (temp.priority.compareTo(c.get(2 * i + 2).priority) > 0)) {
                //if changed priority element is has child, bubble down if c[i] priority is greater than its children
                //priority
                bubbleDown(i);
            }
        }
    }
}
