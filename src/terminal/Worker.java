package terminal;
/* This class is to do with the Workers object that is created in the main program and added to a worker array list.


*/
public class Worker {

    private int badgeNumber;
    private String lastName;
    private String firstName;
    private boolean jobInProgress;


    Worker (int badgeNumber, String lastName, String firstName, boolean jobInProgress) {

    this.badgeNumber = badgeNumber;
    this.lastName = lastName;
    this.firstName = firstName;
    this.jobInProgress = jobInProgress;

    }

    public int getBadgeNumber() {

        return this.badgeNumber;
    }

    public String getName() {

        return this.firstName + " " + this.lastName;
    }


    public boolean getJobStatus() {

        return this.jobInProgress;
    }

    public void setJobStatus(boolean jobStatus) {

        if (jobStatus = true) {

            jobInProgress = true;
        } else {

            jobInProgress = false;
        }

    }
    
}