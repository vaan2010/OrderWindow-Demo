package Dao;

import Model.Order;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;


public class OrderDao implements ImplDao {  
    
    public static void main(String[] args) {

    }

    @Override
    public void addOrder(Order o, javax.swing.JCheckBox b1) {
        try {
            String a;
            int s;
            String sql = "insert into company.order(name, ips, gpu, mb, ram, psu, sum) values(?, ?, ?, ?, ?, ?, ?)";
            Connection con = ImplDao.getDB();
            PreparedStatement ps = con.prepareStatement(sql);
            if(b1.isSelected()){
                a = o.getName()+"(會員)";
                s = (int)(o.getSum()*0.95);
            }
            else{
                a = o.getName();
                s = o.getSum();
            }
            ps.setString(1, a);
            ps.setInt(2, o.getIps());
            ps.setInt(3, o.getGpu());
            ps.setInt(4, o.getMb());
            ps.setInt(5, o.getRam());
            ps.setInt(6, o.getRam());
            ps.setInt(7, s);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void modOrder(javax.swing.JTable table1, String name, Integer a, String[] ss, javax.swing.JCheckBox jc) {
        int[] s = new int[ss.length];
        String q = "";
        
        String sql = "select * from company.order where id = ?";
        String sqlinsert = "update company.order set name=?, ips=?, gpu=?, mb=?, ram=?, psu=?, sum=? where id=?";
        
        
        Connection con = ImplDao.getDB();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            PreparedStatement ps2 = con.prepareStatement(sqlinsert);
            ps.setInt(1, a);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                for(int i=0;i<ss.length;++i){
                    if(ss[i].equals("-1")) s[i] = rs.getInt(i+3);
                    else s[i] = Integer.parseInt(ss[i]);
                }
                if(jc.isSelected()){
                    if(name.equals("")) q = rs.getString(2)+"(會員)";
                    else q = name+"(會員)";
                }
                else{
                    if(name.equals("")) q = rs.getString(2);
                    else q = name;
                }
            }
            
            Order o1 = new Order(q, s[0], s[1], s[2], s[3], s[4]);
            
            ps2.setString(1, o1.getName());
            ps2.setInt(2, o1.getIps());
            ps2.setInt(3, o1.getGpu());
            ps2.setInt(4, o1.getMb());
            ps2.setInt(5, o1.getRam());
            ps2.setInt(6, o1.getPsu());
            if(jc.isSelected()) ps2.setInt(7, (int)(o1.getSum()*0.95));
            else ps2.setInt(7, o1.getSum());
            ps2.setInt(8, a);
            ps2.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void queOrder(javax.swing.JTable table1) {
        String sql = "select * from company.order";
        Connection con = ImplDao.getDB();
        DefaultTableModel dm = (DefaultTableModel)table1.getModel();
        dm.setRowCount(0);
        Object[] columns = {"id", "name", "ips", "gpu", "mb", "ram", "psu", "sum"};
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Object[] o = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                dm.addRow(o);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dm){};
                table1.setRowSorter(sorter);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public String datetime() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        return s.format(d);
    }

    @Override
    public void timetime(JLabel j) {
        Date dd;
        SimpleDateFormat ss;
        new Timer(0, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("a hh:mm:ss");
                j.setText(s.format(d));
            }
        }).start();
        
    }

    @Override
    public void querangeOrder(JTable table1, String[] s) {
        String sql = "select * from company.order where ips > ? && ips < ? "+
                                                        "&& gpu > ? && gpu < ? " +
                                                        "&& mb > ? && mb < ? " +
                                                        "&& ram > ? && ram < ? " +
                                                        "&& psu > ? && psu < ? ";
        int[] a = new int[10];
        
        for(int i=0;i<a.length;++i){
            if(i%2 == 1){
                if(Integer.parseInt(s[i]) == 0) a[i] = 999999999;
                else a[i] = Integer.parseInt(s[i]);
            }
            else a[i] = Integer.parseInt(s[i]);
            
        }
 
        Connection con = ImplDao.getDB();
        DefaultTableModel dm = (DefaultTableModel)table1.getModel();
        dm.setRowCount(0);
        Object[] columns = {"id", "name", "ips", "gpu", "mb", "ram", "psu", "sum"};
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            
            for(int i=0;i<a.length;++i){
            
                ps.setInt(i+1, a[i]);
            }
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Object[] o = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                dm.addRow(o);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dm){};
                table1.setRowSorter(sorter);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void queidOrder(JTable table1, int a) {
        String sql = "select * from company.order where id = ? ";
        Connection con = ImplDao.getDB();
        DefaultTableModel dm = (DefaultTableModel)table1.getModel();
        dm.setRowCount(0);
        Object[] columns = {"id", "name", "ips", "gpu", "mb", "ram", "psu", "sum"};
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Object[] o = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                dm.addRow(o);
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dm){};
                table1.setRowSorter(sorter);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void quenameOrder(JTable table1, String name) {
        String sql = "select * from company.order where name = ? ";
        Connection con = ImplDao.getDB();
        DefaultTableModel dm = (DefaultTableModel)table1.getModel();
        dm.setRowCount(0);
        Object[] columns = {"id", "name", "ips", "gpu", "mb", "ram", "psu", "sum"};
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Object[] o = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                dm.addRow(o);
//                System.out.println(rs.getInt(1));
                TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(dm){};
                table1.setRowSorter(sorter);
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public void delidOrder(javax.swing.JTable table1, int a) {
        String sql = "delete from company.order where id = ? ";
        Connection con = ImplDao.getDB();
        DefaultTableModel dm = (DefaultTableModel)table1.getModel();
        dm.setRowCount(0);
        Object[] columns = {"id", "name", "ips", "gpu", "mb", "ram", "psu", "sum"};
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delnameOrder(javax.swing.JTable table1, String name) {
        String sql = "delete from company.order where name = ? ";
        Connection con = ImplDao.getDB();
        DefaultTableModel dm = (DefaultTableModel)table1.getModel();
        dm.setRowCount(0);
        Object[] columns = {"id", "name", "ips", "gpu", "mb", "ram", "psu", "sum"};
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    
}
