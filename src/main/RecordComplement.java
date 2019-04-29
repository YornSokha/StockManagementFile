package main;

import helper.Validator;
import model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;
// have to default constructor in Test
public class RecordComplement {

    private static ArrayList searchResult;

    /*important and usable method
    * findObjectByCharacterInName(String character,HashMap<Integer,Product> products)//<<<< move to searcherThread
    * Boolean updateObjectById(int number,ArrayList<Product products)
    * void deleteRecordById(int number , ArrayList<Product products)<<should be change to boolean
    * filterListWithPagination(int page,int recordAmountInlist)
    * */

    public static ArrayList filterListWithPagination(int page,int recordAmountInlist){
        if(searchResult == null ){
            System.out.println("has no data");
            return null;
        }else{
            ArrayList arrayList = new ArrayList();
            int startIndex = (recordAmountInlist * 3) - 1;
            int endIndex = (startIndex + recordAmountInlist ) - 1;

            for (int index = startIndex; index < endIndex; index++) {
                if (searchResult.get(index)==null){
                    break;
                }
                arrayList.add(searchResult.get(index));
            }

            return arrayList;
        }
    }

    public static ArrayList findObjectByCharacterInName(String character,HashMap<Integer,Product> products){

        ArrayList arrayList = new ArrayList();

        products.forEach((key,value)->{

            if(stringHasChar(character,value.getName())){
                arrayList.add(value);
            }

        });


        return arrayList;
    }

    /* >findObject*/
    public static Boolean stringHasChar(String regex, String text){
        return Pattern.matches(".*"+regex+".*",text);
    }


    public static Boolean updateObjectById(int number,ArrayList<Product> products){//<<<reference to products ??
        if(products.get(number) == null ){
            System.out.println("Data not found");
            return false;
        }else{
            products.add(insertRecord(products.get(number)));
            return true;
        }
    }

    public static void deleteRecordById(int number , ArrayList<String> products){
        char c;
        if(products.get(number)== null){
            System.out.println("Data not Found");
            return;
        }else {
            System.out.println(products.get(number).toString());
            System.out.println("Are you sure ");
            System.out.println("press 'y' to delete");
            c = new Scanner(System.in).next().charAt(0);
            if( c =='y'){
                products.remove(number);
                System.out.println("successfully deleted");
                return;
            }else{
                return;
            }
        }

    }

    /*> updateRecord*/
    private static Product insertRecord(Product paramProduct){
        Product product = new Product();
        passByValue(product,paramProduct);
        int orderNum;
        boolean loopStatus = true;
        System.out.println("1./Update All||2./Name||3./Price||n4./Qty||5./Exit");
        do{
            orderNum = Validator.readInt("Option :");
                switch (orderNum) {
                    case 1:
                        product = insertNewRecord();
                        product.setId(paramProduct.getId());
                        product.setImportedDate(paramProduct.getImportedDate());
                        break;
                    case 2:
                        System.out.print("Name:"); String name = new Scanner(System.in).nextLine();
                        product.setName(name);
                        break;
                    case 3:

                        product.setUnitPrice(Validator.readDouble("Price :"));
                        break;
                    case 4:
                        product.setStockQty(Validator.readInt("QTY :"));
                        break;
                    case 5:
                        loopStatus = false;
                        break;
                    default:
                        System.out.println("input mistake");
                        break;
                }

        }while(loopStatus);

            System.out.println(product);//??<<<
            System.out.println(paramProduct);//??<<<

        while (true){
            System.out.println("press 'y' to update and 'n' to cancel");
            char c = new Scanner(System.in).next().charAt(0);
            if (c == 'y' ){
                return product;
            }else if(c == 'n'){
                return paramProduct;
            }else{
                continue;
            }
        }
    }

    private static Product insertNewRecord(){
        System.out.print("Name:"); String name = new Scanner(System.in).nextLine();
        double price = Validator.readDouble("Price : ");
        int qty = Validator.readInt("Qty :");

        return new Product(0,name,price,qty,null);
    }

    public static void main(String[] args) {
        System.out.println(insertNewRecord());
    }

    private static void passByValue(Product product1, Product product2){
        product1.setId(product2.getId());
        product1.setImportedDate(product2.getImportedDate());
        product1.setName(product2.getName());
        product1.setStockQty(product2.getStockQty());
        product1.setUnitPrice(product2.getUnitPrice());
    }

}
