package terminal;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;







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
    private PreparedStatement startJob = null;
    private PreparedStatement endJob = null;
    private PreparedStatement pauseJob = null;

    private PreparedStatement findWorker = null;



    public DatabaseConnect() {
    	
        try
        {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            selectWorkers = connection.prepareStatement("SELECT BadgeNum, Lname, Fname, JobInProgress FROM workers");
            insertWorker = connection.prepareStatement("INSERT INTO workers VALUES (NULL ,?,?,0,?)");


            selectProjects = connection.prepareStatement("SELECT ProjectID, ProjName, TotalHours, Finished FROM project");
            insertProject = connection.prepareStatement("INSERT INTO project VALUES (NULL ,?,0,0,NULL,NULL)");

            selectJobs = connection.prepareStatement("SELECT JobID, JobName, TotalTime, OnHoldTime, Paused, Finished  FROM jobs");
            insertJob = connection.prepareStatement("INSERT INTO workers VALUES (NULL ,?,0,0,0,0, NULL, NULL, NULL)");

            showJobs =  connection.prepareStatement("SELECT workerjob.JobID, jobs.Jobname, workerjob.WorkerID FROM workerjob INNER JOIN jobs ON workerjob.JobID = jobs.JobName WHERE Finished = '0'");



        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public ArrayList<Worker> getWorkers() {
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

    public ArrayList<AdminInfo> AdminList() {
        ArrayList<AdminInfo> results = null;
        ResultSet resultSet = null;

        try
        {
            resultSet = selectProjects.executeQuery();
            results = new ArrayList<AdminInfo>();

            while(resultSet.next())
            {
                results.add(new AdminInfo(resultSet.getInt("ProjectID"), resultSet.getString("ProjName"), resultSet.getInt("TotalHours"), resultSet.getBoolean("Finished")));
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


    public ArrayList<Job> getJobs() {
        ArrayList<Job> results = null;
        ResultSet resultSet = null;

        try
        {
            resultSet = selectJobs.executeQuery();
            results = new ArrayList<Job>();

            while(resultSet.next())
            {
                results.add(new Job( resultSet.getInt("JobID"), resultSet.getString("JobName"), resultSet.getInt("TotalTime"), resultSet.getInt("OnHoldTime"), resultSet.getBoolean("Paused"), resultSet.getBoolean("Finished")));
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

}



 /* This functionality is for future use when the admin view might allow adding and deleting of workers, jobs, and projects...some day.
     public void addWorker(String lastName, String firstName, int badgeNumber)

    {
        try
        {
            insertWorker.setString(1, lastName);
            insertWorker.setString(2, firstName);
            insertWorker.setInt(3, badgeNumber);

            int result = insertWorker.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    public void addProject(String projectName)
    {
        try
        {
            insertProject.setString(1, projectName);

            int result = insertProject.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

    public void addJob(String jobName)
    {
        try
        {
            insertJob.setString(1, jobName);

            int result = insertJob.executeUpdate();
        }
        catch (SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }

*/