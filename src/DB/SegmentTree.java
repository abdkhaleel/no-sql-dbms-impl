package DB;
import java.util.function.BinaryOperator;

public class SegmentTree<E> {
    private final E[] data;
    private final E[] tree;
    private final BinaryOperator<E> merger;

    @SuppressWarnings("unchecked")
    public SegmentTree(E[] arr, BinaryOperator<E> merger){
        this.merger = merger;
        this.data = (E[]) new Object[arr.length];
        System.arraycopy(arr, 0, this.data, 0, arr.length);

        this.tree = (E[]) new Object[4 * arr.length];
        buildSegmentTree(0, 0, arr.length - 1);
    }

    private void buildSegmentTree(int treeIndex, int left, int right){
        if(left == right){
            tree[treeIndex] = data[left];
            return;
        }
        int mid = (left + right) / 2;
        int leftChild = 2 * treeIndex + 1;
        int rightChild = 2 * treeIndex + 2;

        buildSegmentTree(leftChild, left, mid);
        buildSegmentTree(rightChild, mid + 1, right);

        tree[treeIndex] = merger.apply(tree[leftChild], tree[rightChild]);
    }

    public E query(int queryL, int queryR){
        return query(0, 0, data.length - 1, queryL, queryR);
    }

    private E query(int treeIndex, int left, int right, int queryL, int queryR){
        if(queryL > right || queryR < left){
            return null;
        }

        if(left >= queryL && right <= queryR){
            return tree[treeIndex];
        }

        int mid = (left + right) / 2;
        int leftChild = 2 * treeIndex + 1;
        int rightChild = 2 * treeIndex + 2;

        if(queryR <= mid){
            return query(leftChild, left, mid, queryL, queryR);
        }
        else if(queryL >= mid + 1){
            return query(rightChild, mid + 1, right, queryL, queryR);
        }

        E leftResult = query(leftChild, left, mid, queryL, mid);
        E rightResult = query(rightChild, mid + 1, right, mid+1, queryR);

        return merger.apply(leftResult, rightResult);
    }

    public void update(int index, E value){
        data[index] = value;
        update(0, 0, data.length - 1, index, value);
    }

    private void update(int treeIndex, int left, int right, int index, E value){
        if(left == right){
            tree[treeIndex] = value;
            return;
        }

        int mid = (left + right) / 2;
        int leftChild = 2 * treeIndex + 1;
        int rightChild = 2 * treeIndex + 2;

        if(index <= mid){
            update(leftChild, left, mid, index, value);
        }
        else{
            update(rightChild, mid + 1, right, index, value);
        }

        tree[treeIndex] = merger.apply(tree[leftChild], tree[rightChild]);
    }
}
