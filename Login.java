package atm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {

    private JLabel accountLabel, pinLabel;
    private JTextField accountTextField;
    private JPasswordField pinPasswordField;
    private JButton loginButton,signupButton;

    public Login() {
        setTitle("Login Form");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create and add components to the frame
        accountLabel = new JLabel("Account Number: ");
        pinLabel = new JLabel("PIN: ");
        accountTextField = new JTextField(10);
        pinPasswordField = new JPasswordField(10);
        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        loginButton.addActionListener(this);
        
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(accountLabel);
        panel.add(accountTextField);
        panel.add(pinLabel);
        panel.add(pinPasswordField);
        panel.add(buttonPanel);
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Signup s=new Signup();
                s.setVisible(true);
            }
        });
        add(panel);
        setVisible(true);
    }
   
    public void actionPerformed(ActionEvent e)
    {
    	 try 
    	{
        // check the account number and PIN against some database
        String ac = accountTextField.getText();
        int acc=Integer.parseInt(ac);
        String pin = new String(pinPasswordField.getPassword());
        Class.forName("com.mysql.cj.jdbc.Driver");
        if(ac.isEmpty()||pin.isEmpty())
        {
        	JOptionPane.showMessageDialog(this, "Enter the details!");
        }
        else
        {
        	
        	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","123456");
        	String query = "SELECT * FROM acc WHERE Acc_no = ? AND Password = ?";
        	PreparedStatement stmt = con.prepareStatement(query);
        	stmt.setInt(1, acc);
        	stmt.setString(2, pin);
        	ResultSet rs = stmt.executeQuery();
        	if(rs.next())
        	{
        		//System.out.print("hi");
        		ButtonExample a=new ButtonExample(acc,pin);
        		a.setVisible(true);
        		this.dispose();
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(this, "wrong account no or pin");
        	}
        	
        }
       }
    	 catch (Exception e1) 
    	    {
    	        System.out.println("error in connection");
    	    }
    }
    
    public static void main(String[] args) {
        new Login();
    }
    
}



