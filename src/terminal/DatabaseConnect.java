package terminal;





import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

/*
This class handles all the database connectivity. It is used to gather the data to the 3 array lists which consist of the three object types: workers, projects and jobs.
There is also functionality to change the credentials but it is disabled at the moment.

There are also methods that update, delete and create data to/from relevant tables.



*/
public class DatabaseConnect {

    private static final String URL = "jdbc:mysql://eu-cdbr-azure-west-b.cloudapp.net:3306/junni_saku_oo";
    private static final String USERNAME = "b3dbb9d50191d1";
    private static final String PASSWORD = "ae9d11fe";

    private Connection connection = null;
    private PreparedStatement selectWorkers = null;
    private PreparedStatement insertWorker = null;
    private PreparedStatement selectProjects = null;
    private PreparedStatement insertProject = null;
    private PreparedStatement selectJobs = null;
    private PreparedStatement insertJob = null;
    private PreparedStatement showJobs = null;
    private PreparedStatement startJob;
    private PreparedStatement pauseJob;
    private PreparedStatement resumeJob;
    private PreparedStatement endJob;
    private PreparedStatement deleteProject;
    private PreparedStatement endProject;
    private PreparedStatement selectAssociatedProject;
    private PreparedStatement findWorker = null;
    private PreparedStatement getjobStatus = null;


    public DatabaseConnect() {
    	
        try
        {
            // Here we initialize the SQL statements that are used to extract the data. Some of these are no yet used and are reserved for future usage.

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            selectWorkers = connection.prepareStatement("SELECT BadgeNum, Lname, Fname, JobInProgress FROM workers");
            insertWorker = connection.prepareStatement("INSERT INTO workers VALUES (NULL ,?,?,0,?)");


            selectProjects = connection.prepareStatement("SELECT ProjectID, ProjName, TotalHours, Finished, StartTime, EndTime FROM project");
            insertProject = connection.prepareStatement("INSERT INTO project VALUES (NULL ,?,0,0,NOW(),NULL)");
            deleteProject = connection.prepareStatement("DELETE FROM project WHERE ProjectID = ?");
            endProject  = connection.prepareStatement("UPDATE project SET EndTime = NOW(), Finished = 1 WHERE ProjectID = ?");

            selectJobs = connection.prepareStatement("SELECT JobID, JobName, TotalTime, OnHoldTime, Paused, Finished, StartTime  FROM jobs WHERE Finished = 0");
            getjobStatus = connection.prepareStatement("SELECT JobID, Paused, Finished, StartTime FROM JOBS WHERE JobID = ?");





            selectAssociatedProject = connection.prepareStatement("SELECT project.ProjName FROM projectjob INNER JOIN project ON projectjob.ProjectID = project.ProjectID WHERE JobID = ?");

            insertJob = connection.prepareStatement("INSERT INTO workers VALUES (NULL ,?,0,0,0,0, NULL, NULL, NULL)");


            startJob = connection.prepareStatement("UPDATE jobs SET StartTime = NOW() WHERE JobID = ?");
            pauseJob = connection.prepareStatement("UPDATE jobs SET Paused = 1, PauseTime = NOW() WHERE JobID = ?");
            resumeJob = connection.prepareStatement("UPDATE jobs SET Paused = 0  WHERE JobID = ?");
            endJob = connection.prepareStatement("UPDATE jobs SET EndTime = NOW(), Finished = 1 WHERE JobID = ?");



            showJobs =  connection.prepareStatement("SELECT workerjob.JobID, jobs.Jobname, workerjob.WorkerID FROM workerjob INNER JOIN jobs ON workerjob.JobID = jobs.JobName WHERE Finished = '0'");



        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }


    public boolean ConnectionSet(String URL, String USERNAME, String PASSWORD) {   // This is functionality for changing the database server credentials and URL, but I'm keeping the default information as Final so at the moment it can't be changed.

     /* These are commented out since the variables are Final and cannot be changed in this version.
        when this is implemented it would be beneficial to run some kind of test connection to make sure the credentials are functional.

        this.URL = URL;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;

    */

    return true; // Better keep it this way for now.


    }

    public String ConnectionGet() {  // This method can be used to get the current connection info, I've blocked out the credentials just in case. It is not used for now.

        return "Url: " + URL + "\n" + "Useranme: *********\n" + "Password: *******";


    }



    public ArrayList<Worker> getWorkers() {  // This method extracts the worker information from the database and inserts it into an array list of workers that are used to identify the worker by badge number
        ArrayList<Worker> results = null;
        ResultSet resultSet = null;

        try
        {
            resultSet = selectWorkers.executeQuery();
            results = new ArrayList<Worker>();

            while(resultSet.next())
            {
                results.add(new Worker( resultSet.getInt("BadgeNum"), resultSet.getString("Lname"), resultSet.getString("Fname"), resultSet.getBoolean("JobinProgress")));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }

        return results;
    }



    public boolean updateJobsStatus(String JobID, int action) {   // This method is used to change the status of a job according to the button pressed. The variable action is used in the switch case to select the correct state change.


        ResultSet resultSet = null;
        boolean started = true;
        boolean paused = true;



        try {
            getjobStatus.setString(1, JobID);
            resultSet = getjobStatus.executeQuery();
            while (resultSet.next()) {

             if(resultSet.getString("StartTime").equalsIgnoreCase("")) {
                 started = false;
             } else
             {
                 started = true;
             }
             paused = resultSet.getBoolean("Paused");


            }

            switch (action) {

                case 0:
                    if (!started) {
                        startJob.setString(1, JobID);
                        startJob.executeUpdate();

                    } else { JOptionPane.showMessageDialog(null, "Job is already started!", "Error", JOptionPane.INFORMATION_MESSAGE);
                         }

                    break;

                case 1:
                    if (!paused) {
                    pauseJob.setString(1, JobID);
                    pauseJob.executeUpdate();
                    } else {  JOptionPane.showMessageDialog(null, "Job is already paused!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case 2:
                    if (paused) {
                    resumeJob.setString(1, JobID);
                    resumeJob.executeUpdate();
                    } else { JOptionPane.showMessageDialog(null, "Job is not paused", "Error", JOptionPane.INFORMATION_MESSAGE);}
                    break;
                case 3:

                    endJob.setString(1, JobID);
                    endJob.executeUpdate();
                    break;
            }


            return true;

        } catch (SQLException sqlException) {

            sqlException.printStackTrace();

        }
        return true;
    }

    public String getAssociatedProject(String jobID) {
        ResultSet resultSet = null;
        String results = "Not in project.";
        try {
            selectAssociatedProject.setString(1, jobID);
            resultSet = selectAssociatedProject.executeQuery();

            while(resultSet.next()) {

           results = resultSet.getString("ProjName");

            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }

        return results;
    }




    public ArrayList<Job> getJobs(int currentWorkerID) {  // This method gathers the job data to an array list, it accepts the current worker's badge number so jobs associated with a certain worker can be shown but this feature is not yet implemented
        ArrayList<Job> results = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;

        try
        {
            resultSet = selectJobs.executeQuery();
            results = new ArrayList<Job>();

            while(resultSet.next())
            {
                results.add(new Job( resultSet.getInt("JobID"), resultSet.getString("JobName"), resultSet.getInt("TotalTime"), resultSet.getInt("OnHoldTime"), resultSet.getBoolean("Paused"), resultSet.getBoolean("Finished"), resultSet.getString("StartTime")));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }

        return results;
    }


    public ArrayList<Project> getProjects() {  // This one gathers data on the projects from the database, similiar to the one that accesses job and worker data.
        ArrayList<Project> results = null;
        ResultSet resultSet = null;

        try
        {
            resultSet = selectProjects.executeQuery();
            results = new ArrayList<Project>();

            while(resultSet.next())
            {
                results.add(new Project(resultSet.getInt("ProjectID"), resultSet.getString("ProjName"), resultSet.getInt("TotalHours"), resultSet.getBoolean("finished"), resultSet.getString("StartTime"), resultSet.getString("EndTime")));
            }
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        finally
        {
            try
            {
                resultSet.close();
            }
            catch (SQLException sqlException)
            {
                sqlException.printStackTrace();
            }
        }

        return results;
    }

    public boolean addProject(String projectName)   // This method takes the project name which the user imputs in the new project popup and creates a new record to the project table in the database with this name.
    {

        if (projectName.length() < 20 && projectName.length() > 0) {


            try {
                insertProject.setString(1, projectName);

                insertProject.executeUpdate();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            return true;

        } else { return false; }

    }
    public void deleteProject(String projectId)  // This method deletes projects. I didn't intend to include this freature in the original plan but since I wanted the whole gallery of CRUD features I included it anyhow. Got to be careful with it lest you delete all the projects before they are finished.
    {
        try
        {
            deleteProject.setString(1, projectId);

            deleteProject.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }


    public void endProject(String projectId)   // This method takes the project id and pastes it into the SQL query which updates the 'Ended' boolean to true.
    {
        try
        {
            endProject.setString(1, projectId);

            endProject.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

}



 /* This functionality is for future use when the admin view might allow adding and deleting of workers, and jobs...some day.
     public void addWorker(String lastName, String firstName, int badgeNumber)

    {
        try
        {
            insertWorker.setString(1, lastName);
            insertWorker.setString(2, firstName);
            insertWorker.setInt(3, badgeNumber);

            insertWorker.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

   */
/*
    public void addJob(String jobName)
    {
        try
        {
            insertJob.setString(1, jobName);

            insertJob.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

*/