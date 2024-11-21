package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet7Controller;

public class RemoveMaintenanceNoteController{
	
    // Private Fields Generation
	@FXML
	private Button deleteMaintenanceNoteButton;
	
	@FXML
	private Button cancelButton;
	
	@FXML
	private TextField maintenanceTicketIdField;
	
	private static int maintenanceTicketId;
	
	@FXML
	private TextField maintenanceNoteIndexField;
	
	private static int maintenanceNoteIndex;
	
	
	
	
	
	// Method Generation
	@FXML
	void initialize() {
	}
	
	@FXML
	void cancelClicked(ActionEvent event) {
		AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
	
	@FXML
	void deleteMaintenanceNoteClicked(ActionEvent event) {
		AssetPlusFeatureSet7Controller.deleteMaintenanceNote(maintenanceTicketId,maintenanceNoteIndex);
		
		ViewUtils.callController("");
		AssetPlusFXMLView.getInstance().closePopUpWindow();
	}
	
	public static void setMaintenanceNoteIndex(int id, int index) {
		maintenanceTicketId = id;
		maintenanceNoteIndex = index;
	
	}
	
	
	
	
	
}
