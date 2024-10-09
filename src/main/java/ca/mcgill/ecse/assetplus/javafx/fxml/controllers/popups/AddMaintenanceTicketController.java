package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

// JavaFX components
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.sql.Date;

// Backend controller and FXMLView

import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;

public class AddMaintenanceTicketController{
	
    // Private Fields Generation
	@FXML 
	private Button addMaintenanceTicketButton;
	
	@FXML
	private Button cancelMaintenanceTicketButton;
	
	@FXML
	private Label addMaintenanceTicketError;
	
	@FXML 
	private TextField idField; 
	
	@FXML 
	private DatePicker raisedOnDateField; 
	
	@FXML
	private TextArea descriptionField;
	
	
	

	// Method Generation
	@FXML
	void initialize(){
	
	   	idField.setEditable(true);
	   	idField.setFocusTraversable(false);  
	   	raisedOnDateField.setEditable(false);
	   	raisedOnDateField.setValue(LocalDate.now());
	   	descriptionField.setEditable(true);
	   	descriptionField.setFocusTraversable(false);  
	
	   	addMaintenanceTicketError.setText(null);
	
	}	

	@FXML
	public void addMaintenanceTicketClicked(ActionEvent event){
	
		String id = idField.getText();
		LocalDate date = raisedOnDateField.getValue();
		Date raisedOnDate = Date.valueOf(date);
		String description = descriptionField.getText();
		
		if(id == null || raisedOnDate == null || description == null){
			addMaintenanceTicketError.setText("One of the required fields is empty");
		}
	
		else {
			String error = AssetPlusFeatureSet4Controller.addMaintenanceTicket(Integer.parseInt(id), raisedOnDate, description);
			ViewUtils.callController("");
			
			if(error == ""){
				idField.setText("");
				raisedOnDateField.setValue(null);
				descriptionField.setText("");
				addMaintenanceTicketError.setText("");
				AssetPlusFXMLView.getInstance().closePopUpWindow();
			}
			else{
				addMaintenanceTicketError.setText(error);
			}
		}
	}

	@FXML
	public void cancelClicked(ActionEvent event) {
		 AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
}
