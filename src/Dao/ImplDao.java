package Dao;

import Model.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public interface ImplDao {
    //Connect to server
    public static Connection getDB(){
        String mql = "jdbc:mysql://localhost:3306/company?useUnicode=true&characterEncoding=UTF-8";
        String usr = "root";
        String password = "1234";
        Connection con = null;
        String drivername = "com.mysql.jdbc.Driver";
        try {
            Class.forName(drivername);
            con = DriverManager.getConnection(mql, usr, password);
        } catch (SQLException ex) {
            Logger.getLogger(ImplDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No Connection");
        } catch (ClassNotFoundException ex) {
            System.out.println("No Driver Found");
            Logger.getLogger(ImplDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
    
    public static Connection getDB(String usr_l, String pass_l) throws SQLException{
        String mql = "jdbc:mysql://localhost:3306/company?useUnicode=true&characterEncoding=UTF-8";
        String usr = usr_l;
        String password = pass_l;
        Connection con = null;
        String drivername = "com.mysql.jdbc.Driver";

        try {
            Class.forName(drivername);
            con = DriverManager.getConnection(mql, usr, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("No Driver Found");
            Logger.getLogger(ImplDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
    
    //Add New Order
    public void addOrder(Order o, javax.swing.JCheckBox b1);
//    public void addOrder(Order o);

    //Modify Order
    public void modOrder(javax.swing.JTable table1, String name, Integer id, String[] ss, javax.swing.JCheckBox jc);

    //Query Order
    public void queOrder(javax.swing.JTable table1);
    
    //Query Order range
    public void querangeOrder(javax.swing.JTable table1, String[] s);
    
    //Query Order ID
    public void queidOrder(javax.swing.JTable table1, int a);
    
    //Query Order Name
    public void quenameOrder(javax.swing.JTable table1, String name);
    
    //Delete Order
    public void delidOrder(javax.swing.JTable table1, int a);
    
    //Delete Order
    public void delnameOrder(javax.swing.JTable table1, String name);
    
    //show date
    public String datetime();
    
    //show time
    public void timetime(JLabel j);
  
}
