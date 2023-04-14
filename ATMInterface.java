package atm;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ATMInterface extends JFrame {
	static int balance;
    private JLabel balanceLabel;
    
    public ATMInterface(int acc,String pin) {
    	try {
        JFrame frame = new JFrame("ATM Interface");
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
    		balanceLabel = new JLabel("Current Balance: " + balance);
            panel.add(balanceLabel);
            JButton depositButton = new JButton("Deposit");
            depositButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String amountString = JOptionPane.showInputDialog(frame, "Enter deposit amount:");
                    if (amountString != null) {
                        int amount = Integer.parseInt(amountString);
                        balance += amount;
                        balanceLabel.setText("Current Balance: " + balance);
                        try {
                            // Update the balance in the database
                            String updateQuery = "UPDATE acc SET bal = ? WHERE Acc_no = ?";
                            PreparedStatement updateStmt = con.prepareStatement(updateQuery);
                            updateStmt.setInt(1, balance);
                            updateStmt.setInt(2, acc);
                            int rowsUpdated = updateStmt.executeUpdate();
                        } catch (Exception ex) {
                            System.out.println("Error updating balance in database: " + ex.getMessage());
                        }
                        try {
                            // create a connection to the database
                            Connection con2 = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm", "root", "123456");
                            String insertQuery = "INSERT INTO tran (Acc_no, transacton, bal) VALUES (?, ?, ?)";
                            PreparedStatement insertStmt = con2.prepareStatement(insertQuery);
                            insertStmt.setInt(1, acc);
                            insertStmt.setString(2, "deposit"+amount);
                            insertStmt.setInt(3, balance);
                            int rowsInserted = insertStmt.executeUpdate();
                            con2.close();

                        } catch (Exception ex) {
                            System.out.println("Error inserting transaction details in database: " + ex.getMessage());
                        }
                        
                    }
                }
            });
            panel.add(depositButton);
            
            frame.add(panel);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}}
    	catch(Exception e) {
            	System.out.println("error in connection"+e);
            }
    		
    }
    
    public static void main(String[] args) {
    }
}
