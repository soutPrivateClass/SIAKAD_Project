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

public class Score {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;

    //get table max row
    public int getMaxId() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from score");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id + 1;
    }

    public boolean getdetails(String sid, String pro) {
        try {
            ps = con.prepareStatement("SELECT * FROM course WHERE student_id = ? and program =?");
            ps.setString(1, sid);
            ps.setString(2, pro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Home.StudentID.setText(String.valueOf(rs.getString(2)));
                Home.programField.setText(String.valueOf(rs.getString(3)));
                Home.course_1.setText(rs.getString(4));
                Home.course_2.setText(rs.getString(5));
                Home.course_3.setText(rs.getString(6));
                Home.course_4.setText(rs.getString(7));
                Home.course_5.setText(rs.getString(8));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Student doesn't exist");
            }

        } catch (Exception ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //get all the students score values from MySQL score table
    public void getStudentsScore(JTable table, String valueToSearch) {
        String sql = "SELECT * FROM score WHERE CONCAT (student_id)LIKE ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + valueToSearch + "%");

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
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Check whether the score id is exist
    public boolean isIdExist(String id) {
        boolean isExist = false;
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }

    //Check whether the student id and semester number exist.
    public boolean isSIdProExist(String sid, String pro) {
        boolean isExist = false;
        try {
            ps = con.prepareStatement("SELECT * FROM score WHERE student_id = ? and program=?");
            ps.setString(1, sid);
            ps.setString(2, pro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }

    //insert score into score table
    public void insert(String id, String sId, String pro, String course1, String course2, String course3, String course4, String course5,
            double score1, double score2, double score3, double score4, double score5, double average) {
        String sql = "INSERT INTO score VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, sId);
            ps.setString(3, pro);
            ps.setString(4, course1);
            ps.setDouble(5, score1);
            ps.setString(6, course2);
            ps.setDouble(7, score2);
            ps.setString(8, course3);
            ps.setDouble(9, score3);
            ps.setString(10, course4);
            ps.setDouble(11, score4);
            ps.setString(12, course5);
            ps.setDouble(13, score5);
            ps.setDouble(14, average);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score added successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //update scores 
    public void update(String id, double score1, double score2, double score3, double score4, double score5, double average) {
        String sql = "UPDATE score SET score1 = ?, score2 = ?, score3 = ?, score4 = ?, score5 = ?, average= ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, score1);
            ps.setDouble(2, score2);
            ps.setDouble(3, score3);
            ps.setDouble(4, score4);
            ps.setDouble(5, score5);
            ps.setDouble(6, average);
            ps.setString(7, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
