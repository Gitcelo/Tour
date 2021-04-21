package vidmot;

import Controller.ReservationController;
import Controller.TourController;
import Model.Tour;
import Model.TourDate;
import application.Parameters;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @FXML
    private TableColumn<Tour,String> cName;
    @FXML
    private TableColumn<Tour,String> cDesc;
    @FXML
    private TableColumn<Tour,Integer> cPrice;
    //We haven't implemented converting tags from their integer values to String values in the table
    @FXML
    private TableColumn<Tour,Integer> cDiff;
    @FXML
    private TableColumn<Tour,Integer> cLoc;
    @FXML
    private TableColumn<Tour,Boolean> cSafe;
    @FXML
    private TableColumn<Tour,String> cProv;
    @FXML
    private TableView<Tour> tourTable;
    @FXML
    private TableColumn<Tour, LocalDateTime> cDate;
    @FXML
    private TableColumn<Tour,Integer> cAvail;
    @FXML
    private TableView<TourDate> dateTable;
    @FXML
    private HBox searchBox;
    @FXML
    private HBox bookBox;
    @FXML
    private Label fxBooked;
    private static final String[] diff = { "Handicap", "Easy", "Medium", "Hard" };
    private static final String[] loc = { "Reykjavík", "Akureyri", "Ísafjörður", "Egilsstaðir" };
    private final Integer[] seats = { 1, 2, 3, 4, 5 };
    private TourController tc;
    private ReservationController rc;
    private ObservableList<Tour> ot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tc = new TourController();
        rc = new ReservationController();
        searchBox.getChildren().get(1).requestFocus();
        tourTable.setPlaceholder(new Label("Tours will appear here"));
        dateTable.setPlaceholder(new Label("Tour dates"));
        initComboBoxes();
        initTableColumns();
        initDatePickers();
    }

    private void initComboBoxes() {
        ObservableList<Node> searchList = searchBox.getChildren();
        ObservableList<String> diffList = FXCollections.observableArrayList(diff);
        ObservableList<String> locList = FXCollections.observableArrayList(loc);
        ObservableList<Integer> seatList = FXCollections.observableArrayList(seats);
        ((ComboBox<String>) searchList.get(0)).setItems(diffList);
        ((ComboBox<String>) searchList.get(3)).setItems(locList);
        ((ComboBox<Integer>) searchList.get(6)).setItems(seatList);
    }

    private void initTableColumns() {
        cName.setCellValueFactory(
                new PropertyValueFactory<Tour,String>("tourName")
        );
        cDesc.setCellValueFactory(
                new PropertyValueFactory<Tour,String>("description")
        );
        cPrice.setCellValueFactory(
                new PropertyValueFactory<Tour,Integer>("price")
        );
        cDiff.setCellValueFactory(
                new PropertyValueFactory<Tour,Integer>("difficulty")
        );
        cLoc.setCellValueFactory(
                new PropertyValueFactory<Tour,Integer>("location")
        );
        cSafe.setCellValueFactory(
                new PropertyValueFactory<Tour,Boolean>("childFriendly")
        );
        cProv.setCellValueFactory(
                new PropertyValueFactory<Tour,String>("providerName")
        );
        cDate.setCellValueFactory(
                new PropertyValueFactory<Tour,LocalDateTime>("date")
        );
        cAvail.setCellValueFactory(
                new PropertyValueFactory<Tour,Integer>("availableSeats")
        );
    }

    private void initDatePickers() {
        ObservableList<Node> searchList = searchBox.getChildren();
        ((DatePicker) searchList.get(4)).setValue(LocalDate.now().plusDays(1));
        ((DatePicker) searchList.get(5)).setValue(LocalDate.now().plusDays(2));
    }

    @FXML
    private void searchHandler() {
        tourTable.getItems().clear();
        dateTable.getItems().clear();
        ObservableList<Node> searchList = searchBox.getChildren();
        int difficulty = convertDifficulty(((ComboBox<String>) searchList.get(0)).getSelectionModel().getSelectedItem());
        int location = convertLocation(((ComboBox<String>) searchList.get(3)).getSelectionModel().getSelectedItem());
        int[] priceRange = {
                Integer.parseInt(((TextField) searchList.get(1)).getText()),
                Integer.parseInt(((TextField) searchList.get(2)).getText())
        };
        LocalDate[] dates = {
                ((DatePicker) searchList.get(4)).getValue(),
                ((DatePicker) searchList.get(5)).getValue()
        };
        int groupSize = ((ComboBox<Integer>) searchList.get(6)).getSelectionModel().getSelectedItem();
        Parameters p = new Parameters(
                difficulty,
                priceRange,
                groupSize,
                dates,
                0,
                location
        );
        ot = tc.searchTour(p);
        if(!ot.get(0).getValidTour()) return;
        tourTable.setItems(ot);
    }

    @FXML
    private void bookHandler() {
        ObservableList<Node> bookList = bookBox.getChildren();
        if(tourTable.getSelectionModel().getSelectedItem() != null &&
           dateTable.getSelectionModel().getSelectedItem() != null &&
           ((TextField) bookList.get(0)).getText()!="" &&
           ((TextField) bookList.get(1)).getText()!="") {

            int confirm = rc.confirmBooking(tourTable.getSelectionModel().getSelectedItem(),
                              dateTable.getSelectionModel().getSelectedItem(),
                    ((ComboBox<Integer>) searchBox.getChildren().get(6)).getSelectionModel().getSelectedItem(),
                    ((TextField) bookList.get(0)).getText(),
                    ((TextField) bookList.get(1)).getText());
            System.out.println(confirm);
            fxBooked.setVisible(true);
        }
    }

    @FXML
    private void clickedTourRowHandler() {
        if (tourTable.getSelectionModel().getSelectedItem() != null) {
            int idx  = tourTable.getSelectionModel().getSelectedIndex();
            dateTable.setItems(ot.get(idx).getDates());
        }
    }

    private int convertLocation(String difficulty) {
        switch (difficulty) {
            case "Reykjavík":
                return 1;
            case "Akureyri":
                return 2;
            case "Ísafjörður":
                return 3;
            case "Egilsstaðir":
                return 4;
            default:
                throw new IllegalArgumentException("Invalid location");
        }
    }

    private int convertDifficulty(String difficulty) {
        switch (difficulty) {
            case "Handicap":
                return 10;
            case "Easy":
                return 11;
            case "Medium":
                return 12;
            case "Hard":
                return 13;
            default:
                throw new IllegalArgumentException("Invalid location");
        }
    }
}
