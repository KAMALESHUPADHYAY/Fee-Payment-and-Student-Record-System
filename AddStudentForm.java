
import java.sql.*;
import javax.swing.*;

public class AddStudentForm extends JFrame {
    JTextField nameField, courseField, branchField;

    public AddStudentForm() {
        setTitle("Add Student");
        setSize(300, 250);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel l1 = new JLabel("Name:");
        l1.setBounds(20, 20, 100, 25);
        add(l1);

        nameField = new JTextField();
        nameField.setBounds(100, 20, 150, 25);
        add(nameField);

        JLabel l2 = new JLabel("Course:");
        l2.setBounds(20, 60, 100, 25);
        add(l2);

        courseField = new JTextField();
        courseField.setBounds(100, 60, 150, 25);
        add(courseField);

        JLabel l3 = new JLabel("Branch:");
        l3.setBounds(20, 100, 100, 25);
        add(l3);

        branchField = new JTextField();
        branchField.setBounds(100, 100, 150, 25);
        add(branchField);

        JButton saveBtn = new JButton("Save");
        saveBtn.setBounds(100, 150, 80, 30);
        add(saveBtn);

        saveBtn.addActionListener(e -> saveStudent());

        setVisible(true);
    }

    private void saveStudent() {
        String name = nameField.getText();
        String course = courseField.getText();
        String branch = branchField.getText();

        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO student (name, course, branch) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, course);
            ps.setString(3, branch);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Student Added!");
            dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
