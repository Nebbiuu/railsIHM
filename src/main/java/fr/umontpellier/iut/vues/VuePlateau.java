package fr.umontpellier.iut.vues;

import fr.umontpellier.iut.IJoueur;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.io.IOException;

import fr.umontpellier.iut.IRoute;
import fr.umontpellier.iut.IVille;
import fr.umontpellier.iut.rails.Joueur;
import fr.umontpellier.iut.rails.Ville;
import javafx.scene.shape.Rectangle;

/**
 * Cette classe présente les routes et les villes sur le plateau.
 * <p>
 * On y définit le listener à exécuter lorsque qu'un élément du plateau a été
 * choisi par l'utilisateur
 * ainsi que les bindings qui mettront à jour le plateau après la prise d'une
 * route ou d'une ville par un joueur
 */
public class VuePlateau extends Pane {

    @FXML
    private Group villes;
    @FXML
    private Group routes;

    ChangeListener<IJoueur> listenerVille;
    ChangeListener<IJoueur> listenerRoute;

    public VuePlateau() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/plateauGaetan.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * @FXML
     * public void choixRouteOuVille(MouseEvent event) {
     * try {
     * Group g = (Group) event.getSource();
     * ((VueDuJeu)
     * getScene().getRoot()).getJeu().uneVilleOuUneRouteAEteChoisie(g.getId());
     * } catch (RuntimeException r) {
     * Circle c = (Circle) event.getSource();
     * ((VueDuJeu)
     * getScene().getRoot()).getJeu().uneVilleOuUneRouteAEteChoisie(c.getId());
     * }
     * }
     */
    @FXML
    public void choixRouteOuVille(Event event) {

        String selection = ((Node) event.getSource()).getId();
        ((VueDuJeu) getScene().getRoot()).getJeu().uneVilleOuUneRouteAEteChoisie(selection);

    }

    public void creerBindings() {
        listenerVille = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {

                    for (Node ville : villes.getChildren()) {
                        ville = (Circle) ville;

                        for (Object villeJeu : ((VueDuJeu) getScene().getRoot()).getJeu().getVilles()) {

                            villeJeu = (IVille) villeJeu;
                            if (ville.getId().equals(((IVille) villeJeu).getNom())) {

                                if (((IVille) villeJeu).getProprietaire() != null
                                        && ((IVille) villeJeu).getProprietaire().equals(t1)) {

                                    ((Circle) ville).setStyle("-fx-fill:" + t1.getCouleurAnglais());
                                    ((Circle) ville).setVisible(true);

                                }
                            }
                        }

                    }

                });
            }

        };

        listenerRoute = new ChangeListener<IJoueur>() {
            @Override
            public void changed(ObservableValue<? extends IJoueur> observableValue, IJoueur iJoueur, IJoueur t1) {
                Platform.runLater(() -> {

                    for (Node route : routes.getChildren()) {

                        for (Node routes2 : ((Group) route).getChildren()) {
                        routes2 = (Rectangle) routes2;

                            for (Object routeJeu : ((VueDuJeu) getScene().getRoot()).getJeu().getRoutes()) {

                                routeJeu = (IRoute) routeJeu;
                                if (route.getId().equals(((IRoute) routeJeu).getNom())) {

                                    if (((IRoute) routeJeu).getProprietaire() != null
                                            && ((IRoute) routeJeu).getProprietaire().equals(t1)) {

                                        ((Rectangle) routes2).setStyle("-fx-fill:" + t1.getCouleurAnglais());
                                        ((Rectangle) routes2).setVisible(true);

                                    }
                                }
                            }

                        }
                    }

                });
            }

        };

        for (Object ville : ((VueDuJeu) getScene().getRoot()).getJeu().getVilles()) {
            ville = (IVille) ville;
            ((IVille) ville).proprietaireProperty().addListener(listenerVille);
        }

        for (Object route : ((VueDuJeu) getScene().getRoot()).getJeu().getRoutes()){
            route = (IRoute) route;
            ((IRoute) route).proprietaireProperty().addListener(listenerRoute);
        }
        // VILLES
        /*
         * for (int i = 0; i < villes.getChildren().size(); i++) {
         * Circle c = (Circle) villes.getChildren().get(i);
         * StringProperty nom = new SimpleStringProperty(c.getId()); // nom du cercle
         * fxml
         * for (Object v : ((VueDuJeu) getScene().getRoot()).getJeu().getVilles()) { //
         * nom des villes du jeu
         * if (nom.get() == ((IVille) v).getNom()) {
         * 
         * StringBinding nomBinding = new StringBinding() {
         * 
         * {
         * super.bind(((IVille) v).proprietaireProperty());
         * }
         * 
         * @Override
         * protected String computeValue() {
         * return "-fx-fill:" + ((IVille)
         * v).proprietaireProperty().get().getCouleur().name();
         * }
         * 
         * };
         * c.styleProperty().bind(nomBinding);
         * }
         * }
         * }
         * 
         * //ROUTES
         * for (int j = 0; j < routes.getChildren().size(); j++) {
         * Group g = (Group) routes.getChildren().get(j);
         * StringProperty nomRoute = new SimpleStringProperty(g.getId());// nom de ma
         * route fxml
         * for (Object r : ((VueDuJeu) getScene().getRoot()).getJeu().getRoutes()) {//
         * nom des routes du jeu
         * if (nomRoute.get() == ((IRoute) r).toString()) {
         * for (Node rect : g.getChildren()){
         * 
         * 
         * StringBinding nomBinding2 = new StringBinding() {
         * 
         * @Override
         * protected String computeValue() {
         * return "-fx-fill:" + ((IRoute)
         * r).proprietaireProperty().get().getCouleur().name();
         * }
         * };
         * g.styleProperty().bind(nomBinding2);
         * }
         * }
         * }
         * }
         * }
         */

    }
}
