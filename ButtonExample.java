package atm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ButtonExample extends JFrame{
    ButtonExample(int acc,String pin){
        JFrame frame = new JFrame("Button Example");
        JPanel panel = new JPanel();
        
        JButton b1 = new JButton("Withdraw");
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//System.out.print("hi");
            	WithdrawButtonDemo a=new WithdrawButtonDemo(acc,pin);
            			a.setVisible(true);
        		//this.dispose();
            }
        });
        JButton b2 = new JButton("Deposit");
        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//System.out.print("hello");
            	ATMInterface a=new ATMInterface(acc,pin);
            	a.setVisible(true);
        		//this.dispose();
            }
        });
        JButton b4 = new JButton("History");
        b4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	Atm a = new Atm(acc, pin);
            	a.setVisible(true);
        		//this.dispose();
            }
        });
        JButton b5 = new JButton("Quit");
        b5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//System.out.print("hello");
            	//QuitButtonExample a=new QuitButtonExample();
            	//a.setVisible(true);
            	frame.dispose();
        		//this.dispose();
            }
        });
        
        panel.add(b1);
        panel.add(b2);
        panel.add(b4);
        panel.add(b5);
        
        
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
public static void main(String[] args) {
}
}
