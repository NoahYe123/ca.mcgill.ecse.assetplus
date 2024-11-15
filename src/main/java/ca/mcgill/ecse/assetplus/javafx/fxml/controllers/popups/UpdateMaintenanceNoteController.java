package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;




import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.sql.Date;



public class UpdateMaintenanceNoteController{

	
    // Private Fields Generation
	@FXML
	private Label errorMessage;
	
	private int maintenanceTicketId;
	
	
	@FXML
	private Button modifyMaintenanceNoteButton;
	
	
	@FXML
	private Button addMaintenanceNoteButton;
	
	private int index;
	
	
	@FXML
	private Button cancelButton;
	
	@FXML 
	private DatePicker datePicker; 
	
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
	void updateMaintenanceNoteClicked(ActionEvent event) {
		Date date = Date.valueOf(datePicker.getValue());
	    String description = descriptionField.getText();
		
		if (date == null || description == null){
	
			errorMessage.setText("One or more input values is/are null!");
		} else {
	
			String err = AssetPlusFeatureSet7Controller.updateMaintenanceNote(date, description, maintenanceTicketId,index);
			
	
	
			
			ViewUtils.callController("");
	
			if (err.isEmpty()) {
				AssetPlusFXMLView.getInstance().closePopUpWindow();
			} else {
			errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString("key.AddNote_ErrorDescription"));
				
			}
		}
	}
	
	public void setMaintenanceTicketIdAndIndex(int id, int index) {
		maintenanceTicketId = id;
		this.index = index;
		TOMaintenanceNote maintenanceNote = ViewUtils.getMaintenanceNotes(id).get(index);
		datePicker.setValue((maintenanceNote.getDate()).toLocalDate());
		descriptionField.setText(maintenanceNote.getDescription());
	
		errorMessage.setText(null);
	}
			
	
	
	
	
	
}
