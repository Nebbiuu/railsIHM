package fr.umontpellier.iut.vues;

import java.io.IOException;
import java.util.List;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Cette classe présente les éléments des joueurs autres que le joueur courant,
 * en cachant ceux que le joueur courant n'a pas à connaitre.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class VueAutresJoueurs extends HBox {

    private IJoueur joueur;
    
    private ChangeListener<IJoueur> ListenerAutresJoueurs;

    @FXML
    private VBox droiteVBox;

    @FXML
    private VBox gaucheVBox;
    @FXML 
    private Label nomJoueur;
    @FXML
    private Label scoreJoueur;

    @FXML
    private ImageView avatar;

    @FXML
    private Label nbGares;

    @FXML 
    private Label nbWagons;

    public IJoueur getJoueur() {
        return joueur;
    }
    
    public VueAutresJoueurs(IJoueur j){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/autreJoueurs.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }      

        joueur=j;

    }
    public void creerBindings() {
        ListenerAutresJoueurs = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {
                    setStyle("-fx-background-color: "+joueur.getCouleurAnglais()+";");
                    nomJoueur.textProperty().setValue(joueur.getNom());
                    scoreJoueur.textProperty().setValue(joueur.getScore()+" pts");
                    Image i = new Image("images/avatar-"+joueur.getCouleur().name().toUpperCase()+".png");
                    avatar.setImage(i);
                    nbWagons.textProperty().setValue(String.valueOf(joueur.getNbWagons()));
                    nbGares.textProperty().setValue(String.valueOf(joueur.getNbGares()));

                });
            }
        };
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(ListenerAutresJoueurs);

    }

    

    

}
