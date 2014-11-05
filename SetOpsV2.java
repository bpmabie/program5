/**
 * This is my own work: Ben Mabie
 * This program uses linked nodes to demonstrate set operations 
 * October 20, 2014
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SetOpsV2{
    
    public static void main(String[] args) throws FileNotFoundException {
   
        /**
    * declare three set reference variables 
    */
        SetInterface<String> set1 = new LinkedSet<>(),
                set2 = new LinkedSet<>(),
                set3 = new LinkedSet<>();
        
        File file = new File("test1.in");                                        
        Scanner scan = new Scanner(file);
        String temp = new String();
        temp = "";
     /**
      * scanner to add to populate the variables from files
      */   
    
     while(scan.hasNext())                      
     {   
         temp = scan.next();
         set1.addItem(temp);
     } 
     
     file = new File("test2.in");
     scan = new Scanner(file);
     
     while(scan.hasNext())                      
     {   
         temp = scan.next();
         set2.addItem(temp);  
     }
     
     file = new File("test3.in");
     scan = new Scanner(file);
     
     while(scan.hasNext())                      
     {   
         temp = scan.next();
         set3.addItem(temp);  
     }
     
     scan.close();
     
     /**
      * output the sets, the union of the three sets, the intersection of the three
      * sets and the elements that are exactly in one set.
      */
     
     System.out.print("S1 = " + set1 +"\n");  
     System.out.print("S2 = " + set2 +"\n");     
     System.out.print("S3 = " + set3 +"\n");
     System.out.print("S1 union S2 union S3 = " + set1.union(set2.union(set3)) +"\n");
     System.out.print("S1 intersect S2 intersect S3 = " 
             + set1.intersection(set2.intersection(set3)) +"\n");
     System.out.print("Elements that are in exactly 1 set = "
     +set1.symmetricDifference(set2.symmetricDifference(set3)).difference(set1.intersection(set2.intersection(set3))));
        
        }
        

}
class LinkedSet<T> implements SetInterface<T>{
    
    private Node front;
    
    /**
     * toString
     * @return input formatted correctly
     */
    
    public String toString(){
       String str = "[";
       Node curr = front;
       boolean isFirst = true;
       
       while(curr != null){
           if(isFirst){
               str+= curr.data;
               curr = curr.next;
               isFirst = false;
           }
           else{
                str += ", " + curr.data;
                curr = curr.next;
           }
       }
       str+= "]";
       return str;
    }


public class Node{
    
    private T data;
    private Node next;
  
    public Node(T data){
        this.data = data;
    }
    
    public Node(T data, Node nextNode){
        this.data = data;
        this.next = nextNode;
    }
}   

/**
 * add
 * @param item
 * @return false if item already exists in list, true if successful 
 */
    @Override
    public boolean addItem(T item) {
             
        Node newNode = new Node(item);
        Node curr = front;
        
        if(this.contains(item)){
                return false;
            }
        
        newNode.next = front;
        front = newNode;
    
        return true;
    }
/**
 * 
 * @return size of node chain
 */
    @Override
    public int getSize() {
        
        Node curr = front.next;
        int count = 0;
        while(curr != null){
            curr = curr.next;
            count++;
        }
        return count;
    }

    @Override
    public boolean isFull() {
       return false;
    }

    @Override
    public boolean isEmpty() {
        return front.next == front;
    }
/**
 * removes nodes until empty
 */
    @Override
    public void clear() {
    while(!isEmpty()){
        remove();
    }
    }
    /**
     * checks to see if
     * @param item exists in node chain
     * @return true if so, false if not
     */
    @Override
    public boolean contains(T item) {
        boolean isFound = false;
        Node curr = front;
        
        while(!isFound && (curr != null)){
            if(item.equals(curr.data)){
                isFound = true;
        }
        else{
           curr = curr.next;     
        }
    }
        return isFound;
    }
 /**
  * checks to see if
  * @param item exists in node chain
  * @return node reference if true, nothing if false
  */
    private Node getReferenceTo(T item){
        boolean isFound = false;
        Node curr = front;
        
        while(!isFound && (curr != null)){
            if(item.equals(curr.data)){
                isFound = true;
            }
            else{
                curr = curr.next;
            }
        }
        return curr;
    }
  /**
   * 
   * @return front node = null
   */
    public T remove(){
        T result = null;
        if(front != null){
            result = front.data;
            front = front.next;
        }
        return result;
    }
/**
 * remove
 * @param item
 * @return true if successful
 */
    @Override
    public boolean remove(T item) {
       boolean result = false;
       Node nodeN = getReferenceTo(item);
       if(nodeN != null){
           nodeN.data = front.data;
           remove();
           result = true;
       }
       return result;
       
    }
    @Override
    public T[] toArray() {
        return null;
    }
    
    @Override
    public SetInterface<T> union(SetInterface<T> rhs) {
        SetInterface result = new LinkedSet<>();
        Node curr = front;
        
        while(curr != null){
            
            result.addItem(curr.data);
            curr = curr.next;
        
        }
        curr = ((LinkedSet<T>)rhs).front;
        
        while(curr != null){
            result.addItem(curr.data);
            curr = curr.next;
        }
        return result;
    }

    @Override
    public SetInterface<T> intersection(SetInterface<T> rhs) {
        SetInterface result = new LinkedSet<>();
        Node curr = front;
         
        while(curr != null){
            if(!rhs.contains(curr.data)){
                curr = curr.next;
            }
            else{
               result.addItem(curr.data);
               curr = curr.next;
            }
                
        }
        return result;
    }


    @Override
    public SetInterface<T> difference(SetInterface<T> rhs) {
        SetInterface result = new LinkedSet<>();
        Node curr = front;
        
        while(curr != null){
            if(!rhs.contains(curr.data)){
            result.addItem(curr.data);
            curr = curr.next;
            }
            else{
              curr = curr.next;  
            }
        }
        return result;
    }

    @Override
    public SetInterface<T> symmetricDifference(SetInterface<T> rhs) {
        SetInterface result = new LinkedSet<>(),
                firstSide = new LinkedSet<>(),
                secondSide = new LinkedSet<>();
        
            firstSide = this.difference(rhs);
            secondSide = rhs.difference(this);
            
            result = firstSide.union(secondSide);
            return result;
        
        
    }
    
}

interface SetInterface<T>{
    /**
     * add
     * @param item to bag
     * @return true if successful, false otherwise
     */
    public boolean addItem(T item);
    public int getSize();
    public boolean isFull();
    public boolean isEmpty();
    /**
     * remove all items from bag
     */
    public void clear();
    public boolean contains(T item);
    /**
     * remove
     * @param item
     * @return true if successful, false otherwise
     */
    public boolean remove(T item);
    /**
     * remove an item from the bag
     * @return reference to the item removed
     */
    public T[] toArray();
    
    SetInterface<T> union(SetInterface<T> rhs);
    SetInterface<T> intersection(SetInterface<T> rhs);
    SetInterface<T> difference(SetInterface<T> rhs);
    SetInterface<T> symmetricDifference(SetInterface<T> rhs);
}