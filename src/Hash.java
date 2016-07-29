import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by liu on 16-7-29.
 */
public class Hash {
    public static SortedMap<Long,String> severHashMap = new TreeMap<Long,String>();
    public static SortedMap<Long,String> clientHashMap = new TreeMap<Long,String>();
    public static SortedMap<Long,String> sevjingMap = new TreeMap<Long,String>();
    public static SortedMap<String, Long> map4 = new TreeMap<String, Long>();
    public static ArrayList<String> severip = new ArrayList<String>();
    public static ArrayList<String> clientip = new ArrayList<String>();
    public static final int max = Integer.MAX_VALUE >> 10;
    public static final int step = 100000;

    public void addList() {
        severip.add("192.168.1.1");
        severip.add("192.168.1.2");
        severip.add("192.168.1.3");
        severip.add("192.168.1.4");
        severip.add("192.168.1.5");
        severip.add("192.168.1.6");
        severip.add("192.168.1.7");
        clientip.add("1111111");
        clientip.add("1111112");
        clientip.add("1111113");
        clientip.add("1111114");
        clientip.add("1111116");
        clientip.add("1111115");
    }

    private long hash(String key) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();
        long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16)
                | ((long) (bKey[1] & 0xFF) << 8)
                | (long) (bKey[0] & 0xFF);
        return res & 0xffffffffL;
    }

    public  void severHash(ArrayList<String> severip) {
        Hash t = new Hash();
        for (int i = 0; i < severip.size(); i++) {
            long sevha = t.hash(severip.get(i)) % max;
            severHashMap.put(sevha,severip.get(i));
            if(sevha<max&sevha>0){
                long a=0;
                for(long j=sevha;j<max;){
                    sevjingMap.put(j,severip.get(i));
                    j=j+step;
                    a=j;
                }
                if(a>max){
                    long c=a-max;
                    sevjingMap.put(c,severip.get(i));
                    for(long b=c-step;b<sevha&b>0;){
                        sevjingMap.put(b,severip.get(i));
                    }
                }
//                for(long m=0;m<sevha-step;){
//                    sevjingMap.put(m,severip.get(i));
//                    m=m+step;
//                }
            }else{
                for(long m=0;m<sevha-step;){
                    sevjingMap.put(m,severip.get(i));
                    m=m+step;
                }
            }
        }
    }

    public void clientHash(ArrayList<String> clientip) {
        Hash t = new Hash();
        for (int i = 0; i < clientip.size(); i++) {
            long cliha = t.hash(severip.get(i)) % max;
            clientHashMap.put(cliha,clientip.get(i));

        }
    }
    public void excute(SortedMap<Long,String>sevjingMap,SortedMap<Long,String>clientHashMap){
        long a=0;
        long b=0;

            for(long k:clientHashMap.keySet()){

                for(long m:sevjingMap.keySet()){
                    a=k;
                    idf(a==m){
                        sevjingMap.get(a);
                        System.out.println(clientHashMap.get(k)+"对应的是"+sevjingMap.get(a));
                    }else{
                        a++;
                    }

                    }
                }
            }




    public static void main(String[] args) {
        Hash ha=new Hash();
        ha.addList();
        ha.severHash(severip);
        ha.clientHash(clientip);
        ha.excute(sevjingMap,clientHashMap);
        for(long i:severHashMap.keySet()){
            System.out.println(i+"服务"+severHashMap.get(i));
        }
        for(long i:clientHashMap.keySet()){
            System.out.println(i+"客户"+clientHashMap.get(i));
        }
        for(long i:sevjingMap.keySet()){
            System.out.println(i+"镜像"+sevjingMap.get(i)+ "--->"+max);
        }

    }
}