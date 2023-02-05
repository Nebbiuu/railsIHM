package fr.umontpellier.iut.vues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

public class VueAJ extends HBox {

    private IJeu jeu;

    private ChangeListener<List<IJoueur>> ListenerVueAutresJoueurs;

    private List<Joueur> autresJoueurs;

    private List<VueAutresJoueurs> listeVueAJ;

    public List<VueAutresJoueurs> getListeVueAJ() {
        return listeVueAJ;
    }

    public void setListeVueAJ(List<VueAutresJoueurs> listeVueAJ) {
        this.listeVueAJ = listeVueAJ;
    }

    

    public VueAJ(IJeu jeu) {
        this.jeu=jeu;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VueVueAJ.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.getChildren().clear();

        listeVueAJ = new ArrayList<>();
        autresJoueurs = new ArrayList<>();
        for (int i = 1; i < jeu.getJoueurs().size(); i++) {
            if (!jeu.getJoueurs().get(i).equals(jeu.joueurCourantProperty().get())) {
                autresJoueurs.add(jeu.getJoueurs().get(i));
            }
        }
        

        
        for (int i = 0; i < autresJoueurs.size(); i++) {
            VueAutresJoueurs vueAutresJoueurs = new VueAutresJoueurs(autresJoueurs.get(i));
            this.getChildren().add(vueAutresJoueurs);
            listeVueAJ.add(vueAutresJoueurs);
        }
    }

    public void creerBindings() {
        ListenerVueAutresJoueurs = new ChangeListener<List<IJoueur>>() {
            @Override
            public void changed(ObservableValue<? extends List<IJoueur>> observable, List<IJoueur> oldValue,
                    List<IJoueur> newValue) {
                    for (VueAutresJoueurs vaj : listeVueAJ) {
                        vaj.creerBindings();
                    }
            }
        };
        ((VueDuJeu) getScene().getRoot()).getJeu().vueAJProperty().addListener(ListenerVueAutresJoueurs);

    }

    public Object getJeu() {
        return this.jeu;
    }

}
