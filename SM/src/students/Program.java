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

public class Program {

    Connection con = MyConnection.getConnection();
    PreparedStatement ps;
    Statement st;
    ResultSet rs;

    //insert data into program
    public void insertProgram(String cid, String cN) {
        String sql = "INSERT INTO program_set VALUES (?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cid);
            ps.setString(2, cN);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "program saved successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //get all the program
    public void getProgram(JTable table, String valueToSearch) {
        String sql = "SELECT * FROM program_set WHERE CONCAT (id,name)LIKE ? order by id desc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + valueToSearch + "%");

            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[2];
                row[0] = rs.getString(1);
                row[1] = rs.getString(2);

                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //update program
    public void update(String id, String name) {
        String sql = "UPDATE program_set SET name = ? WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "program updated successfully");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //delete program
    public void delete(String id) {
        int YesOrNo = JOptionPane.showConfirmDialog(null, "program deleted.", "program Course", JOptionPane.OK_CANCEL_OPTION, 0);
        if (YesOrNo == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("DELETE FROM program_set WHERE id = ?");
                ps.setString(1, id);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "program deleted successfully");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int countPrograms() {
        int total = 0;
        try {
            st = con.createStatement();
            rs = st.executeQuery("select count(*) as 'total' from program_set");
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return total;
    }

    public String[] getPrograms() {
        String[] programs = new String[countPrograms()];
        try {
            st = con.createStatement();
            rs = st.executeQuery("select * from program_set");
            int i = 0;
            while (rs.next()) {
                programs[i] = rs.getString(2);
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return programs;
    }

    public boolean isProgramExist(String id, String pro) {
        boolean isExist = false;
        
        try {
            ps = con.prepareStatement("SELECT * FROM course WHERE student_id = ? and program = ?");
            ps.setString(1, id);
            ps.setString(2, pro);
            rs = ps.executeQuery();
            if (rs.next()) {
                isExist = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return isExist;
    }
}
