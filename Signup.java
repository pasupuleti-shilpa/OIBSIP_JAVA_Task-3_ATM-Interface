package atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.*;

public class Signup extends JFrame implements ActionListener{
    private JLabel accNoLabel, pinLabel, balanceLabel;
    private JTextField accNoField, balanceField;
    private JPasswordField pinField;
    private JButton submitButton;

    public Signup() {
        setTitle("Signup Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create form components
        accNoLabel = new JLabel("Account Number: ");
        accNoField = new JTextField(20);

        pinLabel = new JLabel("Password: ");
        pinField = new JPasswordField(20);

        balanceLabel = new JLabel("Balance: ");
        balanceField = new JTextField(20);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        // Add components to form
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(accNoLabel);
        panel.add(accNoField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(new JLabel());
        panel.add(submitButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get entered values from form
    	try {
        String accNo = accNoField.getText();
        int acc=Integer.parseInt(accNo);
        String pin = new String(pinField.getPassword());
        int balance = Integer.parseInt(balanceField.getText());
        Class.forName("com.mysql.cj.jdbc.Driver");
    	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","123456");
    	Statement stmt = con.createStatement();
    	String query = "INSERT INTO acc(Acc_No, Password, bal) VALUES (" + acc + ", '" + pin + "', " + balance + ")";
        stmt.executeUpdate(query);
    	}catch(Exception ex)
    	{
    		System.out.println("error in connection"+ex);
    	}
    }

    public static void main(String[] args) {
        Signup form = new Signup();
    }
}

