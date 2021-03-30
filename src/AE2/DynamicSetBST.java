package AE2;

public class DynamicSetBST<Item>{
    public Node root;
    private int globalIndex = 0;

    private class Node<Item> implements Comparable<Item>{
        Item key;
        private Node left, right, parent;

        public Node(Item key){
            this.key = key;
            this.left=this.right=this.parent=null;
        }

        @Override
        public String toString() {
            return "key: "+key+" | size: "+size(this);
        }

        @Override
        public int compareTo(Item o) {
            return (Integer) this.key - (Integer) o;
        }
    }

    public DynamicSetBST(){
        this.root = null;
    }

    //main operations
    //add an element
    public boolean add(Item key){
        //check if element is there
        if(!(isElement(this.root, key)==null))
            return false;
        Node x = new Node(key);
        Node y = null;
        Node root = this.root;
        //add item based on greater than or less than parent, skip loop if no parent (root)
        while(root!=null){
            y=root;
            if(x.compareTo(root.key)<0)
                root = root.left;
            else
                root = root.right;
        }
        //set parent (or root if no parent)
        x.parent = y;
        if(y==null)
            this.root = x;
        else if(x.compareTo(y.key)<0)
                y.left = x;
        else
            y.right = x;
        return true;
    }

    //remove an element
    public boolean remove(Item key){
        //check if element is there
        Node toRemove = isElement(this.root, key);
        if(toRemove==null)
            return false;
        //if not, remove element and transplant accordingly
        if(toRemove.left==null)
            transplant(toRemove, toRemove.right);
        else
            if(toRemove.right==null)
            transplant(toRemove, toRemove.left);
        else{
            //find successor if it has two children subtrees, transplant based on whether
            //it is directly linked to parent or not
            Node successor = minimum(toRemove.right);
            if (successor.parent!=toRemove){
                transplant(successor, successor.right);
                successor.right = toRemove.right;
                successor.right.parent = successor;
            }
            else {
                transplant(toRemove, successor);
                successor.left = toRemove.left;
                successor.left.parent = successor;
            }
        }
        return true;
    }

    //checks if element is present
    public Node isElement(Node root, Item key){
        if(root==null || root.key==key) return root;
        if(root.compareTo(key)<0) return isElement(root.right, key);
        else return isElement(root.left, key);
    }

    //checks if set is empty (no root)
    public boolean setEmpty(){
        return root==null;
    }

    //returns the size of the set, uses recursive helper function 'size()'
    public int setSize(){
        return size(root);
    }

    //auxiliary operations
    //all operations make use of 'inorderArrayGenerator' and reset the globalIndex value to '0'
    //after calling the method so that future uses don't go out of bounds

    //find the union of two sets
    public DynamicSetBST union(DynamicSetBST t){
        Node[] arr = new Node[this.setSize()];
        arr= inorderArrayGenerator(this.root, arr);
        globalIndex = 0;
        for(Node node : arr){
            //if element isn't already present in t then add
            if(t.isElement(t.root, node.key)==null)
                t.add(node.key);
        }
        return t;
    }

    public DynamicSetBST unionButBetter(DynamicSetBST t){
        DynamicSetBST union = new DynamicSetBST();
        Node[] a = new Node[this.setSize()];
        a = inorderArrayGenerator(this.root, a);
        globalIndex = 0;
        Node[] b = new Node[t.setSize()];
        b = inorderArrayGenerator(t.root, b);
        globalIndex = 0;
        Node[] merged = arrayMerger(a, b, a.length, b.length);
        union.root = arrayToBST(merged, 0, merged.length-1);
        return union;
    }
    public Node[] arrayMerger(Node[] a, Node[] b, int p, int q){
        Node[] merger = new Node[p+q];
        int i = 0;
        int j = 0;
        int c =0; //index value for merger
        int common=0; //number of common elements
        //add smaller elements first
        while(i<p && j<q){
            if(a[i].compareTo(b[j].key)<0) {
                merger[c++] = a[i++];
            }
            else if(a[i].compareTo(b[j].key)>0){
                merger[c++] = b[j++];
            }
            else{
                merger[c++] = a[i++];
                j++;
                common++;
            }
        }
        //add remaining elements
        while(i<p)
            merger[c++] = a[i++];
        while(j<q)
            merger[c++] = b[j++];
        //create new array to be returned, size = sum of arrays to be merged - number of common elements
        Node[] result = new Node[p+q-common];
        //map elements from merger to result
        for(int k=0; k<result.length; k++)
            result[k]=merger[k];
        return result;
    }
    public Node arrayToBST(Node[] arr, int start, int end){
        if(start>end)
            return null;
        int mid = (start+end)/2;
        //find mid value as root, call recursively to create left and right subtress
        Node node = arr[mid];
        node.left = arrayToBST(arr, start, mid-1);
        node.right = arrayToBST(arr, mid+1, end);
        return node;
    }

    //intersection of two sets
    public DynamicSetBST<Item> intersection(DynamicSetBST t){
        DynamicSetBST<Item> intersectionSet = new DynamicSetBST<>();
        Node[] arr = new Node[this.setSize()];
        arr= inorderArrayGenerator(this.root, arr);
        globalIndex = 0;
        for(Node node : arr){
            //if element is present in t then add
            if(t.isElement(t.root, node.key)!=null)
                intersectionSet.add((Item)node.key);
        }
        return intersectionSet;
    }

    //difference between two sets
    public DynamicSetBST difference(DynamicSetBST t){
        Node[] arr = new Node[t.setSize()];
        arr = inorderArrayGenerator(t.root, arr);
        globalIndex = 0;
        for(Node node : arr){
            Node x = this.isElement(this.root, (Item) node.key);
            if(x!=null)
                this.remove((Item) node.key);
        }
        return this;
    }

    //subset check
    public boolean subset(DynamicSetBST t){
        if(this.setSize()>t.setSize())
            return false;
        Node[] arr = new Node[this.setSize()];
        arr = inorderArrayGenerator(this.root, arr);
        globalIndex = 0;
        for(Node node : arr){
            if(t.isElement(t.root, node.key)==null)
                return false;
        }
        return true;
    }

    //helper functions
    //prints the set through inorder traversal recursively
    public void inorder(Node x){
        if(x!=null){
            inorder(x.left);
            System.out.print(x.key+",");
            inorder(x.right);
        }
    }

    //generates an array of nodes through inorder traversal, similar to printing inorder
    public Node[] inorderArrayGenerator(Node x, Node[] arr){
        if(x!=null){
            inorderArrayGenerator(x.left, arr);
            arr[globalIndex++] = x;
            inorderArrayGenerator(x.right, arr);
        }
        return arr;
    }

    //appropriately appoints pointers to new nodes after deletion
    public void transplant(Node u, Node v){
        if(u.parent==null)
            this.root = v;
        else if(u==u.parent.left)
            u.parent.left = v;
        else u.parent.right = v;
        if(v!=null) {
            v.parent = u.parent;
        }
    }

    //used to find successor to a node, typically minimum value in right subtree
    public Node minimum(Node x){
        while(x.left!=null)
            x = x.left;
        return x;
    }

    //returns the size of the tree recursively (no. of nodes)
    public int size(Node node){
        if(node==null) return 0;
        else return (1+(size(node.left))+(size(node.right)));
    }

    //similar to size, but returns height of the free
    //(maximum height of the two subtrees + 1 for current node)
    public int height(Node node){
        if(node==null) return 0;
        else return (1+ Integer.max(height(node.right), height(node.left)));
    }

    public static void main(String[] args){
        DynamicSetBST<Integer> set = new DynamicSetBST<>();
        set.add(10);
        set.add(8);
        set.add(6);
        set.add(9);
        set.add(11);
        System.out.print("SET A: ");
        System.out.print(set);
        DynamicSetBST<Integer> t = new DynamicSetBST<>();
        t.add(10);
        t.add(14);
        t.add(9);
        t.add(6);
        System.out.print("SET B: ");
        System.out.print(t);
        System.out.println("SUBSET: "+set.subset(t));
        DynamicSetBST intersection = set.intersection(t);
        System.out.print("INTERSECTION: ");
        System.out.print(intersection);
        DynamicSetBST difference = set.difference(t);
        System.out.print("DIFFERENCE: ");
        System.out.print(difference);
        DynamicSetBST union = set.unionButBetter(t);
        System.out.print("UNION: ");
        System.out.print(union);
    }

    @Override
    public String toString() {
        inorder(this.root);
        System.out.println();
        return "";
    }

}
