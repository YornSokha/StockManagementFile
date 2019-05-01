package main;


import java.util.ArrayList;
import java.sql.*;

public class TestSearch extends Thread {

    private static ArrayList<String> arrayList = new ArrayList<>();
    public static void main(String[] args) {
        int i = 0;
        long startTimeMs = System.currentTimeMillis( );
        while(i<10_000_000){
            arrayList.add(i+"Hello Worold.");
            i++;
        }
        System.out.println(System.currentTimeMillis( ) - startTimeMs);
        startTimeMs = System.currentTimeMillis( );
        TestSearch.TestSearch('1',2);
        System.out.println(System.currentTimeMillis( ) - startTimeMs);
    }
//    private final Object Thread
    private static ArrayList<String> searchResult = new ArrayList<>();
    String regex;

    public static void TestSearch(char regex, int ThreadTotal){ // ThreadNum must be n*2;
        int ReadPerThread = arrayList.size() / ThreadTotal;
        for(int i=0; i< ThreadTotal; i++){
        new TestSearch(regex,ReadPerThread, i).start();
        }
        
    }

    private TestSearch(char regex, int ReadPerThread,  int ThreadNum){
        for(int i=ReadPerThread*ThreadNum; i<ReadPerThread*(ThreadNum+1); i++){
            if(arrayList.get(i).charAt(0) == regex){
                searchResult.add(arrayList.get(i));
            }
        }
    }

    private void getStart(){
    }
    @Override
    public void run() {
        getStart();
    }



    public static String[] subString(String str) {
        int firstIndex = str.indexOf('|');
        String s1 = str.substring(0, firstIndex);

        int second = str.indexOf("|", firstIndex + 1);
        String s2 = str.substring(firstIndex + 1, second);

        int third = str.indexOf("|", second + 1);
        String s3 = str.substring(second + 1, third);

        int fourth = str.indexOf("|", third + 1);
        String s4 = str.substring(third + 1, fourth);

        String s5 = str.substring(fourth + 1);

        return new String[]{s1, s2, s3, s4, s5};
    }

}

//Class.forName("me");