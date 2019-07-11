/**
 * Sample Skeleton for 'Ufo.fxml' Controller Class
 */

package it.polito.tdp.ufo;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AvvistamentiAnno;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class UfoController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<AvvistamentiAnno> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxStato"
    private ComboBox<String> boxStato; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleAnalizza(ActionEvent event) {
    	
    	String stato= boxStato.getValue();
    	
    	if (stato.length()== 0)
    	{
    		txtResult.appendText("ERRORE: seleziona stato\n");
    	}
    	
    	String ris= model.trova(stato);
    	
    	txtResult.appendText(ris);
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	
    	AvvistamentiAnno a= boxAnno.getValue();
    	
    	if(a==null)
    	{
    		txtResult.appendText("ERRORE: selezionare un valore\n");
    		return;
    	}
    	
    	Integer anno= a.getAnno();
    	
    	List<String> stati= model.creaGrafo(anno);

    	boxStato.getItems().addAll(stati);
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";
     
    }

    public void setModel(Model model) {
		this.model = model;
		   boxAnno.getItems().addAll(model.getTendina());
		
	}
}
