package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.time.LocalDate;
import java.sql.Date;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;

public class UpdateMaintenanceTicketController{
	
    // Private Fields Generation
	@FXML
	private Label errorMessage;
	
	private int maintenanceTicketId;
	
	private static TOMaintenanceTicket maintenanceTicket; 
	
	@FXML
	private Button modifyMaintenanceTicketButton;
	
	@FXML
	private Button cancelButton;
	
	@FXML 
	private TextField idField; 
	
	@FXML 
	private DatePicker raisedOnDatePicker; 
	
	@FXML 
	private TextArea descriptionField; 
	
	
	
	

	// Method Generation
	@FXML
	void initialize() {
		errorMessage.setText(null);
	}
	@FXML
	void cancelClicked(ActionEvent event) {
		AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
	@FXML
	void updateMaintenanceTicketClicked(ActionEvent event) {
		String id = idField.getText();
		Date raisedOnDate = Date.valueOf(raisedOnDatePicker.getValue());
		String description = descriptionField.getText();
		
		if (id == null || raisedOnDate == null || description == null){
	
			errorMessage.setText("One or more input values is/are null!");
		} else {
			int idNumber = Integer.parseInt(id);
			String err = AssetPlusFeatureSet4Controller.updateMaintenanceTicket(idNumber, raisedOnDate, description);
	
			if (err.isEmpty()) {
				AssetPlusFXMLView.getInstance().closePopUpWindow();
			} else {
				errorMessage.setText(err);
			}
		}
	}
	
	public void setMaintenanceTicketId(int id) {
		maintenanceTicketId = id;
		maintenanceTicket = AssetPlusFeatureSet6Controller.getTicket(maintenanceTicketId);
		idField.setEditable(false);
		idField.setText(Integer.toString(maintenanceTicketId));
		raisedOnDatePicker.setEditable(false);
		raisedOnDatePicker.setValue(maintenanceTicket.getRaisedOnDate().toLocalDate());
		descriptionField.setText(maintenanceTicket.getDescription());
	
		errorMessage.setText(null);
	}
}
