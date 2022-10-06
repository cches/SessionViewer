package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getRequestDataProcessor {
    private String getRequestReturnData;
    private ObservableList<Session> venueSessionArray = FXCollections.observableArrayList();
    private ArrayList<String> sessionArray = new ArrayList<>();

    public getRequestDataProcessor(String getRequestReturn) {
        this.getRequestReturnData = getRequestReturn;
    }

    public ObservableList<Session> processReturnData(){
        int openCurlyParenthesis = 0;
        int closeCurlyParenthesis = 0;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i< getRequestReturnData.length(); i++) {
            char currentChar = getRequestReturnData.charAt(i);
            if (currentChar == '{' && openCurlyParenthesis == 0) {
                sb.setLength(0);
                openCurlyParenthesis++;

            }else if(currentChar == '{'){
                openCurlyParenthesis++;
            }else if(currentChar == '}'){
                closeCurlyParenthesis++;
                if(openCurlyParenthesis == closeCurlyParenthesis){
                    sessionArray.add(sb.toString());
                    createSessionObjects(sb.toString());
                    sb.setLength(0);
                    openCurlyParenthesis = 0;
                    closeCurlyParenthesis = 0;
                }
            }
            sb.append(currentChar);
        }
        return venueSessionArray;
    }

    private void createSessionObjects(String sessionString){
//        System.out.println("creating session object");
        List<String> matches = new ArrayList<>();
        String sessionStringTrimmed = sessionString.replace("\"","");
        String sessionStringTrimmedCopy = sessionStringTrimmed;
//        System.out.println(sessionStringTrimmed);


        Pattern p = Pattern.compile("(^\\{[a-z]*?:\\{)(id:[a-z,0-9]*),(mlbGamePk:[a-z,0-9]*?),(startTime:.*?),.*?(type:[A-Z]*?)," +
                "(status:[A-Z]*?),.*?(mlbId:[0-9]*?)}}.*?");
        Matcher m = p.matcher(sessionStringTrimmed);

        Pattern p2 = Pattern.compile("(^\\{[a-z]*?:\\{)(id:[a-z0-9]*)(,mlbGamePk:[a-z,0-9]*?)?,(startTime:.*?),.*?(type:[A-Z]*?)," +
                "(status:[A-Z]*?),.*?(mlbId:[0-9]*?)}}.*?");
        Matcher m2 = p2.matcher(sessionStringTrimmedCopy);
//        System.out.println("result of matching the pattern:" + m.matches());
        if(m.matches()){
            System.out.println("Session Pattern Matched");
           System.out.println(m.group(2) + ", " + m.group(3)+ ", " + m.group(4)+ ", " + m.group(5) + ", " + m.group(6) + ", " + m.group(7));

            Session session = new Session(m.group(2).split(":")[1],m.group(3).split(":")[1],m.group(4).split(":",2)[1],
                    m.group(5).split(":")[1],m.group(6).split(":")[1],m.group(7).split(":")[1]);
            venueSessionArray.add(session);
        } else if(m2.matches()) {

            Session sessionSecondMatch = new Session(m2.group(2).split(":")[1],"N/A",m2.group(4).split(":",2)[1],
                    m2.group(5).split(":")[1],m2.group(6).split(":")[1],m2.group(7).split(":")[1]);
            venueSessionArray.add(sessionSecondMatch);
        } else {
                System.out.println("Session Pattern Not Matched");
                System.out.println(sessionStringTrimmed);
        }
        }

    }
