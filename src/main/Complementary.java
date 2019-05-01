package main;

import helper.Validator;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/*
 *<<<<< instance table
 *<<<<< move to table update UI
 *<<<<< arraylist pagination
 *<<<<< R-T-F-B-R-A-C
 *<<<<< R-C-MO
 */

public class Complementary extends Thread {
    static private ArrayList arrayList;
    private Integer index;
    public static Table table;

    static {
        /*cmon table*/
    }

    public Complementary(Integer index, ArrayList arrayList) {
        this.index = index;
        this.arrayList = arrayList;
    }

    public Complementary() {

    }

    @Override
    public void run() {

    }

    public static void tabler(String ...str){//<<<< for adding String Array
        int number = str.length;
        Table tbl = new Table(number, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);
        for(int i = 0; i<number; i++){

            try {
                tbl.addCell(str[i]);    //<<<<< add table to two
            }catch (NullPointerException in){

                System.out.println("problem");
                break;
            }
        }
        System.out.println(tbl.render());
    }
    ///<<< first index for array & sec for adding many string as varage
    public static String[] combineArray(String []str,String ...str2 ){
        int number = str.length+str2.length;
        int i=0;
        String []stringResult = new String()[number];
        for (String st:str2) {
            stringResult[i] = st;
            i++;
        }
        for (String st:str) {
            stringResult[i] = st;
            i++;
        }
        return stringResult;
    }

    public static boolean searcher(String character, ArrayList<String> products){
        ArrayList arrayList = findObjectByCharacterInName(character,products);
        if (arrayList.size()>0){
            paginator(arrayList,10);
            return true;
        }else{
            return false;
        }
    }

    public static ArrayList findObjectByCharacterInName(String character, ArrayList<String> products) {

        arrayList = new ArrayList();

        products.forEach((value) -> {

            if (RecordComplement.stringHasChar(character, subString(value)[1])) {
                arrayList.add(value);
            }

        });


        return arrayList;
    }

    private static void paginator(ArrayList<String> products, int recordAmount){

        recordAmount = 5;
        int productSize = products.size()%10;
        productSize = products.size() + 10 - (productSize==0? 10 : productSize);//បង្កត់
        //>>>> end of file
//        System.out.println(productSize);

        if(recordAmount < 5 ){

        }else{
            int page = 0;
            System.out.println("1.previous/2.next/3.exit");

            while (true){
                switch (Validator.readInt("Option")){
                    case 1:
                        if(page*recordAmount <= recordAmount){
                            System.out.println("current");
                            continue;
                        }

                        else
                            page--;
                        break;
                    case 2:
                        //page = page * recordAmount > productSize ? continue : page ;
                        if(page*recordAmount >= productSize){
                            System.out.println("end of page ");
                            continue;
                        }else {
                            page++;
                        }
                        break;
                    case 3:
                        return;

                    default:
                        System.out.println("Input mistake");
                        break;

                }
                for(int i = (recordAmount*page ) - recordAmount ;i < recordAmount*page;i++){
                    try {
                        System.out.println(products.get(i));
                    }catch (IndexOutOfBoundsException e){
                        break;
                    }
                }
            }//while loop


        }

    }

    //<<<<<<main call update or delete determine by boolean
    //<<<<<< UI improver
    public static String updateObjectById(int number, ArrayList<String> products, boolean booFeature) {
        int index = findObjectToUpdate(number, products);
        String productString = null;
        if (index == -1) {

            return null;

        } else {

            if (booFeature) {//update record

                Product product = convertFromStringToProduct(subString(products.get(index)));
                productString = RecordComplement.insertRecord(product);
                products.set(index, productString);

            } else {//delete record

                if (Validator.readYesNo("press 'y' to delete and 'n' to cancel : ") == 'y') {
                    productString = products.remove(index);
                    System.out.println(productString);
                    System.out.println("successfully deleted");//<<<<< move to table
                }

            }
            return productString;
            //>>>>> data found
        }

    }

    private static Product convertFromStringToProduct(String[] str) {
        return new Product(Integer.parseInt(str[0]), str[1], Double.parseDouble(str[2]), Integer.parseInt(str[3]), str[4]);
    }

    private static int findObjectToUpdate(Integer id, ArrayList<String> products) {

        for (int i = 0; i < products.size(); i++) {
            String[] str = subString(products.get(i));
//            System.out.println(str[0]);
            if (Integer.parseInt(str[0]) == id) {

                Table tableReadData = new Table(2);
                tableReadData.addCell("ID");
                tableReadData.addCell(str[0]);
                tableReadData.addCell("Name");
                tableReadData.addCell(str[1]);
                tableReadData.addCell("Price");
                tableReadData.addCell(str[2]);
                tableReadData.addCell("Imported Date");
                tableReadData.addCell(str[3]);
                System.out.println(tableReadData.render());
                return i;
            }
        }

        System.out.println("not found");//<<<<< move to table
        return -1;
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

    public static String subFirstString(String str) {
        int firstIndex = str.indexOf('|');
        String s1 = str.substring(0, firstIndex);
        return s1;
    }

}
