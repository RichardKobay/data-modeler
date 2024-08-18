package edu.upvictoria.datamodeler;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SQLDataModeler extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/edu/upvictoria/datamodeler/views/MainView.fxml"));
        root.getStylesheets().add(getClass().getResource("/edu/upvictoria/datamodeler/styles/style.css").toExternalForm());
        primaryStage.setTitle("SQL Data Modeler");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}