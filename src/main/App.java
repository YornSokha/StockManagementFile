package main;

import com.sun.rowset.internal.Row;
import controller.Connection;
import helper.Validator;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;
import model.Product;
import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class App<publlic> {
    private static final String FILE_NAME = "product.txt";
    public static ArrayList<String> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int numOfRows = 5;
    private static int currentPage = 1;
    private static Table table;

    public static void main(String[] args) throws InterruptedException {
        myGroupname();
//        generateData();
        saveOption();
        getData();

        do {
            String key = printMenu();
//            key.toLowerCase();
            switch (key.toLowerCase()) {
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
                    update();
                    break;
                case "d": /*@Delete*/
                    delete();
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
                    System.out.print("Name :");
                    if (Complementary.searcher(scanner.nextLine(), products, 10) == true) {

                    } else {
                        Complementary.tabler("Data Not Found");
                    }
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
                    saveUpdate();
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

    private static void usingSpecialExpression(String str) {
        int num;
        if (str.toLowerCase().charAt(0) == '#') {
            switch (str.toLowerCase().charAt(1)){
                //read write delete search
                case 'g':
                    /* #g100*/ //go to page 100
                    num = 0;
                    for (int i = 0; i < str.length(); i++) {
                        if (i > 1) num = num * 10 + Integer.parseInt(String.valueOf(str.charAt(i)));
                    }
                    gotoPage(num);
                    break;
                case 'd':
                    /* #d100 */  //Delete id 100
                    num = 0;
                    for (int i = 0; i < str.length(); i++) {
                        if (i > 1) num = num * 10 + Integer.parseInt(String.valueOf(str.charAt(i)));
                    }
                    delete(num);
                    break;
                case 'u':
                    /* #u100*/
                    num = 0;
                    for (int i = 0; i < str.length(); i++) {
                        if (i > 1) num = num * 10 + Integer.parseInt(String.valueOf(str.charAt(i)));
                    }
                    Complementary.updateObjectById(num, products, true);

                    break;
                case 'w':
                    /* #w/Items/100.5/10 */ //write data name: Items /price: 100.5 /qty: 10
                    String [] myString = subStringWrite(str);
                    /*if(myString[0] == "Wrong") return;
                    try {
                        writeData(myString[1], Double.valueOf(myString[2]), Integer.valueOf(myString[3]));
                    }
                    catch (NumberFormatException e){
                        System.err.println("Syntax: #w/ProductName is Text/Price is Number/Qty is Number");
                    }*/
                    break;
                case 's': break;
                case 'r':
                    /* #r100 */ //read data id 100
                    num = 0;
                    for (int i = 0; i < str.length(); i++) {
                        if (i > 1) num = num * 10 + Integer.parseInt(String.valueOf(str.charAt(i)));
                    }
                    readData(num);
                    break;
            }
        }
    }

    public static String[] subStringWrite(String str) {
        try {
            int firstIndex = str.indexOf('/');
            String s0 = str.substring(0, firstIndex);

            int second = str.indexOf("/", firstIndex + 1);
            String s1 = str.substring(firstIndex + 1, second);

            int third = str.indexOf("/", second + 1);
            String s2 = str.substring(second + 1, third);

            String s3 = str.substring(third + 1);
            return new String[]{s0, s1, s2, s3};
        } catch (IndexOutOfBoundsException e) {
            return new String[]{"Wrong"};
        }
    }

    private static void update() {
        String product = Complementary.updateObjectById(Validator.readInt("Input ID : "), products, true);
        if (product != null)
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp\\Update.txt"))) {
                bufferedWriter.write(product);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
            }
    }

    private static void delete() {
        String product = Complementary.updateObjectById(Validator.readInt("Input ID : "), products, false);
        if (product != null)
            try {
                reCalculateCurrentPage();
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("temp\\Delete.txt"));
                bufferedWriter.write(product);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
            }
    }

    private static void delete(int id){
        String product = Complementary.updateObjectById(id, products, false);
        if(product != null)
            reCalculateCurrentPage();
    }

    private static void reCalculateCurrentPage() {
        if (currentPage > getTotalPage())
            currentPage = 1;
    }

    private static void help() {
        String Help[] = {

                "1.    press    * : Display all record of products",
                "2.    press    w : Add new products",
                "      press    w : #proname-unitprice-qty : sortcut for add new product",
                "3.    press    r : read Content any content",
                "      press    r#proId :  sortcut for read product by Id",
                "4.    press    u : Update Data",
                "5.    press    d : Delete Data",
                "      press    d#proId :  sortcut for read product by Id",
                "6.    press    f : Display First Page",
                "7.    press    p : Display Previous Page",
                "8.    press    n : Display Next Page",
                "9.    press    l : Display Last Page",
                "10.   press    s : Search product by name",
                "11.   press    g : Goto a specific page",
                "11.   press    sa : Save record to file",
                "12.   press    ba : Backup data",
                "13.   press    re : Restore data",
                "14.   press    h : Help"

        };
        /*@Seakthong App.myTable*/
        App.myTable(1, 90, "Help", Help, "......tttt");
    }

    private static String printMenu() {
        String[] menu = {"*)Display", "W)rite", "R)ead",
                "U)pdate", "D)elete", "F)irst", "P)revious",
                "N)ext", "L)ast", "S)earch", "G)oto", "Se)t",
                "Sa)ve", "Ba)ck up", "Re)store", "H)elp", "E)xit"};
        App.myTable(9, 15, "Menu", menu, "tttttttttt");
        System.out.print("Command-->");
        String str = scanner.nextLine();
        if (str.charAt(0) == '.') {
            if (str.toLowerCase().charAt(1) == 'u') {
                if (str.charAt(2) == '1') {
                    //.U1:Name:Unit:Price
                    String[] mystr = str.split("/", 10);
                } else if (str.charAt(2) == '2') {
                    System.out.println(2);
                } else if (str.charAt(2) == '3') {
                    System.out.println(3);
                } else if (str.charAt(2) == '4') {
                    System.out.println(4);
                }

            } else if (str.toLowerCase().charAt(1) == 'g') {
                int pageNumber = Validator.getNumberFromShortcut(str);
                for (int i = 2; i < str.length(); i++) {
                    pageNumber = pageNumber * 10 + Integer.parseInt(String.valueOf(str.charAt(i)));
                }
                if (Validator.isValidRangeNumber(1, pageNumber, getTotalPage())){
                    gotoPage(pageNumber);
                }
                else {
                    gotoPage(Validator.readInt("Input page number(1-" + getTotalPage() + ") : ", 1, getTotalPage()));
                }
            }
        }
        return str.toLowerCase();
    }

    private static void initTable() {
        BorderStyle borderStyle = new BorderStyle("╔═", "═", "═╤═", "═╗", "╟─", "─", "─┼─", "─╢", "╚═", "═", "═╧═", "═╝", "║ ", " │ ", " ║", "─┴─", "─┬─");
        table = new Table(5, borderStyle, new ShownBorders("tttttttttt"));
        int myMinWidth[] = {14, 32, 12, 11, 18};
        for (int i = 0; i < 5; i++) {
            table.setColumnWidth(i, myMinWidth[i], 27);
        }
        table.addCell("ID");
        table.addCell("Name");
        table.addCell("Unit Price");
        table.addCell("Qty");
        table.addCell("Date");
    }

    private static int selectChoice() {

        String mySearchChoice[] = {"1. Set row", "2. First", "3. previous", "4. Next", "5. Last", "6. Goto", "7. Display"};
        /*@Seakthong add select choice*/
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
        readData(id);
    }
    private static void readData(int id) {
        for (String product : products) {
            String[] idPro = product.split("\\|");
            if (id == Integer.parseInt(idPro[0])) {
                String shown[] = {"ID", idPro[0], "Name", idPro[1], "Price", idPro[2], "Qty", idPro[3], "Imported Date", idPro[4]};
                App.myTable(2, 20, "Product Detail", shown, "tttttttttt");
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
        if(pageNum > getTotalPage()) return;
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
        System.out.printf("%4sPage : %d of %d %64s Total Record: %d", " ", currentPage, getTotalPage(), " ", products.size());
        System.out.println();
    }

    private static int remainRowInLastPage() {
        return products.size() % numOfRows;
    }

    private static void addRowTable(String product) {
        String[] p = product.split("\\|");
        for (int i = 0; i < 5; i++)
            table.addCell(p[i]);
    }

    private static void goLast() {
        initTable();
        currentPage = getTotalPage();
        int start = numOfRows * (currentPage - 1);
        for (int i = start; i < products.size(); i++) {
            addRowTable(products.get(i));
        }
        System.out.println(table.render());
        printPageSummary();
    }


    public static boolean containedUnsavedFiles() {
        return new File("temp\\Insert.txt").exists() || new File("temp\\Delete.txt").exists() || new File("temp\\Update.txt").exists();
    }

    private static void saveInserted() {
        try {
            long startTime2 = System.nanoTime();
            File fileInsert = new File("temp\\Insert.txt");
            BufferedReader fileTempRead = new BufferedReader(new FileReader(fileInsert));
            BufferedWriter fileSourceWrite = new BufferedWriter(new FileWriter("product.txt", true));
            String line = null;
            while ((line = fileTempRead.readLine()) != null) {
                fileSourceWrite.write(line);
                fileSourceWrite.newLine();
                fileSourceWrite.flush();
            }
            fileTempRead.close();
            fileInsert.delete();
            long time2 = System.nanoTime() - startTime2;
            System.out.println("Read using " + (double) time2 / 1000000 + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveDeleted() {
        try {
            long startTime2 = System.nanoTime();
            File fileTemp = new File("deleteTempPro.txt");
            File fileSource = new File("product.txt");
            File fileDelete = new File("temp\\Delete.txt");
            BufferedReader br = new BufferedReader(new FileReader(fileSource));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileTemp));
            boolean b = false;
            String line1 = null;
            String line2 = null;
            int i = 0, j = 100;
            while ((line1 = br.readLine()) != null) {
                b = false;
                BufferedReader br2 = new BufferedReader(new FileReader(fileDelete));
                while ((line2 = br2.readLine()) != null) {
                    if (line1.split("\\|")[0].equals(line2.split("\\|")[0])) {
                        b = true;
                        break;
                    }
                }
                if (b == false) {
                    bufferedWriter.write(line1);
                    bufferedWriter.newLine();
                    if (i++ == j) {
                        j += 100;
                        bufferedWriter.flush();
                        ;
                    }
                }
            }
            br.close();
            bufferedWriter.close();
            fileSource.delete();
            fileTemp.renameTo(new File("product.txt"));
            br.close();
            fileDelete.delete();
            long time2 = System.nanoTime() - startTime2;
            System.out.println("Read using " + (double) time2 / 1000000 + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveUpdated() {
        try {
            long startTime2 = System.nanoTime();
            File fileTemp = new File("updateTempPro.txt");
            File fileSource = new File("product.txt");
            File fileUpdate = new File("temp\\Update.txt");
            BufferedReader br = new BufferedReader(new FileReader(fileSource));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileTemp));
            BufferedReader br2 = null;
            boolean b = false;
            String line1 = null;
            String line2 = null;
            while ((line1 = br.readLine()) != null) {
                b = false;
                br2 = new BufferedReader(new FileReader(fileUpdate));
                while ((line2 = br2.readLine()) != null) {
                    if (line1.split("\\|")[0].equals(line2.split("\\|")[0])) {
                        bufferedWriter.write(line2);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        b = true;
                        break;
                    }
                }
                if (b == false) {
                    bufferedWriter.write(line1);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
            br.close();
            bufferedWriter.close();
            fileSource.delete();
            fileTemp.renameTo(new File("product.txt"));
            br2.close();
            fileUpdate.delete();
            long time2 = System.nanoTime() - startTime2;
            System.out.println("Read using " + (double) time2 / 1000000 + " milliseconds");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        for (int i = 0; i < products.size(); i++) {
            try {
                bufferedWriter.write(products.get(i));
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
        deleteTempFiles();
    }

    private static void deleteTempFiles() {
        if(new File("temp\\Update.txt").exists())
            new File("temp\\Update.txt").delete();

        if(new File("temp\\Delete.txt").exists())
            new File("temp\\Delete.txt").delete();

        if(new File("temp\\Insert.txt").exists()) {
            new File("temp\\Insert.txt").delete();
        }

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
        /*@Seakthong add App.myTable*/
        String shown[] = {"ID", "" + (lastId + 1), "Name", name, "Price", "" + price, "Imported Date", getDate()};
        App.myTable(2, 20, "Result", shown, "tttttttttt");

        char answer;
        System.out.print("Are you sure to add record? [Y/y] or [N/n]:");
        answer = Character.toLowerCase(scanner.next().charAt(0));
        if (answer == 'y') {
            String product = "" + (lastId + 1) + "|" + name + "|" + price + "|" + qty + "|" + getDate();
            products.add(product);
            try (BufferedWriter writerInsert = new BufferedWriter(new FileWriter("temp\\Insert.txt", true))) {
                writerInsert.write(product);
                writerInsert.newLine();
                writerInsert.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private static void saveOption() {
        if (containedUnsavedFiles()) {
            if (Validator.readYesNo("Are you sure to add record? [Y/y] or [N/n]:") == 'n')
                return;
            if (new File("temp\\Insert.txt").exists())
                saveInserted();
            if (new File("temp\\Delete.txt").exists())
                saveDeleted();
            if (new File("temp\\Update.txt").exists())
                saveUpdated();
            System.out.println("\n\nAlready update!!!\n");
        }
    }


    public static void myTable(int colNum, int colWidth, String[] Value, String shown) {
        BorderStyle borderStyle = new BorderStyle("╔═", "═", "═╤═", "═╗", "╟─", "─", "─┼─", "─╢", "╚═", "═", "═╧═", "═╝", "║ ", " │ ", " ║", "─┴─", "─┬─");
        Table tbl = new Table(colNum, borderStyle, new ShownBorders(shown));
        //        tbl.addCell(content, CellStyle.HorizontalAlign.center,colNum);
        for (int i = 0; i < colNum; i++) {
            tbl.setColumnWidth(i, colWidth, colWidth + 10);
        }
        for (int i = 0; i < Value.length; i++) {
            tbl.addCell(Value[i]);
        }
        System.out.println(tbl.render());
    }

    public static void myTable(int colNum, int colWidth, String content, String[] Value, String shown) {
        BorderStyle borderStyle = new BorderStyle("╔═", "═", "═╤═", "═╗", "╟─", "─", "─┼─", "─╢", "╚═", "═", "═╧═", "═╝", "║ ", " │ ", " ║", "─┴─", "─┬─");
        //        CellStyle cellStyle = new CellStyle();
        Table tbl = new Table(colNum, borderStyle, new ShownBorders(shown));
        tbl.addCell(content, new CellStyle(CellStyle.HorizontalAlign.center), colNum);
        for (int i = 0; i < colNum; i++) {
            tbl.setColumnWidth(i, colWidth, colWidth + 10);
        }
        for (int i = 0; i < Value.length; i++) {
            tbl.addCell(Value[i]);
        }
        System.out.println(tbl.render());
    }

    static void myGroupname() {
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