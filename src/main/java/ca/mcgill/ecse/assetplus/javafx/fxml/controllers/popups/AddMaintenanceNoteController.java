package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

// JavaFX components
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.sql.Date;

// Backend controller and FXMLView

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;

public class AddMaintenanceNoteController{
	
    // Private Fields Generation
	private int maintenanceTicketId;
	
	@FXML 
	private Button addMaintenanceNoteButton;
	
	@FXML
	private Button cancelMaintenanceNoteButton;
	
	@FXML
	private Label addMaintenanceNoteError;
	
	@FXML 
	private DatePicker datePicker; 
	
	@FXML
	private TextArea descriptionField;
	
	
	

	// Method Generation
	@FXML
	void initialize(){
		
		maintenanceTicketId = -1; 
	   	datePicker.setEditable(false);
	   	datePicker.setValue(LocalDate.now());
	   	descriptionField.setEditable(true);
	   	descriptionField.setFocusTraversable(false);  
	
	   	addMaintenanceNoteError.setText(null);
	
	}	

	@FXML
	public void addTicketNotesToMaintenanceTicketClicked(ActionEvent event){
	
		Date date = Date.valueOf(datePicker.getValue());
		String description = descriptionField.getText();
		
		
		if(date == null || description == null){
			addMaintenanceNoteError.setText("One of the required fields is empty");
		}
	
		else {
			String error = AssetPlusFeatureSet7Controller.addMaintenanceNote(date, description, maintenanceTicketId);
			ViewUtils.callController("");
			
			if(error == ""){
				datePicker.setValue(null);
				descriptionField.setText("");
				addMaintenanceNoteError.setText("");
				AssetPlusFXMLView.getInstance().closePopUpWindow();  
			}
			else{
				addMaintenanceNoteError.setText(error);
			}
		}
	}

	@FXML
	public void cancelClicked(ActionEvent event) {
		 AssetPlusFXMLView.getInstance().closePopUpWindow();
	}

	public void setMaintenanceTicketId(int id) {
	  maintenanceTicketId = id;
	}
	
}
