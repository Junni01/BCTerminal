package terminal;

public class Project {

    private int projectId;
    private String projectName;
    private int totalHours;
    private boolean finished;
    private String startTime;
    private String endTime;


    public Project (int projectId, String projectName, int totalHours, boolean finished, String startTime, String endTime) {

        this.projectId = projectId;
        this.projectName = projectName;
        this.totalHours = totalHours;
        this.finished = finished;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getProjectId() {

        return this.projectId;
    }

    public String getProjectName() {

        return this.projectName;
    }


    public String getProjectStartTime() {

        return this.startTime;
    }

    public String getProjectEndTime() {

        return this.endTime;
    }

    public int getProjectTotalHours() {

        return this.totalHours;
    }




    public boolean getProjectFinishedStatus() {

        return this.finished;
    }



    public void setProjectFinished(boolean finish) {

        if( finish = true) {

            finished = true;
        } else {

            finished = false;
        }

    }





}
