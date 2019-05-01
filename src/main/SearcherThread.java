package main;

import model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SearcherThread extends Thread{

    private static ArrayList searchResult;
    //<<<<indexStart indexEnd
    String regex;
    HashMap<Integer,Product> hashMap;//<<<<<?? change arraylist

    SearcherThread(String regex,HashMap hashMap){

        this.regex = regex;
        this.hashMap = hashMap;
    }

//    public void paginator(int number ){
//
//        while (true){
//            if(searchResult.size()>number){
//                break;
//            }
//            Thread.sleep(50);
//        }
//
//        ArrayList arrayList = new ArrayList();
//        for (int i = 0; i < number-10; i--) {//<<<<** index reverse config
//
//            arrayList.add(i,searchResult.get(i));//<<<<<** reverse store
//        }
//    }//<<<<<work required

    public void findObjectByCharacterInName(String character, HashMap<Integer, Product> hashMap){
        searchResult = new ArrayList();

        hashMap.forEach((key,value)->{//<<<changetofor

            if(RecordComplement.stringHasChar(character,value.getName())){
                searchResult.add(value);
            }

        });

        System.out.println("done");
        return ;
    }

    void getStart(){

        if(hashMap.size() > 0){
            findObjectByCharacterInName(this.regex,this.hashMap);
        }else {

        }

    }
    @Override
    public void run() {

        getStart();

    }
}
