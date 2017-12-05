package terminal;



public class AdminInfo {

    private int projectId;
    private String projectName;
    private int totalHours;
    private boolean finished;


    public AdminInfo (int projectId, String projectName, int totalHours, boolean finished) {

        this.projectId = projectId;
        this.projectName = projectName;
        this.totalHours = totalHours;
        this.finished = finished;

    }

    public int getProjectId() {

        return this.projectId;
    }

    public String getProjectName() {

        return this.projectName;
    }


    public boolean getProjectStatus() {

        return this.finished;
    }

    public void setProjectStatus(boolean finish) {

        if( finish = true) {

            finished = true;
        } else {

            finished = false;
        }

    }



}
