package terminal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.BevelBorder;

public class TerminaGUI extends JFrame {
	private JTextField idField;
	private JTable JobTable;
	
	private static DatabaseConnect databaseConnect; 
	
	private JTable table;
	DefaultTableModel tableModel;
	private JPanel LoginScreen; // These are the three screens that are available in the program
	private JPanel WorkerScreen;
	private JPanel AdminView; // Adminview panel where the user can check total hours in projects 
	
	private static Worker currentWorker; // this is the current worker that is logged in
	private static ArrayList<Worker> workers; // This arraylist is filled with the data from the workers table
	private static int workerID; // this is the variable that stores the scanned ID card.
	
	private static ArrayList<Job> jobList;
	private static Job currentJob;
	
	private static final int ID = 0;
	private static final int NAME = 1;
	private static final int COL_COUNT = 2;
	
	
	
	
	public TerminaGUI() {
		
		
		
		
		super("Work Terminal");
		setBounds(0,0,441,600);
		setTitle("BCTerminal Log In");
		getContentPane().setLayout(new CardLayout(0, 0));
		
		LoginScreen = new JPanel();
		getContentPane().add(LoginScreen, "name_21869122785206");
		LoginScreen.setLayout(null);
		
		WorkerScreen = new JPanel();
		getContentPane().add(WorkerScreen, "name_22080245895675");
		WorkerScreen.setLayout(null);
		
		JPanel AdminView = new JPanel();
		getContentPane().add(AdminView, "name_22085565042765");
		AdminView.setLayout(null);
		
		
		idField = new JTextField();
		idField.setBounds(70, 184, 284, 90);
		LoginScreen.add(idField);
		idField.setHorizontalAlignment(SwingConstants.CENTER);
		idField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idField.setColumns(10);
		
		JLabel lblWelcome = new JLabel("Scan you ID card");
		lblWelcome.setBounds(10, 49, 414, 124);
		LoginScreen.add(lblWelcome);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		
		JobTable = new JTable();
		JobTable.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		JobTable.setFont(new Font("Tahoma", Font.PLAIN, 20));
		JobTable.setBounds(15, 11, 400, 330);
		tableModel = new DefaultTableModel(
			new Object[10][COL_COUNT],
			new String[] {
				"ID", "Name"
			}
		);
		JobTable.setModel(tableModel);
		WorkerScreen.add(JobTable);
		
		JButton btnEndJob = new JButton("End Job");
		btnEndJob.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnEndJob.setBounds(10, 438, 203, 64);
		WorkerScreen.add(btnEndJob);
		
		JButton btnPauseJob = new JButton("Pause Job");
		btnPauseJob.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnPauseJob.setBounds(223, 361, 201, 64);
		WorkerScreen.add(btnPauseJob);
		
		JButton btnResumeJob = new JButton("Resume Job");
		btnResumeJob.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnResumeJob.setBounds(223, 438, 201, 64);
		WorkerScreen.add(btnResumeJob);
		
		JButton btnStartJob = new JButton("Start Job");
		btnStartJob.setFont(new Font("Tahoma", Font.PLAIN, 30));
		btnStartJob.setBounds(10, 361, 203, 66);
		WorkerScreen.add(btnStartJob);
		
		
		JButton btnNewButton = new JButton("Log In"); // when the ID is inputed into the login screen and log in is pressed the number is sent to the method for validation
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int idnumber = Integer.parseInt(idField.getText());
				
				if (idnumber == 000) { // 000 id number activates the admin's view
					LoginScreen.setVisible(false);
					AdminView.setVisible(true);
					
				} else if (WorkerIdentification(workers, idnumber)) { 
				
					LoginScreen.setVisible(false);
					WorkerScreen.setVisible(true);
					updateJobTable(currentWorker);
					
					
				
				} else { // if no matching worker id is found from the table we get an error message.
					
					JOptionPane.showMessageDialog(null, "ID number not found", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		
		btnNewButton.setBounds(95, 298, 241, 115);
		LoginScreen.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
	
		table = new JTable();
		table.setBounds(10, 362, 414, -350);
		AdminView.add(table);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.setVisible(true);
				AdminView.setVisible(false);
			}
		});
		btnLogOut.setBounds(335, 485, 89, 23);
		AdminView.add(btnLogOut);
	}



	public static void main(String args[]) {
		databaseConnect = new DatabaseConnect(); // the program initializes the connectivity to the Azure server
		workers = databaseConnect.getWorkers(); // We fill the workers array list with info from the worker table so we can use it as login information
		TerminaGUI frame = new TerminaGUI();
		frame.setVisible(true);
	}





	static boolean WorkerIdentification(ArrayList<Worker> workers1, int workerID) { // This method checks if the id number is found within the workers arraylist.
	    
	    for (Worker p : workers1) {
	        if (p.getBadgeNumber() == workerID) {
	        	currentWorker = p; // we also set the matching worker record as the current worker
	        	return true;
	           
	        }
	        
	    }
	
	    return false;
	
	
	}
	
	private void updateJobTable(Worker currentWorker) {

		jobList = databaseConnect.getJobs(); 
		tableModel.setRowCount(jobList.size());
		for (int row=0; row<jobList.size(); row++){ 
			currentJob = jobList.get(row);
			JobTable.getModel().setValueAt(currentJob.getJobId(), row, 0);
			JobTable.getModel().setValueAt(currentJob.getJobName(), row, 1);

		}
		
		
		
		
	}



}