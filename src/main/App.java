package main;

import model.Product;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws InterruptedException{

        while (true){
            System.out.println("\n\n+--------------Menu--------------+");
            System.out.println("|            ------              |");
            System.out.println("| 1)Search, 2)Delete, 3)Update   |");
            System.out.println("| 4)Next, 5)Last, 6)Prevoise     |");
            System.out.println("+--------------------------------+");
            System.out.print("Enter your chioce here : ");
            switch (new Scanner(System.in).nextInt()){
                case 1:
                    System.out.println("Search");
                break;
                case 2:
                    System.out.println("Delete");
                break;
                case 3:
                    System.out.println("Update");
                break;
                case 4:
                    System.out.println("Next");
                break;
                case 5:
                    System.out.println("Last");
                break;
                case 6:
                    System.out.println("Prevoise");
                break;
                default:
                        System.out.println("again");
                break;
            }
        }
    }

}

