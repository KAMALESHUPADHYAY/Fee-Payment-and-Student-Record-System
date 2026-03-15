import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class LoginForm extends JFrame {
    JTextField tfUser;
    JPasswordField pfPass;

    public LoginForm() {
        setTitle("📘 Fee System Login");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Background image panel
        JPanel backgroundPanel = new JPanel() {
            Image bg;
            {
                try {
                    bg = ImageIO.read(new URL("https://www.shutterstock.com/shutterstock/photos/1073489732/display_1500/stock-photo-top-view-school-fee-past-due-final-notice-letter-education-fee-calculator-money-coin-pen-and-1073489732.jpg"));
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

        // Dark transparent login panel
        JPanel overlay = new JPanel();
        overlay.setLayout(null);
        overlay.setBackground(new Color(0, 0, 0, 160));
        overlay.setBounds(540, 100, 350, 300);
        backgroundPanel.add(overlay);

        JLabel lblTitle = new JLabel("🧑‍🎓 Student Login");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(90, 20, 250, 40);
        overlay.add(lblTitle);

        JLabel userLabel = new JLabel("👤 Username:");
        userLabel.setBounds(30, 80, 100, 25);
        userLabel.setForeground(Color.WHITE);
        overlay.add(userLabel);

        tfUser = new JTextField();
        tfUser.setBounds(140, 80, 160, 25);
        overlay.add(tfUser);

        JLabel passLabel = new JLabel("🔑 Password:");
        passLabel.setBounds(30, 120, 100, 25);
        passLabel.setForeground(Color.WHITE);
        overlay.add(passLabel);

        pfPass = new JPasswordField();
        pfPass.setBounds(140, 120, 160, 25);
        overlay.add(pfPass);

        JCheckBox showPass = new JCheckBox("Show Password");
        showPass.setBounds(140, 150, 150, 20);
        showPass.setOpaque(false);
        showPass.setForeground(Color.WHITE);
        overlay.add(showPass);
        showPass.addActionListener(e -> pfPass.setEchoChar(showPass.isSelected() ? (char) 0 : '•'));

        JButton btnLogin = new JButton("🔓 Log In");
        btnLogin.setBounds(120, 190, 100, 30);
        btnLogin.setBackground(new Color(33, 150, 243));
        btnLogin.setForeground(Color.WHITE);
        overlay.add(btnLogin);

        //  Handle login
        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String username = tfUser.getText();
        String password = String.valueOf(pfPass.getPassword());

        // Dummy validation: you can replace this with database logic
        if (!username.isEmpty() && !password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Login Successful");
            dispose(); // Close login window
            new Dashboard(); // Open Dashboard
        } else {
            JOptionPane.showMessageDialog(this, "Enter valid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::new);
    }
}
