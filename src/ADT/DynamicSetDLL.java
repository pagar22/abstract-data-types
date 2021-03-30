package ADT;

public class DynamicSetDLL<Item>{

    private Node<Item> head;

    private static class Node<Item>{
        private Item key;
        private Node<Item> next;
        private Node<Item> prev;

        public Node(Item key){
            this.key = key;
            this.next = null;
            this.prev = null;
        }

        @Override
        public String toString() {
            return this.key.toString();
        }
    }

    public DynamicSetDLL(){
        this.head = null;
    }

    //main operations
    //add an element
    public boolean add(Item x){
        if(this.isElement(x))
            return false;
        Node<Item> node = new Node<>(x);
        node.next = this.head;
        node.prev = null;
        if(this.head!=null)
            head.prev = node;
        this.head = node;
        return true;
    }

    //remove an element
    public boolean remove(Item x){
        if(this.isElement(x)){
            Node node = this.get(x);
            if(node.prev!=null)
                node.prev.next = node.next;
            else
                this.head = node.next;
            if(node.next!=null)
                node.next.prev = node.prev;
            return true;
        }
        return false;
    }

    //check if element is present
    public boolean isElement(Item x){
        Node node = this.head;
        while(node!=null) {
            if(node.key==x)
                return true;
            node = node.next;
        }
        return false;
    }

    //check whether the set is empty (no head)
    public boolean setEmpty(){
        return this.head == null;
    }

    //returns the size of the set
    public int setSize(){
        int size = 0;
        Node node = this.head;
        while(node!=null){
            size++;
            node = node.next;
        }
        return size;
    }

    //auxiliary operations
    //union of two sets
    public DynamicSetDLL union(DynamicSetDLL t){
        DynamicSetDLL unionSet = new DynamicSetDLL();
        //copy elements from one set
        Node node = this.head;
        while(node!=null){
            unionSet.add(node.key);
            node = node.next;
        }
        //add only those elements that aren't present in the second set
        node = t.head;
        while(node!=null){
            if(!unionSet.isElement(node.key))
                unionSet.add(node.key);
            node = node.next;
        }
        return unionSet;
    }

    //intersection of two sets
    public DynamicSetDLL intersection(DynamicSetDLL t){
        DynamicSetDLL intersectionSet = new DynamicSetDLL();
        Node node = this.head;
        //only add elements that are present in both
        while(node!=null){
            if(t.isElement(node.key))
                intersectionSet.add(node.key);
            node = node.next;
        }
        return intersectionSet;
    }

    //difference between two sets
    public DynamicSetDLL difference(DynamicSetDLL t){
        DynamicSetDLL differenceSet = new DynamicSetDLL();
        //copy elements from one set
        Node node = this.head;
        while(node!=null) {
            differenceSet.add(node.key);
            node = node.next;
        }
        //remove all elements that are present in the second set
        node = t.head;
        while(node!=null){
            if(differenceSet.isElement(node.key))
                differenceSet.remove(node.key);
            node = node.next;
        }
        return differenceSet;
    }

    //subset check
    public boolean subset(DynamicSetDLL t){
        //return false if set1>set2
        if(this.setSize()>t.setSize())
            return false;
        //return false if any element in one set doesn't exist in other set
        Node node = this.head;
        while(node!=null){
            if(!t.isElement(node.key))
                return false;
            node = node.next;
        }
        //else return true
        return true;
    }

    //helper function
    //same as isElement but returns node instead of boolean
    public Node get(Item key){
        Node node = this.head;
        while(node!=null){
            if(node.key==key)
                return node;
            node = node.next;
        }
        return null;
    }

     public static void main(String[] args){
        /*DynamicSetDLL<Integer> dynamicSet = new DynamicSetDLL<>();
        dynamicSet.add(12);
        dynamicSet.add(14);
        dynamicSet.add(17);
        dynamicSet.add(11);
        dynamicSet.add(18);
        dynamicSet.remove(14);
        DynamicSetDLL<Integer> dynamicSet1 = new DynamicSetDLL<>();
        dynamicSet1.add(10);
        dynamicSet1.add(18);
        dynamicSet1.add(14);
        dynamicSet1.add(11);
        System.out.println("SET A: "+dynamicSet);
        System.out.println("SET B: "+dynamicSet1);
        System.out.println("Union: "+dynamicSet.union(dynamicSet1));
        System.out.println("Intersection: "+dynamicSet.intersection(dynamicSet1));
        System.out.println("Difference: "+dynamicSet.difference(dynamicSet1));
        System.out.println("Is subset: "+dynamicSet.subset(dynamicSet1));*/
    }

    @Override
    public String toString() {
        StringBuilder concatenate = new StringBuilder("[");
        Node node = this.head;
        while(node!=null){
            if(node.next==null)
                concatenate.append(node.key.toString());
            else
                concatenate.append(node.key.toString()).append(", ");
            node = node.next;
        }
        return concatenate.append("]").toString();
    }
}
