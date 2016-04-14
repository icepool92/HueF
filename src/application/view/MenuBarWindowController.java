package application.view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuBarWindowController {

	@FXML
	private MenuItem createNew;

	@FXML
	private MenuItem open;

	@FXML
	private MenuItem save;

	@FXML
	private MenuItem export;

	@FXML
	private AnchorPane menuWindow;

	@FXML
	public void initialize(){
	}

	@FXML
	public boolean checkNewDisabled(){
		return createNew.isDisable();
	}

}
