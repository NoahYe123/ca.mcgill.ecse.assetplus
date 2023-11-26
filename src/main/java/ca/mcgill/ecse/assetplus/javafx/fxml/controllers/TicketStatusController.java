package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddTicketPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ModifyTicketPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.DeleteTicketPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewImagesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewNotesController;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import java.util.ResourceBundle;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import javafx.stage.Stage;;

public class TicketStatusController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addTicketButton;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private TableView<TOMaintenanceTicket> ticketTable;

    @FXML
    private TableColumn<TOMaintenanceTicket, Integer> ticketNumberColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> assetColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> reporterColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> assigneeColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> dateStartedColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> statusColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, HBox> actionColumn;

    @FXML
    private DatePicker datePickerBtn;

    private ObservableList<TOMaintenanceTicket> ticketList;

    @FXML
    void initialize() {
        resources = AssetPlusFXMLView.getInstance().getBundle();

        statusChoiceBox.getItems().addAll(
            resources.getString("key.Open"),
            resources.getString("key.Assigned"),
            resources.getString("key.InProgress"),
            resources.getString("key.Resolved"),
            resources.getString("key.Closed"),
            resources.getString("key.ShowAll")
        );

        statusChoiceBox.setValue(resources.getString("key.SelectStatus"));
        statusChoiceBox.setOnAction(event -> filterTableView(getKey(statusChoiceBox.getValue())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        ticketNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetName()));
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(ViewUtils.getUsername(cellData.getValue().getRaisedByEmail())));
        assigneeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFixedByEmail()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //purchaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseDate().toLocalDate().format(formatter)));
        dateStartedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedOnDate().toLocalDate().format(formatter)));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        ticketList = ViewUtils.getMaintenanceTickets();
        ticketTable.setItems(ticketList);
        
        statusColumn.setCellFactory(new Callback<TableColumn<TOMaintenanceTicket,String>,TableCell<TOMaintenanceTicket,String>>() {
            @Override
            public TableCell<TOMaintenanceTicket, String> call(TableColumn<TOMaintenanceTicket, String> param) {
                return new TableCell<TOMaintenanceTicket, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle(null);
                            setCursor(Cursor.DEFAULT);
                        }
                        else {
                            setText(resources.getString("key." + item));
                            setStyle(getStatusStyle(resources.getString("key." + item)));
                            setOnMouseClicked(event -> handleStatusCellClicked("key." + item));
                            setCursor(Cursor.HAND);
                        }
                    }

                    private String getStatusStyle(String status) {
                        switch (status) {
                            case "Open":
                            case "Ouvert":
                                return "-fx-background-color: #D3D3D3; -fx-text-fill: #696969;";
                            case "Assigned":
                            case "Assigné":
                                return "-fx-background-color: #E6F7FF; -fx-text-fill: #0066CC";
                            case "In Progress":
                            case "En progrès":
                                return "-fx-background-color: #FEF2E5; -fx-text-fill: #CD6200";
                            case "Resolved":
                            case "Résolu":
                                return "-fx-background-color: #EBF9F1; -fx-text-fill: #1F9254";
                            case "Closed":
                            case "Fermé":
                                return "-fx-background-color: #FBE7E8; -fx-text-fill: #A30D11";
                            default: 
                                return "";
                        }
                    }
                };
            }
        });

        ticketNumberColumn.setCellFactory(col -> {
            TableCell<TOMaintenanceTicket, Integer> cell = new TableCell<TOMaintenanceTicket, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(null);
                    } else {
                        setText("#" + String.valueOf(item));
                        setStyle("-fx-text-fill: #8768F2; -fx-underline: true; -fx-cursor: hand;");
                    }
                }
            };
        
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    System.out.println("Ticket number " + cell.getItem() + " clicked!");
                }
            });
        
            return cell;
        });

        actionColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item);
                }
            }
        });

        actionColumn.setCellValueFactory(cellData -> {
            int ticketId = cellData.getValue().getId();
            // Create an HBox with three SVGPath objects representing icons
            Button imgBtn = new Button();
            imgBtn.getStyleClass().add("icon-image");
            imgBtn.setPickOnBounds(true);
            imgBtn.setOnAction(event -> handleImageButtonClicked(ticketId));
            Tooltip imgTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_ViewImages"));
            imgTooltip.setStyle("-fx-text-fill: #4488D8");
            imgBtn.setTooltip(imgTooltip);

            Button notesBtn = new Button();
            notesBtn.getStyleClass().add("icon-notes");
            notesBtn.setPickOnBounds(true);
            notesBtn.setOnAction(event -> handleNotesButtonClicked(ticketId));
            Tooltip noteTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_ViewNotes"));
            noteTooltip.setStyle("-fx-text-fill: #CD6200");
            notesBtn.setTooltip(noteTooltip);

            Button editBtn = new Button();
            editBtn.getStyleClass().add("icon-edit");
            editBtn.setPickOnBounds(true);
            editBtn.setOnAction(event -> handleEditButtonClicked(ticketId));
            Tooltip editTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_ModifyTicket"));
            editTooltip.setStyle("-fx-text-fill: #624DE3");
            editBtn.setTooltip(editTooltip);

            Button trashBtn = new Button();
            trashBtn.getStyleClass().add("icon-trash");
            trashBtn.setPickOnBounds(true);
            trashBtn.setOnAction(event -> handleTrashButtonClicked(ticketId));
            Tooltip trashTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_DeleteTicket"));
            trashTooltip.setStyle("-fx-text-fill: #A30D11");
            trashBtn.setTooltip(trashTooltip);

            HBox hbox = new HBox(imgBtn, notesBtn, editBtn, trashBtn);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);

            return new SimpleObjectProperty<>(hbox);
    
        });
        
        setPercentageWidth(ticketNumberColumn, 15);
        setPercentageWidth(assetColumn, 11); 
        setPercentageWidth(reporterColumn, 11); 
        setPercentageWidth(assigneeColumn, 11);
        setPercentageWidth(dateStartedColumn, 21);
        setPercentageWidth(statusColumn, 10);
        setPercentageWidth(actionColumn, 20);
        
    }

    @FXML
    void goToTicketMenu(ActionEvent event) {
        AddTicketPopUpController controller = (AddTicketPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddTicketPopUp.fxml", "Add Ticket");
    }

    @FXML
    void filterTableView(String selectedStatus) {
        if (selectedStatus == null || selectedStatus.equals("key.ShowAll")) {
            ticketTable.setItems(ticketList);
        } else {
            FilteredList<TOMaintenanceTicket> filteredList = new FilteredList<>(ticketList, ticket -> selectedStatus.contains(ticket.getStatus()));
            ticketTable.setItems(filteredList);
        }
    }

    private String getKey(String text) {
        for (String key: resources.keySet()) {
            if (resources.getString(key).equals(text)) {
                return key;
            }
        }

        return "defaultKey";
    } 

    @FXML
    void handleDatePickerClicked(ActionEvent event) {

    }

    private void handleStatusCellClicked(String status) {
        switch (status) {
            case "key.Open":
                AssignStaffToTicketController controller = (AssignStaffToTicketController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AssignStaffToTicket.fxml", "Assign Staff To Ticket");
                break;
        }
    }

    private void handleImageButtonClicked(int ticketId) {
        ViewImagesController controller = (ViewImagesController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewImages.fxml", "View Images");
        controller.setTicketId(ticketId);
    }

    private void handleNotesButtonClicked(int ticketId) {
        ViewNotesController controller = (ViewNotesController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewNotes.fxml", "View Notes");
        controller.setTicketId(ticketId);
    }

    private void handleEditButtonClicked(int ticketId) {
        System.out.println("is anything happening?");

        ModifyTicketPopUpController controller = (ModifyTicketPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ModifyTicketPopUp.fxml", "Update Ticket");
           // if (controller==null) System.out.println("controller null");
        System.out.println("Updating with ticket number: " + Integer.toString(ticketId));
        controller.setTicketId(ticketId);

    }

    private void handleTrashButtonClicked(int ticketId) {
        System.out.println("is anything happening?");
        DeleteTicketPopUpController.setId(ticketId);
        DeleteTicketPopUpController controller = (DeleteTicketPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteTicketPopUp.fxml", "Delete Ticket");
        System.out.println("Deleting with ticket number: " + Integer.toString(ticketId));
    }

    private void setPercentageWidth(TableColumn<?, ?> column, double percentage) {
        column.prefWidthProperty().bind(ticketTable.widthProperty().multiply(percentage / 100.0));
    }

}
