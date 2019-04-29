package main;

import java.util.Scanner;

public class Display {
    public static void main(String[] args) throws Exception {
        Display obj=new Display();
        Scanner scan=new Scanner(System.in);
        System.out.print("Input Charachter:");
        String chr= scan.nextLine();
        obj.help(chr);
        obj.exit();
    }

    private void help(String chr){
        System.out.println("+-----------------------------------------------------------------------------+");
        System.out.println("! 1.    press    * : Display all record of products                           !");
        System.out.println("! 2.    press    w : Add new products                                         !");
        System.out.println("!       press    w : #proname-unitprice-qty : sortcut for add new product     !");
        System.out.println("! 3.    press    r : read Content any content                                 !");
        System.out.println("!       press    r#proId :  sortcut for read product by Id                    !");
        System.out.println("! 4.    press    u : Update Data                                              !");
        System.out.println("! 5.    press    d : Delete Data                                              !");
        System.out.println("!       press    d#proId :  sortcut for read product by Id                    !");
        System.out.println("! 6.    press    f : Display First Page                                       !");
        System.out.println("! 7.    press    p : Display Previous Page                                    !");
        System.out.println("! 8.    press    n : Display Next Page                                        !");
        System.out.println("! 9.    press    l : Display Last Page                                        !");
        System.out.println("! 10.   press    s : Search product by name                                   !");
        System.out.println("! 11.   press    sa : Save record to file                                     !");
        System.out.println("! 12.   press    ba : Backup data                                             !");
        System.out.println("! 13.   press    re : Restore data                                            !");
        System.out.println("! 14.   press    h : Help                                                     !");
        System.out.println("+-----------------------------------------------------------------------------+");

    }
    private void exit(){
        System.exit(0);
    }
}
