package BitMap;

/**
 * 位图数据结构,用来存储大数据,使用上可以减小存储空间,不同的bitmap中方便进行交集和并集
 * @author carson
 * @date 2019-07-19
 */
public class BitMap {
    //每个word对应一个64位的二进制,实际这个就是用来存储的bitmap
    private Long[] words;
    //bitmap实际的位数大小 . 不是被64位限制住的
    private int size;

    public Long[] getWords() {
        return words;
    }

    public void setWords(Long[] words) {
        this.words = words;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    //构造函数
    public BitMap(int size){
        this.size = size;
        //初始化的时候如果size恰好是64的倍数-1+1保证bitmap的位够用
        words = new Long[getWordIndex(size-1)+1];
        for(int i = 0;i<words.length;i++){
           words[i] = 0L;
        }
    }

    //定位bitmap某一位对应的word 实际就是把传入的index除以64
    private int getWordIndex(int bitIndex){
        //右移6位,相当于除以64
        return bitIndex>>6;
    }

    /**
     * 获取位图的第bitIndex位
     * @param bitIndex 位图的第bitIndex位(bitIndex = 0代表bitmap左数第一位)
     * @return 返回值是一个boolean 占用一个字节
     */
    public boolean getBit(int bitIndex){

        if(bitIndex < 0 || bitIndex>size-1){
            throw new IndexOutOfBoundsException("输入超过bitmap的范围");
        }

        int wordIndex = getWordIndex(bitIndex);
        System.out.println("wordIndex的数值是:"+wordIndex);
        System.out.println(Long.toBinaryString(words[wordIndex]));
        int remainder = bitIndex&(63);
        System.out.println("remainder"+" "+remainder);
        Long res = (words[wordIndex]&(1L << (63-bitIndex)));
        System.out.println("res"+ res);
        return res!=0;
    }

    public void setBit(int bitIndex){
        if (bitIndex<0||bitIndex>size-1){
            throw  new IndexOutOfBoundsException("输入超过bitmap的范围");
        }
        int wordIndex = getWordIndex(bitIndex);//这里不用加1,因为下标是从0开始的
        int remainder = bitIndex&(63);//余数,使用的公式是a%b = a&(b-1) b必须是2的整数幂
        words[wordIndex] |= (1L <<(63-bitIndex));
    }

    public static void main(String[] args) {
        BitMap bitMap = new BitMap(129);
        System.out.println(bitMap.getWords().length);
        System.out.println("没有set之前"+bitMap.getBit(127));
        bitMap.setBit(127);
        System.out.println("set之后"+bitMap.getBit(127));

    }
}
