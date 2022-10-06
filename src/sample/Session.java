package sample;

public class Session {


    //Variables returned from http request per venue
    String sessionID;
    String mlbGamePk;
    String startTime;
    String type;
    String status;
    String venueID;


    public Session(String sessionID, String mlbGamePk, String startTime, String type, String status, String venueID) {
        this.sessionID = sessionID;
        this.mlbGamePk = mlbGamePk;
        this.startTime = startTime;
        this.type = type;
        this.status = status;
        this.venueID = venueID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getMlbGamePk() {
        return mlbGamePk;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getVenueID() {
        return venueID;
    }
}
