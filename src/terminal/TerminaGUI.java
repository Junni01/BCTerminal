package terminal;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TerminaGUI extends JFrame {
	private JTextField idField;
	private JTable JobTable;
	
	private static DatabaseConnect databaseConnect; 
	
	private JTable onGoingProjects;
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
	private JTable endedProjects;
	
	
	
	
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
		JobTable.setShowGrid(false);
		JobTable.setBorder(new LineBorder(new Color(0, 0, 0)));
		JobTable.setFont(new Font("Tahoma", Font.PLAIN, 25));
		JobTable.setBounds(15, 48, 400, 293);
		tableModel = new DefaultTableModel(
			new Object[10][COL_COUNT],
			new String[] {
				"ID", "Name"
			}
		);
		JobTable.setModel(tableModel);
		WorkerScreen.add(JobTable);
		
		JButton btnEndJob = new JButton("End Job");
		btnEndJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// End selected Job
                String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();
                databaseConnect.updateJobsStatus(selectedJob, 3);
                updateJobTable(currentWorker);
				
				
			}
		});
		btnEndJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnEndJob.setBounds(10, 438, 203, 64);
		WorkerScreen.add(btnEndJob);
		
		JButton btnPauseJob = new JButton("Pause Job");
		btnPauseJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

                String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();
                databaseConnect.updateJobsStatus(selectedJob, 1);
                updateJobTable(currentWorker);
				
			
			}
		});
		btnPauseJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnPauseJob.setBounds(223, 361, 192, 64);
		WorkerScreen.add(btnPauseJob);
		
		JButton btnResumeJob = new JButton("Resume Job");
		btnResumeJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

                String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();
                databaseConnect.updateJobsStatus(selectedJob, 2);
                updateJobTable(currentWorker);
				
			}
		});
		btnResumeJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnResumeJob.setBounds(223, 438, 192, 64);
		WorkerScreen.add(btnResumeJob);
		
		JButton btnStartJob = new JButton("Start Job");
		btnStartJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();
				databaseConnect.updateJobsStatus(selectedJob, 0);
				updateJobTable(currentWorker);
				
				
				
			}
		});
		btnStartJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnStartJob.setBounds(10, 361, 203, 66);
		WorkerScreen.add(btnStartJob);
		
		
		JButton btnNewButton = new JButton("Log In"); // when the ID is inputed into the login screen and log in is pressed the number is sent to the method for validation (000 automatically activates the admin view)
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int idnumber = Integer.parseInt(idField.getText());
				
				if (idnumber == 000) { // 000 id number activates the admin's view
					LoginScreen.setVisible(false);
					AdminView.setVisible(true);
					
				} else if (WorkerIdentification(idnumber)) {
				
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
		
		JButton btnConf = new JButton("Conf");
		btnConf.setBounds(340, 508, 75, 43);
		LoginScreen.add(btnConf);
		btnConf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfigureConnection();



			}
		});
	
		onGoingProjects = new JTable();
		onGoingProjects.setBounds(10, 182, 414, -170);
		AdminView.add(onGoingProjects);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.setVisible(true);
				AdminView.setVisible(false);
			}
		});
		btnLogOut.setBounds(289, 485, 126, 44);
		AdminView.add(btnLogOut);
		
		endedProjects = new JTable();
		endedProjects.setBounds(10, 439, 405, -170);
		AdminView.add(endedProjects);
		
		JButton btnNewButton_2 = new JButton("Add project");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Add new projects from admin view
				
			}
		});
		btnNewButton_2.setBounds(10, 485, 131, 44);
		AdminView.add(btnNewButton_2);
		
		JButton btnEndProject = new JButton("End project");
		btnEndProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// End selected project from admin view.
				
				
				
			}
		});
		btnEndProject.setBounds(151, 485, 131, 44);
		AdminView.add(btnEndProject);
	}



	public static void main(String args[]) {
		databaseConnect = new DatabaseConnect(); // the program initializes the connectivity to the Azure server
		TerminaGUI frame = new TerminaGUI();
		frame.setVisible(true);
	}





	static boolean WorkerIdentification(int workerID) { // This method checks if the id number is found within the workers arraylist.
		workers = databaseConnect.getWorkers(); // We fill the workers array list with info from the worker table so we can use it as login information
	    for (Worker p : workers) {
	        if (p.getBadgeNumber() == workerID) {
	        	currentWorker = p; // we also set the matching worker record as the current worker
	        	return true;
	           
	        }
	        
	    }
	
	    return false;
	
	
	}
	
	private void updateJobTable(Worker currentWorker) {

		jobList = databaseConnect.getJobs(currentWorker.getBadgeNumber());
		tableModel.setRowCount(jobList.size());
		for (int row=0; row<jobList.size(); row++){ 
			currentJob = jobList.get(row);
			JobTable.getModel().setValueAt(currentJob.getJobId(), row, 0);
			JobTable.getModel().setValueAt(currentJob.getJobName(), row, 1);

		}
		
		
		
		
	}

	private void ConfigureConnection(){ // This method only asks for the credentials but does not change them, implemented in a future version.
		JTextField URLfield = new JTextField(10);
		JTextField USERNAMEfield = new JTextField(10);
		JTextField PASSWORDfield = new JTextField(10);

		JPanel myPanel = new JPanel();

		myPanel.add(new JLabel("Server:"));
		myPanel.add(URLfield);

		myPanel.add(new JLabel("Username:"));
		myPanel.add(USERNAMEfield);

		myPanel.add(new JLabel("Password:"));
		myPanel.add(PASSWORDfield);


		int result = JOptionPane.showConfirmDialog(null, myPanel, "Enter new connection information", JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {
			if(databaseConnect.ConnectionSet(URLfield.getText(), USERNAMEfield.getText(), PASSWORDfield.getText())) {
				JOptionPane.showMessageDialog(null, "Connection changed successfully", "Info", JOptionPane.INFORMATION_MESSAGE);

			} else {
				JOptionPane.showMessageDialog(null, "Connection not changed", "Info", JOptionPane.INFORMATION_MESSAGE);

			}
		}
	}

















}