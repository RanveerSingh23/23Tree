import java.util.ArrayList;
class Tree { // 
    Node root = null; 
    
    // THIS WILL BE OUR ROOT WHEN NOTHING IS IN OUR TREE
    
    public Tree() { 
    	// THIS WILL BE OUR DEFAULT CSTOR
        this.root = null;
    }

    public Node search(int value) { 
    	// TODO: Taylor said to break down into recursive node function since we only use root once in old function
        Node curr = this.root.findNodeRecursive(value);
        return curr;
    }

    public boolean insert(int value) { 
    	//THIS WILL INSERT A NODE INTO THE TREE
       
    	if (this.root == null) { 
        	// this is our base case
            this.root = new Node(value);
            return true;
        }
        Node temp = search(value); 
        // This will hold a node if our value is found in the tree
        if (temp == null || temp.data.contains(value)) 
        	// dup checking
            return false;
       
        //TODO: check if we can insert without splitting else we need to split. 
        			//Check if we need to split at root else split temp
        
        
        if (temp.data.size() < 2) { 
        	// We know we dont have a full node so we can insert right here and can skip splitting
            temp.insertData(value);
            return true;
        }           
        if (temp.data.size() >= 2){  
        	//TODO: Taylor said to leave room for extra data, insert it, then split
                temp.insertData(value); 
                if (temp == this.root) {
                	//Need to make a new root and set the current root as a child of new root
                    this.root = new Node(null);
                    this.root.insertChild(temp);
                    split(temp); 
                    return true;
                } else {
                split(temp);
                    return true;
                }
            }
            return false;
        }
  

    public int size() {
        return this.root.size();
    } // this will return the size of the tree from the root

    public int size(int value) {
        //if(this.root == null) //TODO: SEE IF WE NEED THIS SINCE WE ALREADY HAVE A CHECK IN FIND
        //  return 0;
        Node temp = search(value);
        if (!temp.data.contains(value)) return 0;
        // see if we dont have the have value, then return 0  if we dont 
        return temp.size(); 

    }
    
    
    public void split(Node curr){ 
        curr.parent.insertData(curr.data.get(1)); 
        // insert the middle data into the parent
        Node NodeToAttach = new Node(curr.data.get(2)); 
        // create a new node to attach to parent 
        curr.parent.insertChild(NodeToAttach);

        if (!curr.child.isEmpty()) { 
        	//check within children so we can shift data to newChild
        	NodeToAttach.insertChild(curr.child.get(2));
        	NodeToAttach.insertChild(curr.child.get(3));
        	for(int i = curr.data.size(); i > 1; i--) {
        		curr.child.remove(i); // remove the children from curr since we attached them already
            } 
        }
        for(int i = curr.data.size()-1; i >= 1; i--) {
            curr.data.remove(i);//cleaning the data
        } 
        //TODO:Taylor said for double splitting, check if we need to split again
        //2 cases -- we are at the root and not at the root

        if (curr.parent.data.size() > 2) { 
            if (curr.parent == this.root) { 
                this.root = new Node(null); // create a new root
                this.root.insertChild(curr.parent);
                split(curr.parent);
            } else {
                split(curr.parent);
            }
            
        }
    }

    public int get(int i) { 
    	//TODO: GET METHOD -- maybe instead of doing it here just do it recursively like size and insert
        return root.getSubTree(i);
        }
    
    public class Node {
        //private Integer[] data = new Integer[3];
        //private Node[] child= new Node[4];
        private ArrayList<Integer> data = new ArrayList<>(3);// this will now hold our dataL,dataR,tempData in which case split
        private ArrayList<Node> child = new ArrayList<>(4);// this will hold out left,mid,right,and tempChild
        private Node parent; 
        
        public Node(Integer newData) { // this will act as our 'default' cstor, and for split as well if we need to make a new root
            this.parent = null;
            if(newData == null) {
            	//Only time I will pass in Null is when im making a new root
                this.data = new ArrayList<>(3);
            	this.child = new ArrayList<>(4);
            }
            else {
            	this.data.add(newData);
            }
            
        }
        
        public Node findNodeRecursive(int value) { 
        	// TODO: Taylor said to break down into recursive node function
            if (this.data.contains(value) || this.child.isEmpty()) {
                return this;
            } 
            else {
                int pos = 0;
                while (pos < this.data.size() && value > this.data.get(pos)) {
                	pos++;
                }
                return child.get(pos).findNodeRecursive(value);
            }
        }
        
        public void insertChild(Node curr) { 
        	//helper function to get pos and insertion of child
        	curr.parent = this;
            int pos = 0;
            while (pos < this.child.size() && curr.data.get(0) > this.data.get(pos)) {
            	pos++;
            }
            this.child.add(pos, curr);
        }

        public void insertData(int value) { 
        	//helper to get pos of data and insert
            int pos = 0;
            while (pos < this.data.size() && value > this.data.get(pos)) {
            	pos++;
            }
            this.data.add(pos, value);
        }
      
       public int getSubTree(int value) { 
    	   //This will recursively find the size of the nodes' subtree
               int currPos = 0, nextPos = 0;
               if (!child.isEmpty()) { 
            	   // check to see if we have children
            	   currPos = this.child.get(0).size();
            	   nextPos = this.child.get(1).size();
               }
               int totalSize = currPos + nextPos;
               //base case
               if (currPos == value) {
            	   return this.data.get(0);
               } else if (totalSize + 1 == value) {
                   return this.data.get(1);
               }
               // case 2 -- we didnt find what were looking for -- recursive step
               if (value < currPos) {
                   return this.child.get(0).getSubTree(value);
               } else if (value >= totalSize + this.data.size() && this.child.size() == 3) {
                   return this.child.get(2).getSubTree(value - totalSize- this.data.size());
               } 
       		   else {
                   return this.child.get(1).getSubTree(value - currPos - 1);
               }

           }
       
       
        public int size() { 
        	// THIS WILL GIVE US OUR SUBTREE SIZE
            int counter = 0;
            for (Node i : this.child) {
                counter += i.size();
            }
            return counter + this.data.size();
        }

    }
}
