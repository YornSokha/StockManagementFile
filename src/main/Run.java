package main;

import controller.Connection;
import model.Product;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Run {
    private static HashMap<Integer, Product> products;
    private static int numOfRows = 5;
    private static int currentPage = 1;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
//        generateData();
        getData();
//        writeData();
//        readData();
//        saveUpdate();
        System.out.println("=================GO FIRST===================");
        goFirst();
        System.out.println("================== GO TO PAGE 4==================");
        gotoPage(4);
        System.out.println("================== GO TO LAST PAGE==================");
        goLast();
        System.out.println("=================== GO PREVIOUS=================");
        setRow();
        System.out.println("================== GO First==================");
        goFirst();
        System.out.println("================== GO TO PAGE 10==================");
        gotoPage(10);
        System.out.println("=================GO PREVIOUS===================");
        goPrevious();
        System.out.println("==================GO PREVIOUS==================");
        goPrevious();
        System.out.println("===================GO PREVIOUS=================");
        goPrevious();
        System.out.println("===================GO Next=================");
        goNext();
        System.out.println("===================GO FIRST=================");
        goFirst();


    }

    private static void setRow() {
        System.out.print("Number of row : ");
        scanner.next();
        numOfRows = scanner.nextInt();
    }

    private static void goNext() {
        if (currentPage != getTotalPage())
            gotoPage(++currentPage);
    }

    private static void goPrevious() {
        if (currentPage != 1)
            gotoPage(--currentPage);
    }

    private static void gotoPage(int pageNum) {
        currentPage = pageNum;
        int start = numOfRows * (currentPage - 1) + 1;
        for (int i = start; i < start + numOfRows; i++) {
            System.out.println(products.get(i).toString());
        }
        printPageSummary();
    }

    private static int getTotalPage() {
        return products.size() % numOfRows == 0 ? products.size() / numOfRows : products.size() / numOfRows + 1;
    }

    private static int getCurrentPage() {
        return currentPage;
    }

    private static void goFirst() {
        currentPage = 1;
        for (int i = 1; i <= numOfRows; i++) {
            System.out.println(products.get(i).toString());
        }
        printPageSummary();
    }

    private static void printPageSummary() {
        System.out.println("Page : " + currentPage + " of " + getTotalPage() + "\t\t\tTotal record : " + products.size());
    }

    private static void goLast() {
        currentPage = getTotalPage();
        for (int i = products.size() - (numOfRows - 1); i <= products.size(); i++) {
            System.out.println(products.get(i).toString());
        }

        printPageSummary();
    }

    private static void readData() {
        System.out.print("Read by ID : ");
        int id = scanner.nextInt();
        Product product = products.get(id);
        if (product != null) {
            System.out.println(product.toString());
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
        Product lastProduct = products.get(products.size());
        int lastId = lastProduct.getId();
        System.out.println("Product ID : " + (lastId + 1));
        System.out.print("Product's Name : ");
        String name = scanner.nextLine();
        System.out.print("Product's Price : ");
        double price = scanner.nextDouble();
        System.out.print("Product's Qty : ");
        int qty = scanner.nextInt();

        System.out.println("ID : " + (lastId + 1));
        System.out.println("Name : " + name);
        System.out.println("Price : " + price);
        System.out.println("Qty : " + qty);
        System.out.println("Imported Date : " + getDate());
        char answer;
        System.out.print("Are you sure to add record? [Y/y] or [N/n]:");
        answer = Character.toLowerCase(scanner.next().charAt(0));
        if (answer == 'y')
            products.put(lastId + 1, new Product(lastId + 1, name, price, qty, getDate()));
        scanner.nextLine();

    }

    private static void getData() {
        long startTime = System.nanoTime();
        products = new HashMap<>();
        long time = System.nanoTime() - startTime;
        Connection.getProducts(products);
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }

    private static void generateData() {
        long startTime = System.nanoTime();
        FileWriter fileWriter = null;
        int bufferSize = 8 * 1024;
        try {
            fileWriter = new FileWriter("Product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, bufferSize);
        Product product;
        for (int i = 1; i <= 5_000; i++) {
            product = new Product(i, "Coca", 10d, 1000, getDate());
            try {
                bufferedWriter.write(product.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }
}


