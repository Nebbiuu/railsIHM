package fr.umontpellier.iut;

import fr.umontpellier.iut.rails.*;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import java.util.List;

public interface IJeu {

    ObjectProperty<String> instructionProperty();
    ObservableList<CouleurWagon> cartesWagonVisiblesProperty();
    ObservableList<Destination> destinationsInitialesProperty();
    ObjectProperty<IJoueur> joueurCourantProperty();
    ObjectProperty<List <IJoueur>> vueAJProperty();
    ObjectProperty<Joueur> autreJoueurProperty();

    List<Joueur> getJoueurs();
    List<? super Ville> getVilles();
    List<? super Route> getRoutes();

    void passerAEteChoisi();
    void uneCarteWagonAEtePiochee();
    void uneDestinationAEtePiochee();
    void uneVilleOuUneRouteAEteChoisie(String nom);
    void uneDestinationAEteChoisie(String destination); // parmi les destinations affichées
    void uneCarteWagonAEteChoisie(ICouleurWagon couleurWagon); // parmi les 5 cartes wagons affichées
    IJeu getJeu();








}