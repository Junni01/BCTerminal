package terminal;

public class Job {

    private int jobId;
    private String jobName;
    private int totalTime;
    private int onHoldTime;
    private boolean paused;
    private boolean finished;


    public Job (int jobIdId, String jobName, int totalTime, int onHoldTime, boolean paused, boolean finished) {

        this.jobId = jobIdId;
        this.jobName = jobName;
        this.totalTime = totalTime;
        this.onHoldTime = onHoldTime;
        this.paused = paused;
        this.finished = finished;

    }

    public int getJobId() {

        return this.jobId;
    }

    public String getJobName() {

        return this.jobName;
    }


    public boolean getJobFinishedStatus() {

        return this.finished;
    }

    public boolean getJobPauseStatus() {

        return this.paused;
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

