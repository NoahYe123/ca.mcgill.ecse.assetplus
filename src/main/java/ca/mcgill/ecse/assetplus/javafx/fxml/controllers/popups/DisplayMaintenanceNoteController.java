package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
//import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddMaintenanceNoteController;
//
//import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.UpdateMaintenanceNoteController;
//import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.RemoveMaintenanceNoteController;
//
//import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewTicketNotesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
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
import javafx.scene.control.Label;
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

public class DisplayMaintenanceNoteController{
	
    // Private Fields Generation
	@FXML
	private Label numberLabel;
	
	private int maintenanceTicketId;
	
	@FXML
	private TableColumn<TOMaintenanceNote, String> indexColumn;
	
	@FXML
	private ResourceBundle resources;
	
	@FXML
	private HBox DashboardAndContent;
	
	@FXML
	private VBox TopContent;
	
	@FXML 
	private TableView<TOMaintenanceNote> maintenanceNoteTable;
	
	@FXML
	private Button addMaintenanceNoteButton;
	
	@FXML
	private TableColumn<TOMaintenanceNote, HBox> actionColumn;
	
	@FXML
	private TableColumn<TOMaintenanceNote, String> dateColumn;
	@FXML 
	private DatePicker dateSearch; 
	
	@FXML
	private TableColumn<TOMaintenanceNote, String> descriptionColumn;
	
	
	private ObservableList<TOMaintenanceNote> maintenanceNoteList;
	
	

	// Method Generation
	private void performSearch() {
		LocalDate date = dateSearch.getValue();
		FilteredList<TOMaintenanceNote> filteredMaintenanceNote = new FilteredList<>(maintenanceNoteList);
		
		filteredMaintenanceNote.setPredicate(maintenanceNote -> {
			boolean dateMatch = date == null || maintenanceNote.getDate().toLocalDate().isEqual(date);
	    	
			return dateMatch;
	    });
	
		maintenanceNoteTable.setItems(filteredMaintenanceNote);     
	}  	  
	
	@FXML
	private void initialize() {
		dateSearch.setOnAction(event->performSearch());
	
		showTableView();
		maintenanceNoteTable.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> { 
	    	 showTableView();
		});
	
		AssetPlusFXMLView.getInstance().registerRefreshEvent(maintenanceNoteTable);
	}
	
	public void showTableView(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toLocalDate().format(formatter)));
		descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
		
		maintenanceNoteList = ViewUtils.getMaintenanceNotes(maintenanceTicketId);
		indexColumn.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(String.valueOf(maintenanceNoteList.indexOf(cellData.getValue()) + 1))
	    );
		maintenanceNoteTable.setItems(maintenanceNoteList);
	
		
	
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
	
				int index = maintenanceNoteList.indexOf(cellData.getValue());
	
				Button trashBtn = new Button();
				trashBtn.getStyleClass().add("icon-trash");
				trashBtn.setPickOnBounds(true);
				trashBtn.setOnAction(event -> handleTrashButtonClicked(index));
				setCursor(trashBtn);
				Tooltip trashTooltip = new Tooltip();
				trashTooltip.setStyle("-fx-text-fill: #A30D11");
				trashBtn.setTooltip(trashTooltip);
	
				Button editBtn = new Button();
				editBtn.getStyleClass().add("icon-edit");
				editBtn.setPickOnBounds(true);
				editBtn.setOnAction(event -> handleEditButtonClicked(index));
				setCursor(editBtn);
				Tooltip editTooltip = new Tooltip();
				editTooltip.setStyle("-fx-text-fill: #A30D11");
				editBtn.setTooltip(editTooltip);
	
				
				
	            HBox hbox = new HBox(editBtn, trashBtn);
	            hbox.setSpacing(10);
	            hbox.setAlignment(Pos.CENTER);
	
	            return new SimpleObjectProperty<>(hbox);
	    });
	}
	
	@FXML
	void handleAddMaintenanceNote(ActionEvent event) {
		AddMaintenanceNoteController controller = (AddMaintenanceNoteController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddMaintenanceNotePopUp.fxml", "Add MaintenanceNote");
		if (controller!=null)
            controller.setMaintenanceTicketId(maintenanceTicketId);
	}
	
	private void handleEditButtonClicked(int index) {
		UpdateMaintenanceNoteController controller = (UpdateMaintenanceNoteController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/UpdateMaintenanceNotePopUp.fxml", "Update MaintenanceNote");
	    if (controller==null) System.out.println("controller null");
	    controller.setMaintenanceTicketIdAndIndex(maintenanceTicketId, index);
	
	}
	
	private void handleTrashButtonClicked(int index) {
		RemoveMaintenanceNoteController.setMaintenanceNoteIndex(maintenanceTicketId, index);
		RemoveMaintenanceNoteController controller = (RemoveMaintenanceNoteController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteMaintenanceNotePopUp.fxml", "Delete MaintenanceNote");
	    if (controller==null) System.out.println("controller null");
	}
	
	
	
	
	private void setCursor(Button button) {
	    button.setOnMouseEntered(event -> button.setCursor(Cursor.HAND));
	    button.setOnMouseExited(event -> button.setCursor(Cursor.DEFAULT));
	}

	public void setMaintenanceTicketId(int id) {
	 	maintenanceTicketId = id;
	    numberLabel.setText(Integer.toString(id));
	    showTableView();   
	}
	
}
