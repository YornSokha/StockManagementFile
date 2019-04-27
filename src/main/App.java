package main;

import model.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws InterruptedException{
        long startTime = System.nanoTime();
        FileWriter fileWriter = null;
        int bufferSize = 8 * 1024;
        try {
            fileWriter = new FileWriter("product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, bufferSize);
        Product product;

        for (int i = 0; i < 2000; i++) {
            product = new Product(i, "Coca", 10d, 1000, "12/12/2019");
            System.out.println("i = " + i);
            try {
                bufferedWriter.write(product.toString());
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        long time = System.nanoTime() - startTime;
        System.out.println("Hello World");
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }
}
