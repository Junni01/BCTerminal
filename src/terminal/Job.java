package terminal;
/*

This is the job object that contains the data from the job table that is extracted from the database.
It has a constructor that accepts the field data and getter method to ensure that encapsulation since the variables are set to private.
It also has a couple of setters methods that change the inner object booleans but the pausing and ending functionality
is implemented differently in the main program. The commands go straight to the database and don't change the object
variables.




 */

public class Job {

    private int jobId;
    private String jobName;
    private int totalTime;
    private int onHoldTime;
    private boolean paused;
    private boolean finished;
    private String startTime;



    public Job (int jobIdId, String jobName, int totalTime, int onHoldTime, boolean paused, boolean finished, String startTime) {

        this.jobId = jobIdId;
        this.jobName = jobName;
        this.totalTime = totalTime;
        this.onHoldTime = onHoldTime;
        this.paused = paused;
        this.finished = finished;
        this.startTime = startTime;


    }

    public int getJobId() {

        return this.jobId;
    }

    public String getJobName() {

        return this.jobName;
    }
    
    public int getJobTotal() {

        return this.totalTime;
    }
    

    public boolean getJobFinishedStatus() {

        return this.finished;
    }

    public boolean getJobPauseStatus() {

        return this.paused;
    }

    public String getStartTime() {

        return this.startTime;
    }



    public void setJobFinished(boolean finish) {

        if( finish = true) {

            finished = true;
        } else {

            finished = false;
        }

    }

    public void setJobPause(boolean pause) {

        if( pause = true) {

            paused = true;
        } else {

            paused = false;
        }

    }









}

