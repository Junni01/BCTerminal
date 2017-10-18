package terminal;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JList;

public class Workerview extends JFrame {
	public Workerview() {
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hello, WORKER NAME");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 237, 56);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Start Job");
		btnNewButton.setBounds(28, 426, 121, 46);
		getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("End Job");
		btnNewButton_1.setBounds(288, 426, 121, 46);
		getContentPane().add(btnNewButton_1);
		
		JButton btnPauseJob = new JButton("Pause Job");
		btnPauseJob.setBounds(159, 426, 121, 46);
		getContentPane().add(btnPauseJob);
		
		JList list = new JList();
		list.setBounds(75, 129, 1, 1);
		getContentPane().add(list);
	}
}
