package main;

import model.Product;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class App {

    private static HashMap<Integer, Product> products;

    private static void getData() {
        long startTime = System.nanoTime();
        FileReader fileReader = null;
        products = new HashMap<>();
        int bufferSize = 8 * 1024;
        try {
            fileReader = new FileReader("Mytest.txt");
            BufferedReader buffer = new BufferedReader(fileReader);
            long length = 0;
            String line;
            while ((line = buffer.readLine()) != null) {
                length++;
//                System.out.println(line.split("|"));
//                products.add(new Product(line));
                String parts[] = line.split("\\|");
                int id = Integer.parseInt(parts[0]);;
//                String name = parts[1];
                double unitPrice = Double.parseDouble(parts[2]);
                int stockQty = Integer.parseInt(parts[3]);
//                String importedDate = parts[4];
                products.put(id, new Product(id, parts[1], unitPrice, stockQty, parts[4]));
            }
            System.out.println("Array length:" + products.size());
            System.out.println("Read length: " + length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }

    public static void main(String[] args) throws InterruptedException{
//        getData();
//        readData();
        generateData();
    }
    private static void readData(){
        System.out.println("\n\nread\n\n");
        long startTime2 = System.nanoTime();
        String thisLine = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Mytest.txt") );

            while ((thisLine = br.readLine()) != null) {
                System.out.println(thisLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time2 = System.nanoTime() - startTime2;
        System.out.println("Read using " + (double) time2 / 1000000 + " milliseconds");
    }
    private static void generateData() {
        new Thread(() -> {
            String message = "Please wait....";
            int i = 0;
            while (i < message.length()){
                System.out.print(message.charAt(i++));
                try {
                    Thread.sleep(350);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        long startTime = System.nanoTime();
        try {
            int flush = 0;
            BufferedWriter b=new BufferedWriter(new FileWriter("Mytest.txt",false));
            for(int i=1;i<=10_000_000;i++){
                Product product = new Product(i, "Coca", 10d, 1000, "12/12/2019");
                b.write(product.toString());
                b.newLine();
                if(i == 5000 + flush){
                    flush += 5000;
                    b.flush();
                }
            }
            System.out.println("success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");

    }
}

