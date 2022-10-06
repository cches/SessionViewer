module SessionViewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.net.http;
    requires javafx.base;
    requires java.base;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.commons.codec;
    requires org.slf4j.jul;


    opens sample;
}