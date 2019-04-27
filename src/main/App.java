package main;

import model.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Thread thread = new Thread(new Fetch());
        thread.start();
    }
}

class Fetch implements Runnable{

    @Override
    public void run(){
        long startTime = System.nanoTime();
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        Product product;
        for(long i = 1; i < 100; i++){
            product = new Product(i, "Coca", 10d, 1000, "12/12/2019" );
            try {
                bufferedWriter.write(product.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double)time/1000000 + " milliseconds");
    }
}

