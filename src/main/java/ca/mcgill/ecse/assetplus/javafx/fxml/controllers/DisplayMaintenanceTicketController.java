package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddMaintenanceTicketController;

import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.UpdateMaintenanceTicketController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.RemoveMaintenanceTicketController;

import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewNotesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewTicketPopUpController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DisplayMaintenanceTicketController{
	
    // Private Fields Generation
    @FXML
    private ResourceBundle resources;
    
	@FXML
	private HBox DashboardAndContent;

	@FXML
	private VBox TopContent;
	
	@FXML 
	private TableView<TOMaintenanceTicket> maintenanceTicketTable;
	
	@FXML
	private Button addMaintenanceTicketButton;
	
	@FXML
	private TableColumn<TOMaintenanceTicket, HBox> actionColumn;
	
	@FXML
	private TableColumn<TOMaintenanceTicket, Hyperlink> maintenanceTicketIdColumn;
	
	@FXML 
	private TextField maintenanceTicketIdSearch; 
	
	@FXML
	private TableColumn<TOMaintenanceTicket, String> raisedOnDateColumn;
	@FXML 
	private DatePicker raisedOnDateSearch; 
	
	
	private ObservableList<TOMaintenanceTicket> maintenanceTicketList;
	
	

	// Method Generation
	private void performSearch() {
		int id = maintenanceTicketIdSearch.getText().isEmpty() ? 0 : Integer.parseInt(maintenanceTicketIdSearch.getText().toLowerCase().trim());
		LocalDate raisedOnDate = raisedOnDateSearch.getValue();
		FilteredList<TOMaintenanceTicket> filteredMaintenanceTicket = new FilteredList<>(maintenanceTicketList);
		
		filteredMaintenanceTicket.setPredicate(maintenanceTicket -> {
			boolean idMatch = id == 0 || maintenanceTicket.getId() == id;
			boolean raisedOnDateMatch = raisedOnDate == null || maintenanceTicket.getRaisedOnDate().toLocalDate().isEqual(raisedOnDate);
	    	
			return idMatch && raisedOnDateMatch;
	    });
	
		maintenanceTicketTable.setItems(filteredMaintenanceTicket);     
	}    
	
	@FXML
	private void initialize() {
		maintenanceTicketIdSearch.setOnKeyReleased(event->performSearch());
		raisedOnDateSearch.setOnAction(event->performSearch());
	
		showTableView();
		maintenanceTicketTable.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> { 
	    	 showTableView();
		});
	
		AssetPlusFXMLView.getInstance().registerRefreshEvent(maintenanceTicketTable);
	}
	
	public void showTableView(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		raisedOnDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedOnDate().toLocalDate().format(formatter)));
	
		maintenanceTicketList = ViewUtils.getMaintenanceTickets();
		maintenanceTicketTable.setItems(maintenanceTicketList);
	
		maintenanceTicketIdColumn.setCellValueFactory(cellData -> {
		       int maintenanceTicketId = cellData.getValue().getId();
		       Hyperlink link = new Hyperlink(String.valueOf(maintenanceTicketId));
		       link.setStyle("-fx-text-fill: #8768F2; -fx-underline: true; -fx-cursor: hand;");
//		       link.setOnAction(event -> handleMaintenanceTicketClicked(maintenanceTicketId));
		
		       return new SimpleObjectProperty<>(link);
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
				int id = cellData.getValue().getId();
	
				Button trashBtn = new Button();
				trashBtn.getStyleClass().add("icon-trash");
				trashBtn.setPickOnBounds(true);
				trashBtn.setOnAction(event -> handleTrashButtonClicked(id));
				setCursor(trashBtn);
				Tooltip trashTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_trash"));
				trashTooltip.setStyle("-fx-text-fill: #A30D11");
				trashBtn.setTooltip(trashTooltip);
	
				Button editBtn = new Button();
				editBtn.getStyleClass().add("icon-edit");
				editBtn.setPickOnBounds(true);
				editBtn.setOnAction(event -> handleEditButtonClicked(id));
				setCursor(editBtn);
				Tooltip editTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_edit"));
				editTooltip.setStyle("-fx-text-fill: #A30D11");
				editBtn.setTooltip(editTooltip);
	
				Button ticketNotesBtn = new Button();
				ticketNotesBtn.getStyleClass().add("icon-ticketNotes");
				ticketNotesBtn.setPickOnBounds(true);
//				ticketNotesBtn.setOnAction(event -> handleTicketNotesButtonClicked(id));
				setCursor(ticketNotesBtn);
				Tooltip ticketNotesTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_TicketNotes"));
				ticketNotesTooltip.setStyle("-fx-text-fill: #A30D11");
				ticketNotesBtn.setTooltip(ticketNotesTooltip);
	       
	            HBox hbox = new HBox(ticketNotesBtn, editBtn, trashBtn);
	            hbox.setSpacing(10);
	            hbox.setAlignment(Pos.CENTER);
	
	            return new SimpleObjectProperty<>(hbox);
	    });
	}
	
	@FXML
	void handleAddMaintenanceTicket(ActionEvent event) {
	     AddMaintenanceTicketController controller = (AddMaintenanceTicketController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddTicketPopUp.fxml", "Add Ticket");		
	}
	
//	private void handleMaintenanceTicketClicked(int maintenanceTicketId) {
//	     ViewMaintenanceTicketFXController controller = (ViewMaintenanceTicketFXController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewTicketPopUp.fxml", "View Ticket");
//	     controller.setTicketId(ticketId);
//	}
//	
	private void handleEditButtonClicked(int maintenanceTicketId) {
		UpdateMaintenanceTicketController controller = (UpdateMaintenanceTicketController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ModifyTicketPopUp.fxml", "Update MaintenanceTicket");
	    if (controller==null) System.out.println("controller null");
	    controller.setMaintenanceTicketId(maintenanceTicketId);
	
	}
//	
	private void handleTrashButtonClicked(int maintenanceTicketId) {
		RemoveMaintenanceTicketController.setId(maintenanceTicketId);
		RemoveMaintenanceTicketController controller = (RemoveMaintenanceTicketController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteTicketPopUp.fxml", "Delete MaintenanceTicket");
	    if (controller==null) System.out.println("controller null");
	}
	
	
	void handleDatePickerClicked(ActionEvent event) {
	   ObservableList<TOMaintenanceTicket> items = maintenanceTicketTable.getItems();
	   if (raisedOnDateSearch.getValue() != null) {
	       Date date = Date.valueOf(raisedOnDateSearch.getValue());
	       FilteredList<TOMaintenanceTicket> filteredList = new FilteredList<>(maintenanceTicketList, maintenanceTicket -> maintenanceTicket.getRaisedOnDate().equals(date));
	       maintenanceTicketTable.setItems(filteredList);
	   } else {
	       maintenanceTicketTable.setItems(items);
	   }
	}
	
	

//	private void handleTicketNotesButtonClicked(int maintenanceTicket) {
//	    ViewTicketNotesController controller = (ViewTicketNotesController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewTicketNotes.fxml", "View TicketNotes");
//	    controller.setMaintenanceTicket(maintenanceTicket);
//	}
	
	private void setCursor(Button button) {
	    button.setOnMouseEntered(event -> button.setCursor(Cursor.HAND));
	    button.setOnMouseExited(event -> button.setCursor(Cursor.DEFAULT));
	}
}
