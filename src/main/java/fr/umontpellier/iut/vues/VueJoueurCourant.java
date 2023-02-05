package fr.umontpellier.iut.vues;

import java.io.IOException;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Destination;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à
 * exécuter lorsque ce joueur change
 */
public class VueJoueurCourant extends VBox {

    
    private ChangeListener<IJoueur> ListenerJoueurCourant;
    private ListChangeListener<ICouleurWagon> listenerCarteJC;
    
    @FXML
    private HBox cartesJoueurCourant;

    @FXML
    private HBox basHBox;
    @FXML
    private Label nomJoueur;

    private ImageView avatar;

    // DROITE
    @FXML
    private VBox droiteVBox;
    @FXML
    private Label scoreJoueur;
    @FXML
    private HBox entoureWagGares;
    

    private VueCarteWagon carte;

    @FXML
    private ImageView imageGare;
    @FXML
    private ImageView imageWagon;
    @FXML
    private Label nbGares;
    @FXML
    private Label nbWagons;

    @FXML
    private HBox carteDestination;

    private VueDestination dest;

    private VueMainWagonJC main;

    private IJoueur joueurCourant;

    private int nbDeTour=0;

    public VueJoueurCourant() {
        
    
        main=new VueMainWagonJC(this);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VueJoueurCourant.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // Image imageWagonpng = new Image("imagesIcons8/icons8-container-box-train-transportation-facility---rail-logistic-service-96.png");
       // imageWagon.setImage(imageWagonpng);

       // Image imageGarepng = new Image("/imagesIcons8/icons8-town-hall-98.png");
       // imageGare.setImage(imageGarepng);
        

    }

    public HBox getCartesJoueurCourant() {
        return cartesJoueurCourant;
    }

    public void setCartesJoueurCourant(HBox cartesJoueurCourant) {
        this.cartesJoueurCourant = cartesJoueurCourant;
    }

    public void creerBindings() {
        ListenerJoueurCourant = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {
                    setStyle("-fx-background-color: "+t1.getCouleurAnglais()+";");
                    nomJoueur.textProperty().setValue(t1.getNom());
                    scoreJoueur.textProperty().setValue(String.valueOf(t1.getScore()+ "Pts"));

                    entoureWagGares.getChildren().clear();
                    nbWagons.textProperty().setValue(String.valueOf(t1.getNbWagons()));
                    nbGares.textProperty().setValue(String.valueOf(t1.getNbGares()));
                    entoureWagGares.getChildren().add(nbWagons);
                    entoureWagGares.getChildren().add(nbGares);

                    TextField cartes = new TextField(t1.getCartesWagon().toString());
                    
                    cartesJoueurCourant.getChildren().clear();

                    for (int i = 0; i < t1.getCartesWagon().size(); i++) {
                        carte = new VueCarteWagon(t1.getCartesWagon().get(i));
                        cartesJoueurCourant.getChildren().add(carte);
                        carte.creeBinding();
                    }


                    carteDestination.getChildren().clear();
                    for (int i = 0; i < t1.getDestinations().size(); i++) {
                        dest = new VueDestination(t1.getDestinations().get(i));
                        carteDestination.getChildren().add(dest);
                        dest.creerBindings();
                    }
                   nbDeTour++;
                   ((VueDuJeu) getScene().getRoot()).getNbTour().setText("Tour n° " + nbDeTour/ ((VueDuJeu) getScene().getRoot()).getJeu().getJoueurs().size());
                });
            }

        };

        

        /*listenerCarteJC = new ListChangeListener<ICouleurWagon>() {

            @Override
            public void onChanged(Change<? extends ICouleurWagon> change) {
                Platform.runLater(() -> {

                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (ICouleurWagon c : change.getAddedSubList()) {
                                VueCarteWagon carte = new VueCarteWagon(c);
                                carte.creeBinding();
                                cartesJoueurCourant.getChildren().add(carte);
                            }
                        } else if (change.wasRemoved()) {
                            for(int i=0; i<change.getRemovedSize(); i++){
                                cartesJoueurCourant.getChildren().remove(trouveCarteWagonVisible(change.getRemoved().get(i)));

                            }
                        }
                        
                    }
                });
                
            }

           
        };*/

        
        //((VueDuJeu) getScene().getRoot()).getJoueurCourant().cartesWagonProperty().addListener(listenerCarteJC);

        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().addListener(ListenerJoueurCourant);
    }

    public VueCarteWagon trouveCarteWagonVisible(ICouleurWagon c) {
        for (Node n : cartesJoueurCourant.getChildren()) {
            if(!n.getClass().equals(Label.class)){
                VueCarteWagon Vuecarte = (VueCarteWagon) n;
                if (Vuecarte.getCouleurWagon().equals(c)) {
                    return Vuecarte;
                }
            }
        }
        return null;
    }

    public VueDestination trouveCarteDestination(IDestination destination){
        for(Node node : carteDestination.getChildren()){
            VueDestination c = (VueDestination) node;
            if(c.getDestination().equals(destination)){
                return c;
            }
        }
        return null;
    }


    public Object getJeu() {
        return ((VueDuJeu) getScene().getRoot()).getJeu();
    }

   

}
