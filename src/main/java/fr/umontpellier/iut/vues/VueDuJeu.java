package fr.umontpellier.iut.vues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.ICouleurWagon;
import fr.umontpellier.iut.IDestination;
import fr.umontpellier.iut.IJeu;
import fr.umontpellier.iut.IJoueur;
import fr.umontpellier.iut.rails.CouleurWagon;
import fr.umontpellier.iut.rails.Destination;
import fr.umontpellier.iut.rails.Joueur;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de
 * l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class VueDuJeu extends BorderPane {

    private IJeu jeu;

    private VuePlateau plateau;

    private VueDestination destination;

    private VueCarteWagon pioche;

    @FXML
    private VBox allLeft;
    @FXML
    private HBox hautDeLeft;
    @FXML
    private ImageView refresh;
    @FXML
    private ImageView ivDestinationPioche;
    @FXML
    private ImageView ivWagonPioche;
    @FXML
    private Label nbTour;
    private int tourdejeu = 1;
    @FXML
    private VBox tourEtPioches;

    @FXML
    private VueJoueurCourant joueurCourant; // basDeLeft

    private VueMainWagonJC main;

    @FXML
    private VBox allRight;
    @FXML
    private HBox basDeRight;
    @FXML
    private VueAJ vueAJ; // bas de Right
    private HBox vueDesVues;

    @FXML
    private HBox boxPasser;
    @FXML
    private Button passer;
    @FXML
    private VueAutresJoueurs VueAutresJoueurs;

    private ListChangeListener<IDestination> ListenerDestination;

    @FXML
    private HBox listeDestination;

    private ListChangeListener<ICouleurWagon> listenerCartesVisibles;
    @FXML
    private VBox cartesVisibles;

    private Label instruction;

    private ListChangeListener<IJoueur> ListenerVueAutresJoueurs;

    private List<IJoueur> autresjoueeurs;

    public VueDuJeu(IJeu jeu) {

        setStyle("-fx-background-color:grey");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/panneauNew.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.jeu = jeu;
        joueurCourant = new VueJoueurCourant();
        // PARTIE DE DROITE
        allRight.getChildren().clear();
        basDeRight.getChildren().clear();

        vueAJ = new VueAJ(this.jeu);

        vueDesVues = new HBox();
        autresjoueeurs= new ArrayList<>();

        autresjoueeurs.addAll(jeu.getJoueurs());
        for(IJoueur j : autresjoueeurs){
            System.out.println(j.getNom());
        }
        autresjoueeurs.remove(getJoueurCourant());
        for(IJoueur j : autresjoueeurs){
            System.out.println("apres"+j.getNom());
        }

        System.out.println(autresjoueeurs.size());
        for(int i=0; i<autresjoueeurs.size(); i++){
            VueAutresJoueurs vueAutreJ = new VueAutresJoueurs(autresjoueeurs.get(i));
            vueDesVues.getChildren().add(vueAutreJ);
        }

        basDeRight.getChildren().add(vueDesVues);

        boxPasser.getChildren().clear();
        boxPasser.getChildren().add(passer);
        basDeRight.getChildren().add(boxPasser);

        plateau = new VuePlateau();

        allRight.getChildren().add(plateau);
        listeDestination = new HBox();

        listeDestination.setAlignment(Pos.BOTTOM_CENTER);
        allRight.getChildren().add(listeDestination);
        allRight.getChildren().add(basDeRight);

        this.setRight(allRight);

        // PARTIE DE GAUCHE
        allLeft.getChildren().clear();
        hautDeLeft.getChildren().clear();

        tourEtPioches.getChildren().clear();
        Image rf = new Image("/imagesIcons8/icons8-refresh-120.png");
        refresh.setImage(rf);
        Image imageDestin = new Image("images/destinations.png");
        ivDestinationPioche.setImage(imageDestin);
        Image imagePioche = new Image("images/wagons.png");
        ivWagonPioche.setImage(imagePioche);
        Label nomPiocheWagon = new Label("Pioche");
        Label nomPiochedestination = new Label("Pioche Destinations");
        instruction = new Label("");

        nbTour=new Label();
        nbTour.setText("Tour n° "+tourdejeu);

        tourEtPioches.getChildren().add(refresh);
        tourEtPioches.getChildren().add(nbTour);
        tourEtPioches.getChildren().add(nomPiocheWagon);
        tourEtPioches.getChildren().add(ivWagonPioche);
        tourEtPioches.getChildren().add(nomPiochedestination);
        tourEtPioches.getChildren().add(ivDestinationPioche);
        tourEtPioches.getChildren().add(instruction);

        hautDeLeft.getChildren().add(tourEtPioches);
        hautDeLeft.getChildren().add(cartesVisibles);

        

        main = new VueMainWagonJC(joueurCourant);

        allLeft.getChildren().add(hautDeLeft);

        joueurCourant.setStyle("-fx-border-color: black; -fx-border-width:4");
        allLeft.getChildren().add(joueurCourant);

        this.setLeft(allLeft);

    }

    public Label getNbTour() {
        return nbTour;
    }


    public IJeu getJeu() {
        return jeu;
    }

    public VueAJ getVueAJ() {
        return vueAJ;
    }

    public void creerBindings() {

        ListenerVueAutresJoueurs = new ListChangeListener<IJoueur>() {
            @Override
            public void onChanged(Change<? extends IJoueur> change) {
                Platform.runLater(() -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (IJoueur d : change.getAddedSubList()) {
                                System.out.println(d.getNom() + " a été ajouté");
                                getChildren().add(new Label(d.getNom()));
                                VueAutresJoueurs v = new VueAutresJoueurs(d);
                                vueDesVues.getChildren().addAll(v);
                                v.creerBindings();
                            }
                        } else if (change.wasRemoved()) {
                            for (IJoueur d : change.getRemoved()) {
                                vueDesVues.getChildren().remove(d);
                                System.out.println(d.getNom() + " a été enlevé");

                            }

                        }
                    }
                });
            }
        };
        //jeu.getJoueurs().addListener(ListenerVueAutresJoueurs);


        ListenerDestination = new ListChangeListener<IDestination>() {
            @Override
            public void onChanged(Change<? extends IDestination> change) {
                Platform.runLater(() -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (IDestination d : change.getAddedSubList()) {
                                System.out.println(d.getNom() + " a été ajouté");
                                getChildren().add(new Label(d.getNom()));
                                VueDestination v = new VueDestination(d);
                                listeDestination.getChildren().addAll(v);
                                v.creerBindings();
                            }
                        } else if (change.wasRemoved()) {
                            for (IDestination d : change.getRemoved()) {
                                listeDestination.getChildren().remove(trouveLabelDestination(d));
                                listeDestination.getChildren().remove(trouveDestination(d));
                                System.out.println(d.getNom() + " a été enlevé");

                            }

                        }
                        if (listeDestination.getChildren().isEmpty()) {
                            listeDestination.setMinHeight(0);
                            listeDestination.setMaxHeight(0);
                            listeDestination.setVisible(false);
                            vueDesVues.setVisible(true);
                        } else {
                            listeDestination.setMinHeight(100);
                            listeDestination.setVisible(true);
                            vueDesVues.setVisible(false);
                        }

                    }
                });
            }
        };
        jeu.destinationsInitialesProperty().addListener(ListenerDestination);

        listenerCartesVisibles = new ListChangeListener<ICouleurWagon>() {

            @Override
            public void onChanged(Change<? extends ICouleurWagon> change) {
                Platform.runLater(() -> {

                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (ICouleurWagon c : change.getAddedSubList()) {
                                VueCarteWagon carte = new VueCarteWagon(c);
                                carte.creeBinding();
                                cartesVisibles.getChildren().add(carte);
                            }
                        } else if (change.wasRemoved()) {
                            for (int i = 0; i < change.getRemovedSize(); i++) {
                                cartesVisibles.getChildren()
                                        .remove(trouveCarteWagonVisible(change.getRemoved().get(i)));
                                cartesVisibles.getChildren().remove(new VueCarteWagon(change.getRemoved().get(i)));
                            }
                        }

                    }
                });
            }
        };
        
        //jeu.vueAJProperty().addListener(ListenerVueAutresJoueurs);

        jeu.cartesWagonVisiblesProperty().addListener(listenerCartesVisibles);
        
        instruction.textProperty().bind(jeu.instructionProperty());
       

        joueurCourant.creerBindings();
        //vueAJ.creerBindings();
        plateau.creerBindings();

        for (Node vaj : vueDesVues.getChildren()) {
            ((VueAutresJoueurs) vaj).creerBindings();
        }
    }

    public void passerTour() {
        jeu.passerAEteChoisi();
    }

    public void piocherDestination() {
        jeu.uneDestinationAEtePiochee();

    }

    public void piocherCarteWagon() {
        jeu.uneCarteWagonAEtePiochee();
    }

    public VueDestination trouveDestination(IDestination des) {
        for (Node n : listeDestination.getChildren()) {
            if (!n.getClass().equals(Label.class)) {
                VueDestination Vuecarte = (VueDestination) n;
                if (Vuecarte.getDestination().equals((des))) {
                    return Vuecarte;
                }
            }
        }
        return null;
    }

    public Label trouveLabelDestination(IDestination des) {
        for (Node n : listeDestination.getChildren()) {
            if (!n.getClass().equals(VueDestination.class)) {
                Label nom = (Label) n;
                if (nom.getText().equals((des.getNom()))) {
                    return nom;
                }
            }
        }
        return null;
    }

    public VueCarteWagon trouveCarteWagonVisible(ICouleurWagon c) {
        for (Node n : cartesVisibles.getChildren()) {
            if (!n.getClass().equals(Label.class)) {
                VueCarteWagon Vuecarte = (VueCarteWagon) n;
                if (Vuecarte.getCouleurWagon().equals(c)) {
                    return Vuecarte;
                }
            }
        }
        return null;
    }

    public IJoueur getJoueurCourant() {
        return this.jeu.joueurCourantProperty().getValue();
    }

}