package main;

import helper.Validator;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 *<<<<< instance table
 *<<<<< move to table update UI
 *<<<<< arraylist pagination
 *<<<<< R-T-F-B-R-A-C
 *<<<<< R-C-MO
 */

public class Complementary extends Thread {
    static private ArrayList<String> arrayList;

    static {
        /*cmon table*/
    }


    public Complementary() {

    }

    @Override
    public void run() {

    }

    static void tabler(String... str) {//<<<< for adding String Array
        int number = str.length;
        Table tbl = new Table(number, BorderStyle.UNICODE_DOUBLE_BOX, ShownBorders.ALL);
        for (String aStr : str) {
            try {
                tbl.addCell(aStr);    //<<<<< add table to two
            } catch (NullPointerException in) {

                //System.out.println("problem");
                break;
            }
        }
        System.out.println(tbl.render());
    }

    static boolean searcher(String character, ArrayList<String> products, int recordAmount) {
        ArrayList arrayList = findObjectByCharacterInName(character, products);
        if (arrayList.size() > 0) {
            paginator(arrayList, recordAmount);
            return true;
        } else {
            return false;
        }
    }

    static ArrayList<String> findObjectByCharacterInName(String character, ArrayList<String> products) {

        arrayList = new ArrayList<>();

        products.forEach((value) -> {

            if (RecordComplement.stringHasChar(character, subString(value)[1])) {
                arrayList.add(value);
            }

        });


        return arrayList;
    }

    private static void paginator(ArrayList<String> products, int recordAmount) {

        int productSize = products.size() % 10;
        productSize = products.size() + 10 - (productSize == 0 ? 10 : productSize);//បង្កត់
        //>>>> end of file
//        System.out.println(productSize);
        int page = 0;
        boolean boo = false;
        while (true) {
            Complementary.tabler("1.previous||2.next||3.exit");
            switch (boo==false? 2: Validator.readInt("Option : ")  ) {
                case 1:
                    if (page * recordAmount <= recordAmount) {
                        tabler("First Page");
                        continue;
                    } else
                        page--;
                    break;
                case 2:
                    //page = page * recordAmount > productSize ? continue : page ;
                    if (page * recordAmount >= productSize) {
                        tabler("End of Page ");
                        continue;
                    } else {
                        page++;
                    }
                    break;
                case 3:
                    return;

                default:
                    System.out.println("Input mistake");
                    break;

            }
            boo = true;
            int j = 0;
            String showData[] = new String[recordAmount];
            for (int i = (recordAmount * page) - recordAmount; i < recordAmount * page; i++) {

                try {
                    showData[j] = products.get(i);
                    j++;
                } catch (IndexOutOfBoundsException e) {
                    break;
                }
            }
            App.myTable(15, showData);  //show search string result @Seakthong@Search Table
            System.out.println("Page : "+page+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tRecord : "+products.size() );
        }//while loop


    }


    //<<<<<<main call update or delete determine by boolean
    //<<<<<< UI improver
    static String updateObjectById(int number, ArrayList<String> products, boolean booFeature) {
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
                    String str[] = subString(productString);
                    String myString[] = {"ID", str[0], "Name", str[1], "Price", str[2], "Qty", str[3], "Imported Date", str[4]};
                    App.myTable(2, 20, "Detail", myString, "tttttttttt");
//                    tabler(productString);
                    String myString1[] = {"Successfully Deleted"};
                    App.myTable(1, 43, myString1, "tttttttttt");
                    try (BufferedWriter fileDelete = new BufferedWriter(new FileWriter("temp\\Delete.bak", true))) {
                        fileDelete.write(productString);
                        fileDelete.newLine();
                        fileDelete.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    String myString1[] = {"Delete Canceled"};
                    App.myTable(1, 43, myString1, "tttttttttt");
                }
            }

            return productString;
        }
    }

    private static Product convertFromStringToProduct(String[] str) {
        return new Product(Integer.parseInt(str[0]), str[1], Double.parseDouble(str[2]), Integer.parseInt(str[3]), str[4]);
    }

    private static int findObjectToUpdate(Integer id, ArrayList<String> products) {

        for (int i = 0; i < products.size(); i++) {
            String[] str = subString(products.get(i));
            if (Integer.parseInt(str[0]) == id) {
                String myString[] = {"ID", str[0], "Name", str[1], "Price", str[2], "Qty", str[3], "Imported Date", str[4]};
                App.myTable(2, 20, "Detail", myString, "tttttttttt");
                return i;
            }
        }

        tabler("not found");
        return -1;
    }

    static String[] subString(String str) {
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
