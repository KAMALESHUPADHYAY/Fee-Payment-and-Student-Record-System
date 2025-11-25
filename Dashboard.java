
import javax.swing.*;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Dashboard");
        setSize(300, 200);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addStudentBtn = new JButton("Add Student");
        addStudentBtn.setBounds(50, 30, 180, 30);
        add(addStudentBtn);

        JButton viewFeesBtn = new JButton("View Fees");
        viewFeesBtn.setBounds(50, 80, 180, 30);
        add(viewFeesBtn);

        addStudentBtn.addActionListener(e -> new AddStudentForm());
        viewFeesBtn.addActionListener(e -> new ViewFeesForm());

        setVisible(true);
    }
}
