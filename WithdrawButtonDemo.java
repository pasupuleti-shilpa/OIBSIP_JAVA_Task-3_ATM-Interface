package atm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WithdrawButtonDemo extends JFrame{
static int balance;
    WithdrawButtonDemo(int acc,String pin) {
    	try {
        JFrame frame = new JFrame("Withdraw Button Demo");
        JPanel panel = new JPanel();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","123456");
    	String query = "SELECT * FROM acc WHERE Acc_no = ? AND Password = ?";
    	PreparedStatement stmt = con.prepareStatement(query);
    	stmt.setInt(1, acc);
    	stmt.setString(2, pin);
    	ResultSet rs = stmt.executeQuery();
    	
    	if(rs.next()) {
    		 balance=rs.getInt(3);
        JLabel label = new JLabel("Current Balance: " + balance);
        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                String input = JOptionPane.showInputDialog(frame, "Enter amount to withdraw:");
                int amount = Integer.parseInt(input);

                if (amount <= 0) {
                    JOptionPane.showMessageDialog(frame, "Invalid amount.");
                    return;
                }

                if (amount > balance) {
                    JOptionPane.showMessageDialog(frame, "Insufficient balance.");
                    return;
                }

                balance -= amount;
                try {
                    String updateQuery = "UPDATE acc SET bal = ? WHERE Acc_no = ?";
                    PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                    updateStmt.setInt(1, balance);
                    updateStmt.setInt(2, acc);
                    int rowsUpdated = updateStmt.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("Balance updated in database.");
                    }
                } catch (Exception ex) {
                    System.out.println("Error updating balance in database: " + ex.getMessage());
                }
                label.setText("Current Balance: " + balance);
                try {
                    // create a connection to the database
                    Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "123456");

                    // prepare a statement to insert the updated values into a new table
                    String insertQuery = "INSERT INTO tran (Acc_no, transacton, bal) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = con2.prepareStatement(insertQuery);

                    // set the values to be inserted in the statement
                    insertStmt.setInt(1, acc);
                    insertStmt.setString(2, "Withdrawal"+amount);
                    insertStmt.setInt(3, balance);

                    // execute the statement
                    int rowsInserted = insertStmt.executeUpdate();

                    // close the connection
                    con2.close();

                } catch (Exception ex) {
                    System.out.println("Error inserting transaction details in database: " + ex.getMessage());
                }

                JOptionPane.showMessageDialog(frame, "Successfully withdrawn " + amount);
            }
        });

        panel.add(withdrawButton);
        panel.add(label);
        frame.add(panel);
        frame.setSize(300, 100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}}catch(Exception e) {
        	System.out.println("error in connection"+e);
        }
    }
public static void main(String[] args) {
	
}
}
