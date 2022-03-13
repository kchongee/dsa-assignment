package adtImplementation;

import adtInterfaces.ListInterface;

public class ArrayList<T> implements ListInterface<T> {    
    private T[] arr;    
    private int size;
    private static final int DEFAULT_CAPACITY=50;

    public ArrayList(){
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int inputCapacity) {
        arr = (T[]) new Object[inputCapacity];
        size=0;
    }

    @Override
    public int size() {        
        return this.size;
    }

    @Override
    public boolean add(T element) {        
        if (isArrayFull()){
            expandArray();
        }
        arr[size]=element;
        size++;
        return true;
    }

    // @Override
    // public boolean add(int newIndex, T newElement) {
    //     boolean isSuccessful = false;
    
    //     if ((newIndex >= 1) && (newIndex <= size + 1)) {
    //         addAGap(newIndex);
    //         arr[newIndex] = newElement;
    //         size++;
    //     } 
    
    //     return isSuccessful;
    // }

    @Override
    public boolean remove(int index) {        
        if ((index >= 1) && (index <= size)) {
            arr[index - 1]=null;

            if (index < size) {
                removeGap(index);
            }
            size--;
        }else{
            return false;
        }
        return true;
    }

    public boolean remove(T element) {
        for(int i=0;i<arr.length;i++){
            if(element.equals(arr[i])){
                remove(i);
                return true;
            }
        }
        return false;
    }  

    @Override
    public void clear(){
        this.size=0;
    }
    
    @Override
    public boolean contains(T element){
        boolean found=true;
        for(int i=0;i<this.size;i++){
            if (element.equals(arr[i])){
                found=true;
                break;
            } else{
                found=false;
            }
        }
        return found;
    }

    @Override
    public T get(int index) {        
        T returned = null;
        if((index>=1) && (index <= size)){
            returned = arr[index];
        }
        return returned;
    }

    public int get(T anEntry) {    
        if(!isEmpty()){
            for (int index = 0; index < size; index++) {
                if (anEntry.equals(arr[index])) {
                  return index;
                }
            }
        }
        return -1;                
      }

    @Override
    public boolean replace(int index, T newElement){        
        if ((index >= 1) && (index <= size)){
            arr[index]=newElement;
        } else{
            return false;
        }
        return true;
    }

    public boolean addAll(T[] elements) {
        boolean isSuccessful = true;
    
        if(arr.length < elements.length){
          expandArray();
        }
        System.arraycopy(elements, 0, arr, 0, elements.length);
        size = elements.length;
    
        return isSuccessful;
    }

    @Override
    public boolean isEmpty() {        
        return size==0;
    }
    
    private void expandArray() {
        T[] oldArray=arr;
        int oldsize=oldArray.length;
        arr = (T[]) new Object[2*oldsize];

        System.arraycopy(oldArray, 0, arr, 0, oldsize);
    }

    private boolean isArrayFull() {
        return this.size==arr.length;
    }

    private void removeGap(int index) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        //int removedIndex = index - 1;
        //int lastIndex = size - 1;
        for (int i = index; index<this.size; index++) {
          arr[i] = arr[i+1];
        }
    }    

    // private void addAGap(int index) {
    //     int newIndex = index;
    //     int lastIndex = size-1;
    
    //     for (int i = lastIndex; i >= newIndex; i--) {
    //         arr[index + 1] = arr[index];
    //     }
    // }

}