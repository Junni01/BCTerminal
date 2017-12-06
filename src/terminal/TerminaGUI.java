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
	
	private static JTable onGoingProjects;
	DefaultTableModel tableModel;
	DefaultTableModel tableModel2;

	
	private JPanel LoginScreen; // These are the three screens that are available in the program
	private JPanel WorkerScreen;
	private JPanel AdminView; // Adminview panel where the user can check total hours in projects 
	
	private static Worker currentWorker; // this is the current worker that is logged in
	private static ArrayList<Worker> workers; // This arraylist is filled with the data from the workers table
	private static int workerID; // this is the variable that stores the scanned ID card.
	
	private static ArrayList<Job> jobList; // These two arraylists hold the job and project objects which are used to populate the Jtables
	private static Job currentJob;

	private static ArrayList<Project> projectList;
	private static Project currentProject;

	

	private static final int COL_COUNT = 5; // These variables hold the amount of columns for the job and projects tables
    private static final int PROJCOL_COUNT = 6;
	
	
	
	
	public TerminaGUI() {
		
		// This part is about setting up the Swing GUI, I used data cards to get different views.
		
		
		super("Work Terminal");
		setBounds(0,0,795,600);
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
		idField.setBounds(244, 175, 284, 90);
		LoginScreen.add(idField);
		idField.setHorizontalAlignment(SwingConstants.CENTER);
		idField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idField.setColumns(10);
		
		JLabel lblWelcome = new JLabel("Scan you ID card");
		lblWelcome.setBounds(189, 40, 414, 124);
		LoginScreen.add(lblWelcome);
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 33));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		
		onGoingProjects = new JTable();   // This is table that is shown in the admin view which contains information about projects.
		onGoingProjects.setFont(new Font("Tahoma", Font.PLAIN, 15));
		onGoingProjects.setBounds(10, 71, 759, 340);
		tableModel2 = new DefaultTableModel(
				new Object[10][PROJCOL_COUNT],
				new String[] {
					"ID", "Name", "Total Hours", "Start Time", "End Time", "Finished"
				}
			);
			onGoingProjects.setModel(tableModel2);
		AdminView.add(onGoingProjects);
		
		onGoingProjects.getColumnModel().getColumn(0).setPreferredWidth(10);
		onGoingProjects.getColumnModel().getColumn(1).setPreferredWidth(120);
		onGoingProjects.getColumnModel().getColumn(2).setPreferredWidth(20);
		onGoingProjects.getColumnModel().getColumn(3).setPreferredWidth(120);
		onGoingProjects.getColumnModel().getColumn(4).setPreferredWidth(120);
		onGoingProjects.getColumnModel().getColumn(5).setPreferredWidth(20);
		
		
		
		
		
		tableModel = new DefaultTableModel(
			new Object[10][COL_COUNT],
			new String[] {
				"ID", "Name", "Status", "Project", "StartTime"
			}
		);
		
		JButton btnEndJob = new JButton("End Job");
		btnEndJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// End selected Job

             try {

                String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();
                databaseConnect.updateJobsStatus(selectedJob, 3);
                updateJobTable(currentWorker);

                } catch (ArrayIndexOutOfBoundsException e) {

                    JOptionPane.showMessageDialog(null, "Please select a job", "Error", JOptionPane.INFORMATION_MESSAGE);

                }



				
			}
		});
		btnEndJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnEndJob.setBounds(566, 487, 203, 64);
		WorkerScreen.add(btnEndJob);
		
		JButton btnPauseJob = new JButton("Pause Job");
		btnPauseJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			    try {
                    String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();
                    databaseConnect.updateJobsStatus(selectedJob, 1);
                    updateJobTable(currentWorker);
                } catch (ArrayIndexOutOfBoundsException e) {

                    JOptionPane.showMessageDialog(null, "Please select a job", "Error", JOptionPane.INFORMATION_MESSAGE);
                }

			}
		});
		btnPauseJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnPauseJob.setBounds(279, 487, 192, 64);
		WorkerScreen.add(btnPauseJob);

		JButton btnResumeJob = new JButton("Resume Job");
		btnResumeJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

                try {
                    String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();

                    databaseConnect.updateJobsStatus(selectedJob, 2);

                    updateJobTable(currentWorker);

                } catch (ArrayIndexOutOfBoundsException e) {

                        JOptionPane.showMessageDialog(null, "Please select a job", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
				
			}
		});
		btnResumeJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnResumeJob.setBounds(10, 487, 192, 64);
		WorkerScreen.add(btnResumeJob);
		
		JButton btnStartJob = new JButton("Start Job");
		btnStartJob.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


			try {

                String selectedJob = JobTable.getModel().getValueAt(JobTable.getSelectedRow(), 0).toString();
                databaseConnect.updateJobsStatus(selectedJob, 0);
                updateJobTable(currentWorker);

            } catch (ArrayIndexOutOfBoundsException e) {

                JOptionPane.showMessageDialog(null, "Please select a job", "Error", JOptionPane.INFORMATION_MESSAGE);
            }

			}
		});
		btnStartJob.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnStartJob.setBounds(10, 410, 192, 66);
		WorkerScreen.add(btnStartJob);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblNewLabel.setBounds(208, 410, 561, 73);
		WorkerScreen.add(lblNewLabel);
		
		
				JobTable = new JTable();
				JobTable.setFillsViewportHeight(true);
				JobTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				JobTable.setBorder(new LineBorder(new Color(0, 0, 0)));
				JobTable.setFont(new Font("Tahoma", Font.PLAIN, 15));
				JobTable.setBounds(15, 35, 754, 364);
				
				JobTable.setModel(tableModel);
				JobTable.removeColumn(JobTable.getColumnModel().getColumn(0));
				WorkerScreen.add(JobTable);
				
				JobTable.getColumnModel().getColumn(0).setPreferredWidth(27);
				JobTable.getColumnModel().getColumn(1).setPreferredWidth(27);
				JobTable.getColumnModel().getColumn(2).setPreferredWidth(100);
				JobTable.getColumnModel().getColumn(3).setPreferredWidth(100);
				
				JLabel lblJobName = new JLabel("Job Name");
				lblJobName.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblJobName.setBounds(17, 11, 185, 24);
				WorkerScreen.add(lblJobName);
				
				JLabel lblJobStatus = new JLabel("Job Status");
				lblJobStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblJobStatus.setBounds(166, 9, 185, 24);
				WorkerScreen.add(lblJobStatus);
				
				JLabel lblAssociatedProject = new JLabel("Associated Project");
				lblAssociatedProject.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblAssociatedProject.setBounds(320, 9, 185, 24);
				WorkerScreen.add(lblAssociatedProject);
				
				JLabel lblStartTime_1 = new JLabel("Start Time");
				lblStartTime_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblStartTime_1.setBounds(544, 9, 185, 24);
				WorkerScreen.add(lblStartTime_1);

	
		
		JButton btnNewButton = new JButton("Log In"); // when the ID is inputed into the login screen and log in is pressed the number is sent to the method for validation (000 automatically activates the admin view)
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String login = idField.getText();
				idField.setText("");
				try {

					int idnumber = Integer.parseInt(login);



				if (idnumber == 000) { // 000 id number activates the admin's view
					updateProjectTable();
					LoginScreen.setVisible(false);
					AdminView.setVisible(true);

				} else if (WorkerIdentification(idnumber)) {
				// If the badge number is found in the database the user is moved to worker screen
					LoginScreen.setVisible(false);
					WorkerScreen.setVisible(true);
					updateJobTable(currentWorker);
                    lblNewLabel.setText("Current worker: " + currentWorker.getName());



				} else { // if no matching worker id is found from the table we get an error message.

					JOptionPane.showMessageDialog(null, "ID number not found", "Error", JOptionPane.INFORMATION_MESSAGE);
				}


				} catch (NumberFormatException e) {

					JOptionPane.showMessageDialog(null, "Enter a three number ID code", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		
		
		btnNewButton.setBounds(276, 302, 241, 115);
		LoginScreen.add(btnNewButton);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		JButton btnConf = new JButton("Conf");
		btnConf.setBounds(694, 508, 75, 43);
		LoginScreen.add(btnConf);
		btnConf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfigureConnection();



			}
		});
	

		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginScreen.setVisible(true);
				AdminView.setVisible(false);
			}
		});
		btnLogOut.setBounds(643, 471, 126, 44);
		AdminView.add(btnLogOut);
		

		
		JButton btnNewButton_2 = new JButton("Add project");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Add new projects from admin view

                addProject();
				updateProjectTable();

			}
		});
		btnNewButton_2.setBounds(10, 471, 131, 44);
		AdminView.add(btnNewButton_2);
		
		JButton btnEndProject = new JButton("End project");
		btnEndProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// End selected project from admin view.
                try {
                    String selectedProject = onGoingProjects.getModel().getValueAt(onGoingProjects.getSelectedRow(), 0).toString();
                    databaseConnect.endProject(selectedProject);
                    updateProjectTable();

                } catch (ArrayIndexOutOfBoundsException e) {

                    JOptionPane.showMessageDialog(null, "Please select a project", "Error", JOptionPane.INFORMATION_MESSAGE);

                }



				
			}
		});
		btnEndProject.setBounds(151, 471, 131, 44);
		AdminView.add(btnEndProject);
		
		JButton btnDeleteProject = new JButton("Delete project");
		btnDeleteProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                try {
                    // Delete selected projects from the admin view (and from the database)
                    String selectedProject = onGoingProjects.getModel().getValueAt(onGoingProjects.getSelectedRow(), 0).toString();
                    databaseConnect.deleteProject(selectedProject);
                    updateProjectTable();
                } catch (ArrayIndexOutOfBoundsException e) {

                    JOptionPane.showMessageDialog(null, "Please select a project", "Error", JOptionPane.INFORMATION_MESSAGE);

                }

			}
		});
		btnDeleteProject.setBounds(292, 471, 131, 44);
		AdminView.add(btnDeleteProject);
		
		JLabel lblNewLabel_1 = new JLabel("ID");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 42, 28, 27);
		AdminView.add(lblNewLabel_1);
		
		JLabel lblProjectName = new JLabel("Project Name");
		lblProjectName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblProjectName.setBounds(80, 42, 164, 27);
		AdminView.add(lblProjectName);
		
		JLabel lblTotalHours = new JLabel("Total hours");
		lblTotalHours.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTotalHours.setBounds(261, 42, 79, 27);
		AdminView.add(lblTotalHours);
		
		JLabel lblStartTime = new JLabel("Start Time");
		lblStartTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStartTime.setBounds(377, 42, 86, 27);
		AdminView.add(lblStartTime);
		
		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEndTime.setBounds(555, 42, 79, 27);
		AdminView.add(lblEndTime);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblStatus.setBounds(690, 42, 79, 27);
		AdminView.add(lblStatus);
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
	
	private void updateJobTable(Worker currentWorker) {  // Connect to the database and fetch the job information and create job objects with this data and insert them to arraylist which is used to populate the table

		jobList = databaseConnect.getJobs(currentWorker.getBadgeNumber());

		tableModel.setRowCount(jobList.size());
		for (int row=0; row<jobList.size(); row++) {
			currentJob = jobList.get(row);
			JobTable.getModel().setValueAt(currentJob.getJobId(), row, 0);
			JobTable.getModel().setValueAt(currentJob.getJobName(), row, 1);
            if (currentJob.getJobPauseStatus()) {
                JobTable.getModel().setValueAt("Paused", row, 2);
            } else {
                JobTable.getModel().setValueAt(" ", row, 2);
            }
			String project = Integer.toString(currentJob.getJobId());
            String asProject = databaseConnect.getAssociatedProject(project);
			JobTable.getModel().setValueAt(asProject, row, 3);

            JobTable.getModel().setValueAt(currentJob.getStartTime(), row, 4);



		}
		
		
		
		
	}






	private void updateProjectTable() {  // Same stuff here but for the project table, we have an if clause to turn the boolean into different strings.

		projectList = databaseConnect.getProjects();
		tableModel2.setRowCount(projectList.size());
		for (int row=0; row<projectList.size(); row++) {
            currentProject = projectList.get(row);



                onGoingProjects.getModel().setValueAt(currentProject.getProjectId(), row, 0);
                onGoingProjects.getModel().setValueAt(currentProject.getProjectName(), row, 1);
                onGoingProjects.getModel().setValueAt(currentProject.getProjectTotalHours(), row, 2);
                onGoingProjects.getModel().setValueAt(currentProject.getProjectStartTime(), row, 3);
                onGoingProjects.getModel().setValueAt(currentProject.getProjectEndTime(), row, 4);

                if (currentProject.getProjectFinishedStatus() == true) {
                    onGoingProjects.getModel().setValueAt("Finished", row, 5);
                } else {
                    onGoingProjects.getModel().setValueAt("Ongoing", row, 5);
                }



        }


	}

	private void addProject() {  // This method is used to add projects to the project table, it fires a popup that asks for the projects name and then creates a new project with that name.
        JTextField nameField = new JTextField(10);
        JPanel myPanel = new JPanel();

        myPanel.add(new JLabel("New project name:"));
        myPanel.add(nameField);

        int result = JOptionPane.showConfirmDialog(null, myPanel, "Add a new project", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            if(databaseConnect.addProject(nameField.getText())) {
                JOptionPane.showMessageDialog(null, "New project added", "Info", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Project name no compatible", "Info", JOptionPane.INFORMATION_MESSAGE);

            }
        }

    }

	private void ConfigureConnection(){ // This method asks for the server's credentials but does not change them, implemented in a future version.
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