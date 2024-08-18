package edu.upvictoria.datamodeler.controllers;

import edu.upvictoria.datamodeler.models.ForeignKey;
import edu.upvictoria.datamodeler.models.Table;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Line;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private Button createTableButton;
    @FXML
    private Pane tablesPane;
    @FXML
    private Pane linesPane;

    private List<Table> tables = new ArrayList<>();

    @FXML
    private void handleCreateTableButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/upvictoria/datamodeler/views/CreateTableView.fxml"));
            Parent root = loader.load();

            CreateTableController controller = loader.getController();
            controller.setMainController(this);
            controller.setExistingTables(tables);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            updateTablesView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTable(Table table) {
        tables.add(table);
    }

    private void updateTablesView() {
        tablesPane.getChildren().clear();
        linesPane.getChildren().clear();

        for (Table table : tables) {
            VBox tableBox = createTableBox(table);
            tablesPane.getChildren().add(tableBox);

            makeTableDraggable(tableBox);
        }

        drawConnections();
    }

    private void drawConnections() {
        for (Table table : tables) {
            for (ForeignKey fk : table.getForeignKeys()) {
                VBox sourceTableBox = findTableBox(table.getTableName());
                VBox targetTableBox = findTableBox(fk.getReferencedTableName());

                if (sourceTableBox != null && targetTableBox != null) {
                    Line connectionLine = createConnectionLine(sourceTableBox, targetTableBox);
                    linesPane.getChildren().add(connectionLine);
                }
            }
        }
    }

    private Line createConnectionLine(VBox sourceTableBox, VBox targetTableBox) {
        Line line = new Line();

        line.startXProperty().bind(sourceTableBox.layoutXProperty().add(sourceTableBox.widthProperty().divide(2)));
        line.startYProperty().bind(sourceTableBox.layoutYProperty().add(sourceTableBox.heightProperty().divide(2)));

        line.endXProperty().bind(targetTableBox.layoutXProperty().add(targetTableBox.widthProperty().divide(2)));
        line.endYProperty().bind(targetTableBox.layoutYProperty().add(targetTableBox.heightProperty().divide(2)));

        line.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");

        return line;
    }

    private VBox findTableBox(String tableName) {
        for (javafx.scene.Node node : tablesPane.getChildren()) {
            if (node instanceof VBox) {
                VBox tableBox = (VBox) node;
                Label label = (Label) tableBox.getChildren().get(0);
                if (label.getText().equals("Table: " + tableName)) {
                    return tableBox;
                }
            }
        }
        return null;
    }

    private VBox createTableBox(Table table) {
        VBox tableBox = new VBox();
        tableBox.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: lightgrey;");
        tableBox.setSpacing(5);

        Label tableNameLabel = new Label("Table: " + table.getTableName());
        tableBox.getChildren().add(tableNameLabel);

        for (String column : table.getColumns().keySet()) {
            String columnType = table.getColumns().get(column);
            String columnInfo = column + " : " + columnType;

            if (table.getPrimaryKeys().contains(column)) {
                columnInfo += " (PK)";
            }

            for (ForeignKey fk : table.getForeignKeys()) {
                if (fk.getColumnName().equals(column)) {
                    columnInfo += " (FK -> " + fk.getReferencedTableName() + "." + fk.getReferencedColumnName() + ")";
                }
            }

            Label columnLabel = new Label(columnInfo);
            tableBox.getChildren().add(columnLabel);
        }

        return tableBox;
    }

    private void makeTableDraggable(VBox tableBox) {
        final double[] offsetX = new double[1];
        final double[] offsetY = new double[1];

        tableBox.setOnMousePressed(event -> {
            offsetX[0] = event.getSceneX() - tableBox.getLayoutX();
            offsetY[0] = event.getSceneY() - tableBox.getLayoutY();
        });

        tableBox.setOnMouseDragged(event -> {
            tableBox.setLayoutX(event.getSceneX() - offsetX[0]);
            tableBox.setLayoutY(event.getSceneY() - offsetY[0]);
        });
    }
}
