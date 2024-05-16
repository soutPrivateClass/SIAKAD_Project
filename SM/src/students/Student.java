package students;

import dbConnection.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Student {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    //get table max row
    public int getMaxId() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from student");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    //insert data into student table
    public void insert(String id, String name, String date, String gender, String email, String phone, String fother, String mother, String address1, String address2, String img) {
        String sql = "INSERT INTO student VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, date);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setString(6, phone);
            ps.setString(7, fother);
            ps.setString(8, mother);
            ps.setString(9, address1);
            ps.setString(10, address2);
            ps.setString(11, img);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Student added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //update student value 
    public void update(String id, String name, String date, String gender, String email, String phone, String fother, String mother, String address1, String address2, String img) {
        String sql = "UPDATE student SET name = ?, date_of_birth = ?, gender = ?, email = ?, phone = ?, father_name = ?, mother_name = ?, address1 = ?, address2 = ?, image_path = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, gender);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, fother);
            ps.setString(7, mother);
            ps.setString(8, address1);
            ps.setString(9, address2);
            ps.setString(10, img);
            System.out.println(img);
            ps.setString(11, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Student's data updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //delete student
    public void delete(String id) {
        int YesOrNo = JOptionPane.showConfirmDialog(null, "Courses and scores records will also be deleted.", "Delete Student", JOptionPane.OK_CANCEL_OPTION, 0);
        if (YesOrNo == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("DELETE FROM student WHERE id = ?");
                ps.setString(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Student deleted successfully");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //check student email address is already exists
    public boolean isEmailExist(String email) {
        boolean isExist = false;
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }

    //check student phone number is already exists
    public boolean isPhoneExist(String phone) {
        boolean isExist = false;
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE phone = ?");
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }

    //check student id is already exists
    public boolean isIdExist(String id) {
        boolean isExist = false;
        try {
            ps = con.prepareStatement("SELECT * FROM student WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }

    //get all the student value from MySQL student table
    public void getStudentsValue(JTable table, String valueToSearch) {
        String sql = "SELECT * FROM student WHERE CONCAT (id,name,email,phone)LIKE ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + valueToSearch + "%");

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[11];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int totalStudent() {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from student");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total + 1;
    }
    
}
