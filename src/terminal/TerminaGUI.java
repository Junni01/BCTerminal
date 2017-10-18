package terminal;

import javax.swing.JFrame;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class TerminaGUI extends JFrame {
	private JTextField textField;
	public TerminaGUI() {
		setTitle("BCTerminal Log In");
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		textField.setBounds(110, 220, 214, 56);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblWelcome = new JLabel("Welcome, please scan your ID-card.");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(46, 144, 341, 65);
		getContentPane().add(lblWelcome);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(139, 287, 156, 51);
		getContentPane().add(btnNewButton);
	}
}