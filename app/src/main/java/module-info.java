module com.antoniotassone.warehouse.app.main{
    requires org.json;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.swing;
    requires javafx.web;

    opens com.antoniotassone.warehouse to javafx.graphics;
    opens com.antoniotassone.views to javafx.fxml;
    exports com.antoniotassone.controllers;
    exports com.antoniotassone.exceptions;
    exports com.antoniotassone.models;
    exports com.antoniotassone.parser;
    exports com.antoniotassone.utilities;
    exports com.antoniotassone.views;
    exports com.antoniotassone.warehouse;
}