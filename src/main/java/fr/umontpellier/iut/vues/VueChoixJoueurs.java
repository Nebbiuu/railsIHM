package fr.umontpellier.iut.vues;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir le
 * nombre et les noms des joueurs de la partie.
 *
 * Sa présentation graphique peut automatiquement être actualisée chaque fois
 * que le nombre de joueurs change.
 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à
 * démarrer la partie.
 */
public class VueChoixJoueurs extends Stage {

    private ObservableList<String> nomsJoueurs;

 
    private Scene scene;

    @FXML
    private BorderPane panneau;

    @FXML
    private Button commencer;

    @FXML
    private ChoiceBox nbJoueurs;
    @FXML
    private TextField nomDunJoueur;
    @FXML
    private TextField nomDunJoueur2;
    @FXML
    private TextField nomDunJoueur3;
    @FXML
    private TextField nomDunJoueur4;
    @FXML
    private Button valideNoms;
    @FXML
    private Button valideNb;

    List<String> listeTemporaire;
    Integer nombre;

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public List<String> getNomsJoueurs() {
        return nomsJoueurs;

    }

    EventHandler<ActionEvent> actionCommencer = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            nomsJoueurs.addAll(listeTemporaire);
            hide();
        }

    };
    EventHandler<ActionEvent> actionValideNombre = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            nombre = Integer.valueOf((String) nbJoueurs.getValue());
            if (nombre == 2) {
                nomDunJoueur3.setVisible(false);
                nomDunJoueur4.setVisible(false);
            }
            if (nombre == 3) {
                nomDunJoueur4.setVisible(false);
            }

        }

    };

    EventHandler<ActionEvent> actionValideNoms = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            listeTemporaire = new ArrayList<>();
            for (int i = 0; i < nombre-(nombre-1); i++) {
                if (valideNoms.isArmed()) {
                    listeTemporaire.add(nomDunJoueur.getText());
                    listeTemporaire.add(nomDunJoueur2.getText());
                    if (nomDunJoueur3.getText() != "") {
                        listeTemporaire.add(nomDunJoueur3.getText());
                    }
                    if (nomDunJoueur4.getText() != "") {
                        listeTemporaire.add(nomDunJoueur4.getText());
                    }
 
                    
                }

            }

        }

    };

    public VueChoixJoueurs() {
        nomsJoueurs = FXCollections.observableArrayList();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VueChoixJoueurs.fxml"));
            // loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        nbJoueurs.setItems(FXCollections.observableArrayList(
                "2", "3", "4"));

        scene = new Scene(panneau);
        valideNb.setOnAction(actionValideNombre);
        valideNoms.setOnAction(actionValideNoms);

        commencer.setOnAction(actionCommencer);

        setScene(scene);
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est
     * correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        /*
         * quandLesNomsDesJoueursSontDefinis = new ListChangeListener<String>() {
         * 
         * @Override
         * public void onChanged(Change<? extends String> c) {
         * Platform.runLater(() -> {
         * nomsJoueurs.add(nomDunJoueur.getText());
         * nomDunJoueur.clear();
         * });
         * }
         * };
         * this.nomsJoueurs.addListener(quandLesNomsDesJoueursSontDefinis);
         */
        nomsJoueursProperty().addListener(quandLesNomsDesJoueursSontDefinis);
    }

    /**
     * Définit l'action à exécuter lorsque le nombre de participants change
     */
    protected void setChangementDuNombreDeJoueursListener(ChangeListener<Integer> quandLeNombreDeJoueursChange) {

        /*
         * String nombre = nombreDeJoueurs.getText();
         * int n = Integer.valueOf(nombre);
         * System.out.println(n);
         * quandLeNombreDeJoueursChange = new ChangeListener<Integer>() {
         * 
         * @Override
         * public void changed(ObservableValue<? extends Integer> observable, Integer
         * oldValue, Integer newValue) {
         * Platform.runLater(() -> {
         * 
         * });
         * }
         * 
         * };
         * this.nomsJoueursProperty().addListener(quandLeNombreDeJoueursChange);
         */
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNombreDeJoueurs(); i++) {
            String name = getJoueurParNumero(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            } else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNombreDeJoueurs() {
        return getNomsJoueurs().size();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la
     * partie
     * 
     * @param playerNumber : le numéro du participant
     */
    protected String getJoueurParNumero(int playerNumber) {
        return getNomsJoueurs().get(playerNumber);
    }

}
