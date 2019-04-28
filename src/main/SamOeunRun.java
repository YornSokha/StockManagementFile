package main;


import controller.Connection;
import model.Product;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class SamOeunRun {

    private static HashMap<Integer, Product> products;

    private static void getData() {
        long startTime = System.nanoTime();
        products = new HashMap<>();
        Connection.getProducts(products);
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }

    public static void main(String[] args) throws InterruptedException {
        getData();
//        readData();
//        backup();
//        reStore();
//        generateData();


    }

    private static void readData() {
        System.out.println("read\n\n");
        ArrayList<String> arrayList = new ArrayList<>();
        long startTime = System.nanoTime();
        String thisLine = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Mytest.txt"));

            while ((thisLine = bufferedReader.readLine()) != null) {
                arrayList.add(thisLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
        System.out.print("Read using " + (double) time / 1000000 + " milliseconds");
    }

    private static void generateData() {
        new Thread(() -> {
            String message = "Please wait.....";
            int i = 0;
            while (i < message.length()) {
                System.out.print(message.charAt(i++));
                try {
                    Thread.sleep(350);
                    ;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        long startTime = System.nanoTime();
        try {
            int flush = 0;
            BufferedWriter b = new BufferedWriter(new FileWriter("Mytest.txt", false));
            for (int i = 1; i <= 10_000; i++) {
                Product product = new Product(i, "Backup 4", 10d, 1000, "12/12/2019");
                b.write(product.toString());
                b.newLine();
                ;
                if (i == 5000 + flush) {
                    flush += 5000;
                    b.flush();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");

    }

    static void backup() {
        long start = System.nanoTime();
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            Date date = new Date();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Mytest.txt"));
            BufferedWriter backup = new BufferedWriter(new FileWriter("backup\\" + dateFormat.format(date) + ".bac"));
            String thisLine = "";
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
        System.out.print("Read using " + (double) time / 1000000 + " milliseconds");
//            System.out.println("Time taken by Stream Copy = "+(System.nanoTime()-start));
    }

    static void reStore() {
        File[] listOfFiles;
        listOfFiles = (new File("backup")).listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println((i + 1) + ") " + listOfFiles[i].getName());
            }
        }
        System.out.print("\nEnter your  choice : ");
        int index = new Scanner(System.in).nextInt();
        try {
            BufferedReader bacRead = new BufferedReader(new FileReader("backup\\" + listOfFiles[index - 1].getName()));
            BufferedWriter restoreTo = new BufferedWriter(new FileWriter("Mytest.txt"));
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
    }
}

