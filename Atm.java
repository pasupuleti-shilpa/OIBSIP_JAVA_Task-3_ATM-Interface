package atm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Atm extends JFrame {
    Atm(int acc, String pin) {
        // Create DefaultTableModel to hold the data for the JTable
        DefaultTableModel model = new DefaultTableModel();

        // Add the column names to the model
        model.addColumn("Transaction ID");
        model.addColumn("Transaction");
        model.addColumn("Balance Amount");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //System.out.println("driver loaded");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root","123456");
            String query = "SELECT * FROM tran WHERE Acc_no = ?";
            //System.out.println("connection");
            PreparedStatement stmt = con.prepareStatement(query);
            //System.out.println("query");
            stmt.setInt(1, acc);
            ResultSet rs = stmt.executeQuery();
            //System.out.println("exe query");
            while (rs.next()) {
            	//System.out.println("hello");
                Object[] row = {rs.getInt("Acc_no"), rs.getString("transacton"), rs.getInt("bal")};
                model.addRow(row);
            }
        }
        catch(Exception e) {
            System.out.println("error in connection"+e);
        }

        // Create JTable using the model
        JTable table = new JTable(model);

        // Create JScrollPane to hold the JTable
        JScrollPane scrollPane = new JScrollPane(table);

        // Create JPanel to hold the JScrollPane
        JPanel panel = new JPanel();
        panel.add(scrollPane);

        // Create JFrame to display the table
        JFrame frame = new JFrame("Transaction History");
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public static void main(String[] args) {
        
    }
}
