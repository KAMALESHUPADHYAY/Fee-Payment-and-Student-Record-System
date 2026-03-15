import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class StudentForm extends JFrame {
    JTextField tfName, tfCourse, tfBranch, tfTotal, tfPaid;
    Integer studentId;

    public StudentForm(Integer id) {
        studentId = id;
        setTitle(id==null?"Add Student":"Update Student");
        setSize(400, 350);
        setLayout(new GridLayout(7,2,10,10));
        setLocationRelativeTo(null);

        tfName = new JTextField(); tfCourse = new JTextField();
        tfBranch = new JTextField(); tfTotal = new JTextField();
        tfPaid = new JTextField();

        addFormFields();
        if (id!=null) loadStudentData(id);

        JButton btnSave = new JButton(id==null?"Add":"Save");
        btnSave.addActionListener(e -> saveStudent());
        add(new JLabel());
        add(btnSave);

        setVisible(true);
    }

    void addFormFields() {
        add(new JLabel("Name:")); add(tfName);
        add(new JLabel("Course:")); add(tfCourse);
        add(new JLabel("Branch:")); add(tfBranch);
        add(new JLabel("Total Fee:")); add(tfTotal);
        add(new JLabel("Paid Fee:")); add(tfPaid);
    }

    void loadStudentData(int id) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM student1 WHERE student_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                tfName.setText(rs.getString("name"));
                tfCourse.setText(rs.getString("course"));
                tfBranch.setText(rs.getString("branch"));
                tfTotal.setText(rs.getString("total_fee"));
                tfPaid.setText(rs.getString("paid_fee"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    void saveStudent() {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps;
            if (studentId==null) {
                ps = con.prepareStatement("INSERT INTO student1(name,course,branch,total_fee,paid_fee) VALUES(?,?,?,?,?)");
            } else {
                ps = con.prepareStatement("UPDATE student1 SET name=?,course=?,branch=?,total_fee=?,paid_fee=? WHERE student_id=?");
                ps.setInt(6, studentId);
            }
            ps.setString(1, tfName.getText());
            ps.setString(2, tfCourse.getText());
            ps.setString(3, tfBranch.getText());
            ps.setDouble(4, Double.parseDouble(tfTotal.getText()));
            ps.setDouble(5, Double.parseDouble(tfPaid.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, studentId==null?"Added!":"Updated!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new StudentManager().refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
