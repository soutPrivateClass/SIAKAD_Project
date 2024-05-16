/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package students;

import dbConnection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MarksSheet {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    //check student id exist or not
    public boolean isIdExist(String id) {
        boolean isExist = false;
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE student_id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }

    //get student score
    public void getStudentsScore(JTable table, String sid) {
        String sql = "select * from score where student_id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, sid);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getString(12);
                row[12] = rs.getDouble(13);
                row[13] = rs.getDouble(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //get student CGPA
    public double getCGPA(String sid) {
        double cgpa = 0.0;
        Statement st;
        try {
            st = con.createStatement();

            ResultSet rs = st.executeQuery("select avg(average) from score where student_id = '" + sid + "'");
            while (rs.next()) {

                cgpa = rs.getDouble(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MarksSheet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cgpa;
    }
}
