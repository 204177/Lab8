package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.db.LetturaConnessioneDAO;
import it.polito.tdp.metrodeparis.db.LetturaFermataDAO;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.MetroModel;
import it.polito.tdp.metrodeparis.model.MetroModel2;
import it.polito.tdp.metrodeparis.model.RichFermata;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbPartenza;

    @FXML
    private ComboBox<String> cmbArrivo;

    @FXML
    private Button btnCalcola;

    @FXML
    private TextArea txtArea;

    @FXML
    void doCalcola(ActionEvent event) {
    	/*
    	txtArea.setText("");
    	
    	MetroModel model = new MetroModel();
    	List<Fermata> cammino = new LinkedList<Fermata>();
    	
    	if(cmbPartenza.getValue()==null || cmbArrivo.getValue()==null || cmbPartenza.getValue().compareTo(cmbArrivo.getValue())==0){
    		txtArea.appendText("Errore nell'inserimento della stazione di Arrivo o di Partenza");
    		return;
    	}
    	model.creaGrafo();
    	cammino.addAll(model.camminoMinimo(cmbPartenza.getValue(), cmbArrivo.getValue()));
    	txtArea.appendText("Percorso da "+cmbPartenza.getValue()+" a "+cmbArrivo.getValue()+":\n");
    	for(Fermata f : cammino){
    		if(f.getNome().compareTo(cmbPartenza.getValue())!=0 && f.getNome().compareTo(cmbArrivo.getValue())!=0)
    			txtArea.appendText(""+f.getNome()+"\n");
    	}
    	txtArea.appendText("Tempo stimato: "+arrotondamento(model.tempo)+" minuti");
    	*/
    	txtArea.setText("");
    	int cont = 0;
    	int idLinea = 0;
    	
    	MetroModel2 model = new MetroModel2();
    	List<RichFermata> cammino = new LinkedList<RichFermata>();
    	
    	if(cmbPartenza.getValue()==null || cmbArrivo.getValue()==null || cmbPartenza.getValue().compareTo(cmbArrivo.getValue())==0){
    		txtArea.appendText("Errore nell'inserimento della stazione di Arrivo o di Partenza");
    		return;
    	}
    	model.creaGrafo();
    	cammino.addAll(model.camminoMinimo(cmbPartenza.getValue(), cmbArrivo.getValue()));
    	txtArea.appendText("Percorso da "+cmbPartenza.getValue()+" a "+cmbArrivo.getValue()+":\n");
    	for(RichFermata f : cammino){
    		if(f.getFermata().getNome().compareTo(cmbPartenza.getValue())!=0 && f.getFermata().getNome().compareTo(cmbArrivo.getValue())!=0){
    			txtArea.appendText(""+f.getFermata().getNome()+"\n");
    			cont++;
    		}
    		if(cont>1)
    			if(idLinea != f.getIdLinea())
    				txtArea.appendText("__________Cambio metro nella stazione precedente__________\n");		
    		idLinea = f.getIdLinea();
    	}
    	txtArea.appendText("\n\n\nTempo stimato: "+arrotondamento((model.tempo+cont*30/60))+" minuti");
    }
    
    public static double arrotondamento(double x){
    	 x = Math.floor(x*1);
    	 x = x/1;
    	 return x;
    	}


    @FXML
    void initialize() {
        assert cmbPartenza != null : "fx:id=\"cmbPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert cmbArrivo != null : "fx:id=\"cmbArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        
        LetturaFermataDAO dao = new LetturaFermataDAO();
        List<Fermata> lista = new LinkedList<Fermata>();
        
        lista.addAll(dao.getFermate());
        
        for(Fermata f : lista){
        	cmbPartenza.getItems().add(f.getNome());
        	cmbArrivo.getItems().add(f.getNome());
        }
        	
        
    }
}
