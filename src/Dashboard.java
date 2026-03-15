import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("📊 Dashboard - Fee Management System");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background Panel with online image
        JPanel backgroundPanel = new JPanel() {
            Image bg;
            {
                try {
                    bg = ImageIO.read(new URL("https://media.istockphoto.com/id/1448205354/vector/investment-in-knowledge-student-loan-and-scholarship.jpg?s=2048x2048&w=is&k=20&c=-HMYEdJIcjg1CLkktF45uLLFXsVvS-G7nQR7vfbVvZM="));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bg != null) g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);

        // Panel to hold buttons
        JPanel panel = new JPanel(new GridLayout(2, 2, 20, 20));
        panel.setBounds(200, 150, 450, 280);
        panel.setOpaque(false);

        Font textFont = new Font("Segoe UI", Font.BOLD, 16);

        // Load icons and scale them
        JButton btnAdd = createButton("Add Student", "https://cdn-icons-png.flaticon.com/512/992/992651.png", new Color(76, 175, 80), textFont);
        JButton btnView = createButton("View / Update / Delete", "https://cdn-icons-png.flaticon.com/512/1828/1828817.png", new Color(33, 150, 243), textFont);
        JButton btnPay = createButton("Record Fee Payment", "https://cdn-icons-png.flaticon.com/512/484/484560.png", new Color(255, 152, 0), textFont);
        JButton btnExit = createButton("Logout", "https://cdn-icons-png.flaticon.com/512/1828/1828479.png", new Color(244, 67, 54), textFont);

        // Add buttons to panel
        panel.add(btnAdd);
        panel.add(btnView);
        panel.add(btnPay);
        panel.add(btnExit);
        backgroundPanel.add(panel);

        // Button Actions
        btnAdd.addActionListener(e -> new StudentForm(null));
        btnView.addActionListener(e -> new StudentManager());
        btnPay.addActionListener(e -> new StudentManager());
        btnExit.addActionListener(e -> {
            dispose();
            new LoginForm();
        });

        setVisible(true);
    }

    private JButton createButton(String text, String iconURL, Color bgColor, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        // Load and resize icon 1.5x (40x40)
        try {
            Image img = new ImageIcon(new URL(iconURL)).getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(img));
        } catch (IOException e) {
            System.err.println("Error loading icon for " + text);
        }

        addHoverEffect(button, bgColor.darker());
        return button;
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
        SwingUtilities.invokeLater(Dashboard::new);
    }
}
