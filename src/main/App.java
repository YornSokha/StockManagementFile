package main;

import model.Product;

import java.io.*;

public class App extends Thread{
    @Override
    public void run() {

        FileWriter fileWriter = null;
        int bufferSize = 8 * 1024;
        try {
            fileWriter = new FileWriter("product.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter, bufferSize);
        Product product;
        for (int i = 1; i <= 10000000; i++) {
            product = new Product(i, "Coca", 10d, 1000, "12/12/2019");
            System.out.println("i = " + i);
            try {
                bufferedWriter.write(product.toString());
                bufferedWriter.newLine();
//                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args) throws InterruptedException{
        long startTime = System.nanoTime();
        App a=new App();
        a.start();
        long time = System.nanoTime() - startTime;
        System.out.println("Hello Worldskjfjkdlsfjklsfd");
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");
    }
}
class Outoutest{
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        try {
            for(int i=1;i<1000;i++){
                Product product = new Product(i, "Coca", 10d, 1000, "12/12/2019");
                ObjectOutputStream output = new ObjectOutputStream (
                        new FileOutputStream("file\\myfile.txt", true));
                output.writeObject(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long time = System.nanoTime() - startTime;
        System.out.println("Read using " + (double) time / 1000000 + " milliseconds");

    }
}
