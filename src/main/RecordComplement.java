package main;

import helper.Validator;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

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
                System.out.println(products);
                return;
            }else{
                return;
            }
        }

    }

    /*> updateRecord*/

//    private String myTable(){}

    public static String insertRecord(Product paramProduct){
        Product product = new Product();
        passByValue(product,paramProduct);
        int orderNum;
        boolean loopStatus = true;
        boolean isUpdated = false;
         do{
             String InsertMenu[] ={"1.Update All","2.Name","3.Price","4.Qty","5.Exit"};
             Table tbl = new Table(5,BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);
             App.myTable(5, 10 ,InsertMenu,"tttttttttt");

             orderNum = Validator.readInt("Option :");
                switch (orderNum) {
                    case 1:
                        product = insertNewRecord();
                        product.setId(paramProduct.getId());
                        product.setImportedDate(paramProduct.getImportedDate());
                        isUpdated =true;
                        break;
                    case 2:
                        System.out.print("Name:"); String name = new Scanner(System.in).nextLine();
                        product.setName(name);
                        isUpdated = true;
                        break;
                    case 3:

                        product.setUnitPrice(Validator.readDouble("Price :"));
                        isUpdated = true;
                        break;
                    case 4:
                        product.setStockQty(Validator.readInt("QTY :"));
                        isUpdated = true;
                        break;
                    case 5:
                        loopStatus = false;
                        break;
                    default:
                        Complementary.tabler("Input Mistake");
                        break;
                }
             if(isUpdated==true) {
//                 Complementary.tabler("Updated Info : " + product.toString()) ;
                 String checkInfo[] = Complementary.subString(product.toString());
                 String PreviousInfo[] = Complementary.subString(paramProduct.toString());
                 String addStringTable[]= {
                         " ","Old","New",
                         "ID",PreviousInfo[0],checkInfo[0],
                         "Name",PreviousInfo[1],checkInfo[1],
                         "Price",PreviousInfo[2],checkInfo[2],
                         "Qty",PreviousInfo[3],checkInfo[3]
                         ,"Imorted Date",PreviousInfo[4],checkInfo[4]
                 };
                 App.myTable(3,19,"Chceck",addStringTable,"tttttttttt");

//                 Complementary.tabler("Previous Info : " + paramProduct.toString());
             }
        }while(loopStatus);

        if(isUpdated==false){
            return paramProduct.toString();
        }

        while (true){

            Complementary.tabler("press 'y' to update and 'n' to cancel");
            char c = new Scanner(System.in).next().charAt(0);
            if (c == 'y' || c == 'Y'){
                return product.toString();
            }else if(c == 'n' || c == 'N'){
                return paramProduct.toString();
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
