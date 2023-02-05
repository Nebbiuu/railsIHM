package fr.umontpellier.iut.vues;

import java.io.IOException;
import java.util.Locale;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.rails.Jeu;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * Cette classe représente la vue d'une carte Wagon.
 *
 * On y définit le listener à exécuter lorsque cette carte a été choisie par l'utilisateur
 */
public class VueCarteWagon extends Pane {

    private ICouleurWagon couleurWagon;

    public VueCarteWagon(ICouleurWagon couleurWagon) {
        this.couleurWagon = couleurWagon;
        Image carte = new Image("images/cartesWagons/carte-wagon-"+couleurWagon.toString().toUpperCase(Locale.ROOT)+".png");
        ImageView imageview = new ImageView(carte);
        imageview.setFitHeight(74);
        imageview.preserveRatioProperty().set(true);
        getChildren().add(imageview);
        
    }

    public VueCarteWagon(){
        Image pioche = new Image("images/wagons.png");
        ImageView iv = new ImageView(pioche);
        getChildren().add(iv);
    }

    public ICouleurWagon getCouleurWagon() {
        return couleurWagon;
    }

    public void creeBinding(){

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                ((VueDuJeu) getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie(couleurWagon);
                
            }
        });

    }
    

    

}
