package edu.upvictoria.datamodeler.controllers;

import edu.upvictoria.datamodeler.models.ForeignKey;
import edu.upvictoria.datamodeler.models.Table;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;

public class CreateTableController {
    @FXML
    private TextField tableNameField;
    @FXML
    private VBox columnsLayout;
    @FXML
    private Button saveButton;

    private MainController mainController;
    private List<Table> existingTables;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setExistingTables(List<Table> existingTables) {
        this.existingTables = existingTables;
    }

    @FXML
    private void handleAddColumnButton() {
        GridPane newColumn = new GridPane();
        TextField columnNameField = new TextField();
        ComboBox<String> dataTypeComboBox = new ComboBox<>();
        ComboBox<String> columnTypeComboBox = new ComboBox<>();

        dataTypeComboBox.getItems().addAll("number", "varchar", "boolean");
        columnTypeComboBox.getItems().addAll("None", "Primary Key", "Foreign Key");

        newColumn.add(columnNameField, 0, 0);
        newColumn.add(dataTypeComboBox, 1, 0);
        newColumn.add(columnTypeComboBox, 2, 0);

        ComboBox<String> tableComboBox = new ComboBox<>();
        ComboBox<String> columnComboBox = new ComboBox<>();

        columnTypeComboBox.setOnAction(event -> {
            if ("Foreign Key".equals(columnTypeComboBox.getValue())) {
                tableComboBox.getItems().clear();
                for (Table table : existingTables) {
                    tableComboBox.getItems().add(table.getTableName());
                }
                newColumn.add(tableComboBox, 3, 0);
                newColumn.add(columnComboBox, 4, 0);
                tableComboBox.setOnAction(e -> {
                    columnComboBox.getItems().clear();
                    String selectedTableName = tableComboBox.getValue();
                    for (Table table : existingTables) {
                        if (table.getTableName().equals(selectedTableName)) {
                            columnComboBox.getItems().addAll(table.getPrimaryKeys());
                            break;
                        }
                    }
                });
            } else {
                newColumn.getChildren().remove(tableComboBox);
                newColumn.getChildren().remove(columnComboBox);
            }
        });

        columnsLayout.getChildren().add(newColumn);
    }

    @FXML
    private void handleSaveButton() {
        String tableName = tableNameField.getText();
        Table newTable = new Table(tableName);

        for (javafx.scene.Node node : columnsLayout.getChildren()) {
            if (node instanceof GridPane) {
                GridPane gridPane = (GridPane) node;
                TextField columnNameField = (TextField) gridPane.getChildren().get(0);
                ComboBox<String> dataTypeComboBox = (ComboBox<String>) gridPane.getChildren().get(1);
                ComboBox<String> columnTypeComboBox = (ComboBox<String>) gridPane.getChildren().get(2);

                String columnName = columnNameField.getText();
                String dataType = dataTypeComboBox.getValue();
                newTable.addColumn(columnName, dataType);

                if ("Primary Key".equals(columnTypeComboBox.getValue())) {
                    newTable.addPrimaryKey(columnName);
                } else if ("Foreign Key".equals(columnTypeComboBox.getValue())) {
                    ComboBox<String> tableComboBox = (ComboBox<String>) gridPane.getChildren().get(3);
                    ComboBox<String> columnComboBox = (ComboBox<String>) gridPane.getChildren().get(4);

                    ForeignKey foreignKey = new ForeignKey(
                            columnName,
                            tableComboBox.getValue(),
                            columnComboBox.getValue()
                    );
                    newTable.addForeignKey(foreignKey);
                }
            }
        }

        mainController.addTable(newTable);

        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }
}
