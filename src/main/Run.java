package main;

import model.Product;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.TreeMap;

public class Run {
    private static ArrayList<Product> products;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
//        generateData();
        getData();
        writeData();
        saveUpdate();
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
        for (int i = 0; i <products.size(); i++) {
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

    private static String getDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
        return dateFormat.format(date);
    }

    private static void writeData() {
        Product product = products.get(products.size() -1 );
        int lastId = product.getId();
        System.out.println("Product ID : " + lastId + 1);
        System.out.print("Product's Name : ");
        String name = scanner.nextLine();
        System.out.print("Product's Price : ");
        double price = scanner.nextDouble();
        System.out.print("Product's Qty : ");
        int qty = scanner.nextInt();

        System.out.println("ID : " + lastId + 1);
        System.out.println("Name : " + name);
        System.out.println("Price : " + price);
        System.out.println("Qty : " + qty);
        System.out.println("Imported Date : " + getDate());
        char answer = ' ';
        System.out.print("Are you sure to add record? [Y/y] or [N/n]:");
        answer = scanner.next().charAt(0);
        products.add(new Product(lastId+1, name, price, qty, getDate()));
        scanner.nextLine();

    }

    private static void getData() {
        long startTime = System.nanoTime();
        FileReader fileReader = null;
        products = new ArrayList<>();
        int bufferSize = 8 * 1024;
        try {
            fileReader = new FileReader("Product.txt");
            BufferedReader buffer = new BufferedReader(fileReader);
            long length = 0;
            String line;
            while ((line = buffer.readLine()) != null) {
                length++;
//                System.out.println(line.split("|"));
//                products.add(new Product(line));
                String parts[] = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
//                String name = parts[1];
                double unitPrice = Double.parseDouble(parts[2]);
                int stockQty = Integer.parseInt(parts[3]);
//                String importedDate = parts[4];
                products.add(new Product(id, parts[1], unitPrice, stockQty, parts[4]));
            }
            System.out.println("Array length:" + products.size());
            System.out.println("Read length: " + length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
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
        for (int i = 1; i <= 10_000; i++) {
            product = new Product(i, "Coca", 10d, 1000, "12/12/2019");
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


