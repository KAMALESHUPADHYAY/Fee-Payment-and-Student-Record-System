import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.net.URL;
import java.io.IOException;
import java.sql.*;
import javax.imageio.ImageIO;

public class StudentManager extends JFrame {
    DefaultTableModel model;
    JTable table;

    public StudentManager() {
        setTitle("Manage Students");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 🔹 Background Panel with image
        JPanel backgroundPanel = new JPanel() {
            Image bg;
            {
                try {
                    bg = ImageIO.read(new URL("https://i.pinimg.com/1200x/c2/26/48/c22648ceb5a6f1a45742c2a9890f5c34.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null)
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // 🔹 Table Setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Course", "Branch", "Total Fee", "Paid Fee"}, 0);
        table = new JTable(model);
        table.setOpaque(false);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        refreshTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // 🔹 Button Panel
        JPanel pnlButtons = new JPanel();
        pnlButtons.setOpaque(false);

        JButton btnAdd = new JButton("➕ Add");
        JButton btnEdit = new JButton("✏️ Update");
        JButton btnDelete = new JButton("🗑️ Delete");

        Font btnFont = new Font("Segoe UI", Font.BOLD, 15);
        styleButton(btnAdd, btnFont, new Color(76, 175, 80));      // Green
        styleButton(btnEdit, btnFont, new Color(33, 150, 243));    // Blue
        styleButton(btnDelete, btnFont, new Color(244, 67, 54));   // Red

        pnlButtons.add(btnAdd);
        pnlButtons.add(btnEdit);
        pnlButtons.add(btnDelete);
        backgroundPanel.add(pnlButtons, BorderLayout.SOUTH);

        // 🔹 Button Actions
        btnAdd.addActionListener(e -> new StudentForm(null));

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) model.getValueAt(row, 0);
            new StudentForm(id);
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) return;
            int id = (int) model.getValueAt(row, 0);
            if (JOptionPane.showConfirmDialog(this, "Delete ID " + id + "?") == 0) {
                try (Connection con = DBConnection.getConnection()) {
                    PreparedStatement ps = con.prepareStatement("DELETE FROM student1 WHERE student_id=?");
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting student: " + ex.getMessage());
                }
            }
        });

        setVisible(true);
    }

    void refreshTable() {
        model.setRowCount(0);
        try (Connection con = DBConnection.getConnection()) {
            ResultSet rs = con.prepareStatement("SELECT * FROM student1").executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("student_id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getString("branch"),
                        rs.getDouble("total_fee"),
                        rs.getDouble("paid_fee")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void styleButton(JButton button, Font font, Color bgColor) {
        button.setFont(font);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addHoverEffect(button, bgColor.darker());
    }

    private void addHoverEffect(JButton button, Color hoverColor) {
        Color original = button.getBackground();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(original);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManager::new);
    }
}
