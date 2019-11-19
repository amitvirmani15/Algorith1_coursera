import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Set;

public class Find10Items {
    private final int[] items;
    public Find10Items(int[] arr){
items = arr;
    }

    public Integer[] ReturnItems(){
        var hashTable = new HashMap<Integer, Integer>();
        
        for (int i =0; i < items.length; i++){
            if(hashTable.containsKey(items[i]))
            {
                hashTable.replace(items[i], hashTable.get(items[i])+1);
            }
            else {
                hashTable.put(items[i], 1);
            }
            
        }
        var ref = items.length/10;
        ArrayList<Integer> intli = new ArrayList<Integer>();
        for(int key : hashTable.keySet()){

            if(hashTable.get(key)>ref)
            {
                intli.add(key);
            }
        }
        Integer[] ints = (Integer[]) intli.toArray();
        return ints;
    }
}
