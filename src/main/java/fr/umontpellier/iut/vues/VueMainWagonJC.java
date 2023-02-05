package fr.umontpellier.iut.vues;

import java.io.IOException;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class VueMainWagonJC extends Stage {
    
    private ChangeListener<IJoueur> ListenerJoueurCourant;

    @FXML
    Scene scene;

    @FXML
    BorderPane panneau;

    @FXML
    private HBox cartesJoueurCourant;

    private VueCarteWagon carte;

    private VueJoueurCourant vjc;
    
    public VueMainWagonJC(VueJoueurCourant vjc){

        this.vjc=vjc;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VueMainJC.fxml"));
            // loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(panneau);
        setScene(scene);
    }

    public HBox getCartesJoueurCourant() {
        return cartesJoueurCourant;
    }

    public void setCartesJoueurCourant(HBox cartesJoueurCourant) {
        this.cartesJoueurCourant = cartesJoueurCourant;
    }
    

    public VueJoueurCourant getVjc() {
        return vjc;
    }

    public void creerBindings() {
       /* this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            /*@Override
            public void handle(MouseEvent mouseEvent) {

                /*for(int i=0; i<getCartesJoueurCourant().getChildren().size();i++){
                    if( getCartesJoueurCourant().getChildren().get(i).getClass().equals(VueCarteWagon.class)){
                    ((VueCarteWagon) getCartesJoueurCourant().getChildren().get(i)).creeBinding();
                    }

                } 
                
                

               // ((VueDuJeu) getVjc().getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie((ICouleurWagon) ((VueCarteWagon) 
               // getCartesJoueurCourant().getChildren().get(0)).getCouleurWagon());
              //         ((VueDuJeu) getVjc().getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie(v.getCouleurWagon());
                

                //((IJeu) getVjc().getScene()).getJeu().uneCarteWagonAEteChoisie((ICouleurWagon) getCartesJoueurCourant().getChildren().get(0));
                
                //((VueDuJeu) getScene().getRoot()).getJeu().uneCarteWagonAEteChoisie((ICouleurWagon) getCartesJoueurCourant().getChildren().get(1));
            }
        });
*/
        ListChangeListener<ICouleurWagon> listenerCarteJC = new ListChangeListener<ICouleurWagon>() {

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
            
        };
        
        ((VueDuJeu) getScene().getRoot()).getJeu().joueurCourantProperty().getValue().cartesWagonProperty().addListener(listenerCarteJC);
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

}
