package controller;

import model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Connection {
    public static HashMap<Integer, Product> getProducts(HashMap<Integer, Product> products){
        try (BufferedReader buffer = new BufferedReader(new FileReader("Product.txt"))
        ) {
            long length = 0;
            String line;
            while ((line = buffer.readLine()) != null) {
                length++;
                String parts[] = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                double unitPrice = Double.parseDouble(parts[2]);
                int stockQty = Integer.parseInt(parts[3]);
                products.put(id, new Product(id, parts[1], unitPrice, stockQty, parts[4]));
            }
            System.out.println("Array length:" + products.size());
            System.out.println("Read length: " + length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }
}
