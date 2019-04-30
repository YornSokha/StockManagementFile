package main;

import controller.Connection;
import helper.Validator;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class App {
    private static final String FILE_NAME = "product.txt";
    public static ArrayList<String> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int numOfRows = 5;
    private static int currentPage = 1;
    private static Table table;

    public static void main(String[] args) {
        myGroupname();
        generateData();
        getData();

        do{
            String key = printMenu();
            switch (key) {
                case "*":
                    gotoPage(currentPage);
                    break;
                case "w":
                    writeData();
                    break;
                case "r":
                    readData();
                    break;
                case "u":
                    //RecordComplement.updateObjectById(10, products);
                    break;
                case "d": /*@Delete*/
                    //System.out.println("Delete");
                    RecordComplement.deleteRecordById(Validator.readInt("Enter Number: ", 0, products.size() - 1), products);

                    break;
                case "f":
                    goFirst();
                    break;
                case "p":
                    goPrevious();
                    break;
                case "n":
                    goNext();
                    break;
                case "l":
                    goLast();
                    break;
                case "s":
                    System.out.println("search");
                    break;
                case "g":
                    gotoPage(Validator.readInt("Input page number(1-" + getTotalPage() + ") : ", 1, getTotalPage()));
                    break;
                case "se":
                    setRow();
                    break;
                case "ba":
                    backup();
                    break;
                case "sa":
                    System.out.println("Save");
                    break;
                case "re":
                    reStore();
                    break;
                case "h":
                    help();
                    break;
                case "e":
                    System.exit(0);
                    break;

                /*@Seakthong*/
            }
        } while (true);
    }

    private static void help() {
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


    private static String printMenu() {
        BorderStyle borderStyle = new BorderStyle("╔═", "═", "═╤═", "═╗", "╟─", "─", "─┼─", "─╢", "╚═", "═", "═╧═", "═╝", "║ ", " │ ", " ║", "─┴─", "─┬─");
        Table tableMenu = new Table(9, borderStyle, new ShownBorders("......tttt"));
        String[] menu = {"*)Display", "W)rite", "R)ead",
                "U)pdate", "D)elete", "F)irst", "P)revious",
                "N)ext", "L)ast", "S)earch", "G)oto", "Se)t",
                "Sa)ve", "Ba)ck up", "Re)store", "H)elp", "E)xit"};
        for (int i = 0; i < 9; i++) {
            tableMenu.setColumnWidth(i, 11, 11);
        }
        for (String s : menu) {
//            tableMenu.addCell(s);
            tableMenu.addCell(s);
        }
        System.out.println(tableMenu.render());
        System.out.print("Command-->");
//        String str =scanner.nextLine().toLowerCase();
        String str = scanner.nextLine();
        if(str.charAt(0)=='.'){
            if(str.toLowerCase().charAt(1)=='u'){
                if(str.charAt(2)=='1'){
                    //.U1:Name:Unit:Price
                    String []mystr = str.split("/",10);
//                    System.out.println("1"+mystr[1]);
//                    System.out.println("2"+mystr[2]);
                }
                else if(str.charAt(2)=='2'){
                    System.out.println(2);
                }
                else if(str.charAt(2)=='3'){
                    System.out.println(3);
                }
                else if(str.charAt(2)=='4'){
                    System.out.println(4);
                }

            }
            else if(str.toLowerCase().charAt(1)=='g'){
                int num = 0;
                for(int i = 0 ; i < str.length(); i++){
                    if(i>1) num = num * 10 + Integer.parseInt(String.valueOf(str.charAt(i)));
                }
                gotoPage(num);
            }

        }
        return str.toLowerCase();
    }

    private static void initTable() {
        BorderStyle borderStyle = new BorderStyle("╔═", "═", "═╤═", "═╗", "╟─", "─", "─┼─", "─╢", "╚═", "═", "═╧═", "═╝", "║ ", " │ ", " ║", "─┴─", "─┬─");
        table = new Table(5, borderStyle, new ShownBorders("tttttttttt"));
        int myMinWidth[] = {14, 32, 11, 12, 18};
        for (int i = 0; i < 5; i++) {
            table.setColumnWidth(i, myMinWidth[i], 27);
        }
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Qty");
        table.addCell("Unit Price");
        table.addCell("Date");
    }

    private static int selectChoice() {
        System.out.println("1. Set row");
        System.out.println("2. First");
        System.out.println("3. previous");
        System.out.println("4. Next");
        System.out.println("5. Last");
        System.out.println("6. Goto");
        System.out.println("7. Display");
        int choice = Validator.readInt("Please select a choice :");
        return choice;
    }

    private static void getData() {
        long startTime = System.nanoTime();
        products = new ArrayList<>();
        Connection.getProducts(products);
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " seconds");
    }

    private static void readData() {
        int id = Validator.readInt("Read by ID :");
        for (String product : products) {
            String[] idPro = product.split("\\|");
            if (id == Integer.parseInt(idPro[0])) {
                String shown[]={"ID",idPro[0], "Name",idPro[1],"Price",idPro[2],"Imported Date",idPro[3]};
                App.myTable(2,shown,"tttttttttt");
//                Table tableReadData = new Table(2);
//                tableReadData.addCell("ID");
//                tableReadData.addCell(idPro[0]);
//                tableReadData.addCell("Name");
//                tableReadData.addCell(idPro[1]);
//                tableReadData.addCell("Price");
//                tableReadData.addCell(idPro[2]);
//                tableReadData.addCell("Imported Date");
//                tableReadData.addCell(idPro[3]);
//                System.out.println(tableReadData.render());
            }
        }
    }

    private static void generateData() {
//        new Thread(() -> {
//            String message = "Please wait....";
//            int i = 0;
//            while (i < message.length()) {
//                System.out.print(message.charAt(i++));
//                try {
//                    Thread.sleep(350);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        long startTime = System.nanoTime();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            int flush = 0;
            for (int i = 1; i <= 1_00; i++) {
                Product product = new Product(i, "Angkor Beer", 10d, 1000, getDate());
                bufferedWriter.write(product.toString());
                bufferedWriter.newLine();
                if (i == 5000 + flush) {
                    flush += 5000;
                    bufferedWriter.flush();
                }
            }
            System.out.println("success");
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");

    }

    private static void setRow() {
        System.out.print("Number of row : ");
        numOfRows = scanner.nextInt();
        if (currentPage > getTotalPage())
            currentPage = 1;
        scanner.nextLine();
    }

    private static void goNext() {
        if (currentPage != getTotalPage())
            gotoPage(++currentPage);
        else gotoPage(getTotalPage());
    }

    private static void goPrevious() {
        if (currentPage != 1)
            gotoPage(--currentPage);
        else
            gotoPage(1);
    }

    private static void gotoPage(int pageNum) {
        initTable();
        currentPage = pageNum;
        int start = numOfRows * (currentPage - 1);
        if (pageNum == getTotalPage()) {
            goLast();
        } else {
            for (int i = start; i < start + numOfRows; i++) {
                addRowTable(products.get(i));
            }
        }

        System.out.println(table.render());
        printPageSummary();
    }

    private static int getTotalPage() {
        return products.size() % numOfRows == 0 ? products.size() / numOfRows : products.size() / numOfRows + 1;
    }

    private static void goFirst() {
        currentPage = 1;
        initTable();
        for (int i = 0; i < numOfRows; i++) {
            addRowTable(products.get(i));
        }
        System.out.println(table.render());
        printPageSummary();
    }

    private static void printPageSummary() {
//        System.out.print("Page : " + currentPage + " of " + getTotalPage() + "\t\t\t\t\t\t\tTotal record : " + products.size());
        System.out.printf("%4sPage : %d of %d %64s Total Record: %d", " ", currentPage, getTotalPage(), " ", products.size());
        System.out.println();
    }

    private static int remainRowInLastPage(){
        return products.size()%numOfRows;
    }

    private static void goLast() {
        initTable();
        currentPage = getTotalPage();
        int start = numOfRows * (currentPage - 1);
        for (int i = start  ; i < products.size(); i++) {
            addRowTable(products.get(i));
        }
        System.out.println(table.render());
        printPageSummary();
    }

    private static void addRowTable(String product) {
        String[] p = product.split("\\|");
        for(int i = 0; i < 5; i++)
            table.addCell(p[i]);
    }

    private static void saveUpdate() {
        long startTime = System.nanoTime();
        FileWriter fileWriter = null;
        int bufferSize = 8 * 1024;
        try {
            fileWriter = new FileWriter("Product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, bufferSize);
        System.out.println(products.size());
        for (int i = 1; i <= products.size(); i++) {
            try {
                bufferedWriter.write(products.get(i).toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }

    private static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
//        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        return dateFormat.format(date);
    }

    private static void writeData() {
        String[] lastProduct = products.get(products.size() - 1).split("\\|");
        int lastId = Integer.parseInt(lastProduct[0]);
        System.out.println("Product ID : " + (lastId + 1));
        System.out.print("Product's Name : ");
        String name = scanner.nextLine();
        double price = Validator.readDouble("Product's Price : ");
        int qty = Validator.readInt("Product's Qty : ", 1, 1_000_000);
        String shown[]={"ID","" + (lastId + 1), "Name",name,"Price","" + price,"Imported Date",getDate()};
        App.myTable(2,shown,"tttttttttt");
        /*Table tableWriteData = new Table(2);
        tableWriteData.addCell("ID");
        tableWriteData.addCell("" + (lastId + 1));
        tableWriteData.addCell("Name");
        tableWriteData.addCell(name);
        tableWriteData.addCell("Price");
        tableWriteData.addCell("" + price);
        tableWriteData.addCell("Imported Date");
        tableWriteData.addCell(getDate());
        System.out.println(tableWriteData.render());*/
        char answer;
        System.out.print("Are you sure to add record? [Y/y] or [N/n]:");
        answer = Character.toLowerCase(scanner.next().charAt(0));
        if (answer == 'y')
            products.add("" + (lastId + 1) + "|" + name + "|" + price + "|" + qty + "|" + getDate());
        scanner.nextLine();

    }

    static void backup() {
        long start = System.nanoTime();
        try (BufferedWriter backup = new BufferedWriter(new FileWriter("backup\\" + (new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date())) + ".bac"))) {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME));
            String thisLine;
            int flush = 5000;
            int i = 0;
            //copy the file content in bytes
            while ((thisLine = bufferedReader.readLine()) != null) {
                i++;
                backup.write(thisLine);
                backup.newLine();
                if (i == flush) {
                    flush += 5000;
                    backup.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - start;
        System.out.print("Backup successfully " + (double) time / 1000000 + " milliseconds");
    }

    static void reStore() {
        File[] listOfFiles;
        listOfFiles = (new File("backup")).listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println((i + 1) + ") " + listOfFiles[i].getName());
            }
        }
        int index = Validator.readInt("Enter you choice :", 1, listOfFiles.length);
        try (BufferedWriter restoreTo = new BufferedWriter(new FileWriter(FILE_NAME))) {
            BufferedReader bacRead = new BufferedReader(new FileReader("backup\\" + listOfFiles[index - 1].getName()));
            String thisLine = "";
            int flush = 5000;
            int i = 0;
            //copy the file content in bytes
            while ((thisLine = bacRead.readLine()) != null) {
                i++;
                restoreTo.write(thisLine);
                restoreTo.newLine();
                ;
                if (i == flush) {
                    flush += 5000;
                    restoreTo.flush();
                }
            }
            System.out.println("Restore success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        getData();

    }

    public static void myTable(int colNum, String[] Value, String shown){
        BorderStyle borderStyle = new BorderStyle("╔═", "═", "═╤═", "═╗", "╟─", "─", "─┼─", "─╢", "╚═", "═", "═╧═", "═╝", "║ ", " │ ", " ║", "─┴─", "─┬─");
        Table tbl = new Table(colNum, borderStyle,new ShownBorders(shown));
        for(int i = 0; i<Value.length; i++){
            tbl.addCell(Value[i]);
        }
        System.out.println(tbl.render());
    }

    static void myGroupname(){
        System.out.println
        (
                "\n" +

                        ".....................................................................................................................................\n" +
                        ".....................................................................................................................................\n" +
                        "...______.........._........_________..._________........_........____....____...______.........._........____.._____.....______.....\n" +
                        "..|_..._.\\......../.\\......|.._..._..|.|.._..._..|....../.\\......|_...\\../..._|.|_..._.\\......../.\\......|_...\\|_..._|...'.___..|....\n" +
                        "....|.|_).|....../._.\\.....|_/.|.|.\\_|.|_/.|.|.\\_|...../._.\\.......|...\\/...|.....|.|_).|....../._.\\.......|...\\.|.|.../..'...\\_|....\n" +
                        "....|..__'....../.___.\\........|.|.........|.|......../.___.\\......|.|\\../|.|.....|..__'....../.___.\\......|.|\\.\\|.|...|.|...____....\n" +
                        "..._|.|__).|.._/./...\\.\\_....._|.|_......._|.|_....._/./...\\.\\_..._|.|_\\/_|.|_..._|.|__).|.._/./...\\.\\_..._|.|_\\...|_..\\.`.___]..|...\n" +
                        "..|_______/..|____|.|____|...|_____|.....|_____|...|____|.|____|.|_____||_____|.|_______/..|____|.|____|.|_____|\\____|..`._____.'....\n" +
                        ".....................................................................................................................................\n" +
                        "...................................______..................................................._...._...................................\n" +
                        "..................................'.___..|.................................................|.|..|.|..................................\n" +
                        "................................/..'...\\_|..._..--......--.....__..._...._..--.....______..|.|__|.|_.................................\n" +
                        "................................|.|...____..[.`/'`\\]./..'`\\.\\.[..|.|.|..[.'/'`\\.\\.|______|.|____..._|................................\n" +
                        "................................\\.`.___]..|..|.|.....|.\\__..|..|.\\_/.|,..|.\\__/.|.............._|.|_.................................\n" +
                        ".................................`._____.'..[___].....'.__.'...'.__.'_/..|.;.__/..............|_____|................................\n" +
                        "........................................................................[__|.........................................................\n" +
                        ".....................................................................................................................................\n" +
                        ".....................................................................................................................................\n"

        );
    }
}
