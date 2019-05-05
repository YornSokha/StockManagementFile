package sample;

import main.Complementary;
import main.GetConnection;
import main.RecordComplement;
import model.Product;

import java.lang.reflect.GenericArrayType;
import java.sql.*;
import java.util.ArrayList;

/*
* Manipulator.productQueryer(sqlStatement ) return arraylist;
* Manipulator.updater(sqlStatement) return int whether update or not;
* */



public class Manipulator {


    public static void main(String[] args) throws SQLException{

        ArrayList<Product> arrayList = Complementary.findObjectByCharacterInName("o");
        arrayList.get(2).setName("menghok hahaha");
          String str = Complementary.generateSQLstatementFromProduct(Complementary.convertFromStringToProduct(
                  Complementary.subString(arrayList.get(2).toString())
          ));
        System.out.println(str);
         Manipulator.updater(Complementary.generateSQLstatementFromProduct( arrayList.get(2)));

    }

    public static ArrayList<String> productQueryer(String sqlStatement) throws SQLException {

        ArrayList arrayList = new ArrayList();

        GetConnection.openConnection();

        PreparedStatement preparedStatement = GetConnection.connection.prepareStatement(sqlStatement);
        ResultSet resultSet = preparedStatement.executeQuery();
        //<<<<< change query ;
       while (resultSet.next()) {
            arrayList.add(
                 new Product(resultSet.getInt(1), resultSet.getString(2),resultSet.getFloat(3),resultSet.getInt(4), resultSet.getString(5))//<<<<< feild of constructor
            );
           //System.out.println(resultSet.getInt(1));

        }
        resultSet.close();
        preparedStatement.close();
        GetConnection.closeConnection();
        return arrayList;
    }

    public static int updater(String sqlStatement ) throws SQLException{

        GetConnection.openConnection();

        PreparedStatement preparedStatement = GetConnection.connection.prepareStatement(sqlStatement);
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
        GetConnection.closeConnection();

        //effected or not
        return i;
    }

    //***** generate sql statement
    public static String generateSQLstatementFromProduct(Product product){
        return "update products set name = '"+product.getName()+"',unitPrice = "+product.getUnitPrice()+",stockQty="+product.getStockQty()+",importedDate='"+product.getImportedDate()+"' where id ="+product.getId() ;
    }

    //***** String of Product overloading
    public static String generateSQLstatementFromProduct(String []product){
        return "update products set name = '"+product[1]+"',unitPrice = "+product[2]+",stockQty="+product[3]+",importedDate='"+product[4]+"' where id ="+product[0] ;
    }


}

