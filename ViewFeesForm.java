
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewFeesForm extends JFrame {
    public ViewFeesForm() {
        setTitle("View Fees");
        setSize(500, 300);
        setLocationRelativeTo(null);

        String[] columns = { "Fee ID", "Student Name", "Amount", "Date Paid" };
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll);

        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT fees.fee_id, student.name, fees.amount, fees.date_paid " +
                           "FROM fees INNER JOIN student ON fees.student_id = student.student_id";
            ResultSet rs = con.createStatement().executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("fee_id"),
                    rs.getString("name"),
                    rs.getDouble("amount"),
                    rs.getDate("date_paid")
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        setVisible(true);
    }
}
