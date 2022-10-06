package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn venueID;
    @FXML
    private TableColumn status;
    @FXML
    private TableColumn sessionID;
    @FXML
    private TableColumn mlbGamePk;
    @FXML
    private TableColumn startTime;
    @FXML
    private ListView<String> venueIDList;
    @FXML
    private Button openSessionButton;
    @FXML
    private Button closeSessionButton;


    private String sessionManagerURL = "https://metadata.baseball.data.hawkeyeinnovations.com";
//    private String sessionManagerURL = "https://metadata.stage.baseball.data.hawkeyeinnovations.com";
    private ObservableList<Session> venueSessions = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        VenueArray venueArray = new VenueArray();
        venueIDList.setItems(venueArray.getVenueIDs());
        venueID.setCellValueFactory(new PropertyValueFactory<Session, String>("venueID"));
        status.setCellValueFactory(new PropertyValueFactory<Session, String>("status"));
        sessionID.setCellValueFactory(new PropertyValueFactory<Session, String>("sessionID"));
        mlbGamePk.setCellValueFactory(new PropertyValueFactory<Session, String>("mlbGamePk"));
        startTime.setCellValueFactory(new PropertyValueFactory<Session, String>("startTime"));
        openSessionButton.setDisable(true);
        closeSessionButton.setDisable(true);
    }

    @FXML
    private void handleSessionButtonPress(){
        String selectedVenue = venueIDList.getSelectionModel().getSelectedItem();
        selectedVenue = selectedVenue.split(" ")[0];
        HTTP httpObject = new HTTP(sessionManagerURL, selectedVenue);
        String httpGetRequestReturn;
        try {
            httpGetRequestReturn = httpObject.httpGetRequest();
            getRequestDataProcessor dataProcessor = new getRequestDataProcessor(httpGetRequestReturn);
            venueSessions.clear();
            venueSessions = dataProcessor.processReturnData();
            updateTableView(venueSessions);
            if (checkForOpenSessions()){
                openSessionButton.setDisable(true);
                closeSessionButton.setDisable(false);
            } else {
                openSessionButton.setDisable(false);
                closeSessionButton.setDisable(true);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @FXML
    private void handleGetOpenButtonPress(){
        VenueArray venueArray = new VenueArray();
        ObservableList<Session> openSessionList = FXCollections.observableArrayList();

        for(String venueID : venueArray.getVenueIDs()) {
            String selectedVenue = venueID.split(" ")[0];
            HTTP httpObject = new HTTP(sessionManagerURL, selectedVenue);
            String httpGetRequestReturn;
            try {
                httpGetRequestReturn = httpObject.httpGetRequest();
                getRequestDataProcessor dataProcessor = new getRequestDataProcessor(httpGetRequestReturn);
                venueSessions.clear();
                venueSessions = dataProcessor.processReturnData();

                for(Session session : venueSessions) {
                    System.out.println(session.getSessionID() + ", " + session.getStatus());
                    if(session.getStatus().equalsIgnoreCase("OPENED")) {
                        System.out.println("Session Added, Venue ID: " + session.getVenueID());
                        openSessionList.add(session);
                    }
                }

                updateTableView(openSessionList);

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }


    }


    @FXML
    public void handleOpenSession(){
        if(tableView.getSelectionModel().getSelectedItem() != null){
            Session session = (Session) tableView.getSelectionModel().getSelectedItem();
            System.out.println(session.getSessionID());
            HTTP httpObject = new HTTP(sessionManagerURL, venueIDList.getSelectionModel().getSelectedItem());
            try {
                httpObject.httpPatchRequest(session.getSessionID(), "OPENED");
                handleSessionButtonPress();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else  {
            System.out.println("Please select a session from the table.");
        }
    }


    @FXML
    public void handleCloseSession(){
        if(tableView.getSelectionModel().getSelectedItem() != null){
            Session session = (Session) tableView.getSelectionModel().getSelectedItem();
            System.out.println(session.getSessionID());
            HTTP httpObject = new HTTP(sessionManagerURL, venueIDList.getSelectionModel().getSelectedItem());
            try {
                httpObject.httpPatchRequest(session.getSessionID(), "CLOSED");
                handleSessionButtonPress();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        } else  {
            System.out.println("Please select a session from the table.");
        }
    }

    private boolean checkForOpenSessions(){
        for(Session session : venueSessions){
//            System.out.println(session.getSessionID()+ ": " + session.getStatus());
            if (session.getStatus().equalsIgnoreCase("OPENED")){
                return true;
            }
        }
        return false;
    }


    private void updateTableView(ObservableList<Session> sessionArrayList){
        tableView.setItems(sessionArrayList);
    }

}


