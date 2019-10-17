package HashTable;

import java.util.TreeMap;

/**
 * hashtable如果想实现O(1)的实现复杂度就要实现动态的扩容和缩容
 * @param <K>
 * @param <V>
 */
public class HashTable<K,V> {
    private final int[] capacity //保证每次扩容的时候数组的容量都是素数
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    private static final int upperTol = 10;//最大发生冲突的个数
    private static final int lowerTol = 2;//最小发生冲突的个数
    private int capacityIndex = 0;//数组中的位置

    private TreeMap[] hashTable;
    private int M;
    private int size;

    public HashTable(int M){
        this.M = M;
        size = 0;
        hashTable = new TreeMap[M];
        for(int i=0;i<hashTable.length;i++){
            hashTable[i] = new TreeMap<>();
        }
    }
    public HashTable(){
        this(97);
    }

    private int hash(K key){
        return (key.hashCode()&0x7fffffff) % M;
    }
    public  int  getSize(){
        return size;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public void add(K key,V value){
        TreeMap<K,V> map = hashTable[hash(key)];
       if( map.containsKey(key)){
           map.put(key,value);
       }else {
           map.put(key,value);
           size++;
           if(size >= capacity[capacityIndex] * upperTol && capacityIndex + 1 < capacity.length ){
               resize(capacity[++capacityIndex]);
           }
       }
    }

    public V remove(K key){
        TreeMap<K,V> map = hashTable[hash(key)];
        V ret = null;
        if (map.containsKey(key)){
            ret = map.remove(key);
            size --;
            if(size < capacity[capacityIndex] * lowerTol && capacityIndex - 1 >= 0 ){
                resize(capacity[--capacityIndex]);
            }
        }
        return ret;
    }

    private void resize(int newM){
        TreeMap<K,V>[] newHashTable = new TreeMap[newM];
        for(int i = 0;i<newM;i++){
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        M = newM;
        for(int i=0;i<oldM;i++){//取出其中所有的元素,对所有的元素进行重新的hash
            TreeMap<K,V> map = hashTable[i];
            for(K key:map.keySet()){
                newHashTable[hash(key)].put(key,map.get(key));
            }
        }
        this.hashTable = newHashTable;
    }

    public void  set(K key,V value){
        TreeMap<K,V> map = hashTable[hash(key)];
        if (map.containsKey(key)){
            map.put(key,value);
        }else{
            throw new IllegalArgumentException("错误");
        }
    }

    public boolean contains(K key){
        TreeMap<K,V> map = hashTable[hash(key)];
        return map.containsKey(key);
    }

    public V get(K key){
        TreeMap<K,V> map = hashTable[hash(key)];
        return map.get(key);
    }
}
