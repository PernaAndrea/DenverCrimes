/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.sun.tools.javac.util.List;

import it.polito.tdp.crimes.model.Adiacenza;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxCategoria"
    private ComboBox<String> boxCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="boxArco"
    private ComboBox<Adiacenza> boxArco; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String categoria = this.boxCategoria.getValue();
    	Integer mese = this.boxMese.getValue();
    	
    	if(categoria == null || mese == null) {
    		txtResult.appendText("Seleziona i valori di input!");
    		return ;
    	}
    	
    	this.model.creaGrafo(categoria, mese);
    	ArrayList<Adiacenza> archi = (ArrayList<Adiacenza>) this.model.getArchi();
    	for(Adiacenza a : archi) {
    		txtResult.appendText(a.toString()+"\n");
    	}
    	
    	
    	//riempio la tendina per il punto 2
    	this.boxArco.getItems().addAll(archi);
    	
    }
    
    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	Adiacenza arco = this.boxArco.getValue();
    	if(arco == null) {
    		txtResult.appendText("Seleziona un arco!");
    		return ;
    	}
    	
    	LinkedList<String> percorso = (LinkedList<String>) this.model.trovaPercorso(arco.getV1(), arco.getV2());
    	txtResult.appendText("PERCORSO TRA " + arco.getV1() + " e " + arco.getV2() + ":\n\n");
    	for(String v : percorso) {
    		txtResult.appendText(v + "\n");	
    	}
    }
    /*
    @FXML
    void doCalcolaPercorso(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	String categoria = this.boxCategoria.getValue();
    	Integer mese = this.boxMese.getValue();
    	if(categoria==null || mese==null) {
    		txtResult.appendText("Seleziona i valori di input");
    		return;
    	}
    	this.model.creaGrafo(categoria, mese);
    	
    	for(Adiacenza a : this.model.getArchi()) {
    		txtResult.appendText(a.toString());
    	}
    }

*/
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxCategoria != null : "fx:id=\"boxCategoria\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxArco != null : "fx:id=\"boxArco\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxCategoria.getItems().addAll(model.getCategorie());
    	LinkedList<Integer> mesi = new LinkedList<>();
    	for(int i = 1;i<=12;i++) {
    		mesi.add(i);
    	}
    	this.boxMese.getItems().addAll(mesi);
    }
}
