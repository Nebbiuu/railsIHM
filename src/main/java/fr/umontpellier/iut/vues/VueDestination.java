package fr.umontpellier.iut.vues;

import java.io.IOException;

import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.rails.Destination;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Destination.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par
 * l'utilisateur
 */
public class VueDestination extends Pane {

    private IDestination destination;

    private ChangeListener<IDestination> destinationListener;

    public VueDestination(IDestination destination) {
        this.destination = destination;
        String[] dest= destination.getNom().split(" ");
        
        Image i = new Image("images/missions/eu-"+dest[0].toLowerCase()+"-"+dest[2].toLowerCase()+".png");
        ImageView iv = new ImageView(i);
        iv.setFitHeight(90);
        iv.preserveRatioProperty().set(true);


        getChildren().add(iv);
        
       
    }

    public IDestination getDestination() {
        return destination;
    }

    public VueDestination() {
        Image dest = new Image("images/destinations.png");
        ImageView imageview = new ImageView(dest);
        getChildren().add(imageview);
    }

public void creerBindings(){
    this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            ((VueDuJeu) getScene().getRoot()).getJeu().uneDestinationAEteChoisie(destination.getNom());
        }
        
    });
}

    /*
     * destinationListener = new ChangeListener() {
     * 
     * @Override
     * public void changed(ObservableValue observable, Object oldValue, Object
     * newValue) {
     * 
     * 
     * }
     * 
     * 
     * 
     * };
     */

}
