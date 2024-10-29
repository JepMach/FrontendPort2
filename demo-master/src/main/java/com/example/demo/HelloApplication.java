package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class HelloApplication extends Application {
    List<String> kurser;
    List<String> projekter;
    String valgteModul;
    int ectsSum;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bachelor program");

        //I disse comboboxes skal der stå navnene på de forskellige fag der kan vælges. her skal dataen fra SQL være.
        ComboBox<String> programComboBox = new ComboBox<>();
        programComboBox.getItems().addAll(getData("programNavn","BachelorProgrammer","programNavn='Bach1'"));
        programComboBox.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> subject1ComboBox = new ComboBox<>();
        subject1ComboBox.getItems().addAll(getData("modulNavn","Moduler","modulNavn='mod1' or modulNavn='Mod2' or modulNavn='Basis'"));
        subject1ComboBox.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> subject2ComboBox = new ComboBox<>();
        subject2ComboBox.getItems().addAll(getData("kursusNavn","Kurser","modulNavn='"+valgteModul+"'"));
        subject2ComboBox.setMaxWidth(Double.MAX_VALUE);

        ComboBox<String> electiveComboBox = new ComboBox<>();
        electiveComboBox.getItems().addAll(getData("projektNavn","Projekter","modulNavn='"+valgteModul+"'"));
        electiveComboBox.setMaxWidth(Double.MAX_VALUE);

        //lister til at se de fag der er blevet added.
        ListView<String> programListView = new ListView<>();

        ListView<String> modulListView = new ListView<>();

        ListView<String> kurserListView = new ListView<>();

        ListView<String> projekterListView = new ListView<>();

        // Labels der viser hvor mange ects der er valgt i en given collum
        Label programNumberLabel = new Label("ECTS:");
        Label modulNumberLabel = new Label("ECTS:");
        Label kurserNumberLabel = new Label("ECTS:");
        Label projekterNumberLabel = new Label("ECTS:");

        //labels der viser hvad der vælges i en given collum. ikke vigtigt for funktionalitet.
        Label programLabel = new Label("Program:");
        Label modulLabel = new Label("Modul:");
        Label kurserLabel = new Label("Kurser:");
        Label projekterLabel = new Label("Projekter:");

        //knapper til at add de forskellige fag til listerne.
        Button addProgramButton = new Button("Add");
        Button addModulButton = new Button("Add");
        Button addKursusButton = new Button("Add");
        Button addProjektButton = new Button("Add");

        //gør knapperne brede for at det passer sammen med resten af programmet. ikke vigtigt for funktionalitet.
        addProgramButton.setMaxWidth(Double.MAX_VALUE);
        addModulButton.setMaxWidth(Double.MAX_VALUE);
        addKursusButton.setMaxWidth(Double.MAX_VALUE);
        addProjektButton.setMaxWidth(Double.MAX_VALUE);

        //gør at knapperne virker. henter information fra combo boksene og sætter dem ind i listerne, samt ændre ects label. der er en for hver collum
        addProgramButton.setOnAction(e -> {
            String selectedProgram = programComboBox.getSelectionModel().getSelectedItem();
            if (selectedProgram != null) {
                programListView.getItems().add(selectedProgram);
                programNumberLabel.setText("ECTS:" + getEctsSUM());
            }
        });

        addModulButton.setOnAction(e -> {
            String selectedSubject1 = subject1ComboBox.getSelectionModel().getSelectedItem();
            if (selectedSubject1 != null) {
                modulListView.getItems().add(selectedSubject1);
                modulNumberLabel.setText("ECTS:" + getModulSUM());
            }
        });

        addKursusButton.setOnAction(e -> {
            String selectedSubject2 = subject2ComboBox.getSelectionModel().getSelectedItem();
            if (selectedSubject2 != null) {
                kurserListView.getItems().add(selectedSubject2);
                kurserNumberLabel.setText("ECTS:" + getKursusEcts());
            }
        });

        addProjektButton.setOnAction(e -> {
            String selectedElective = electiveComboBox.getSelectionModel().getSelectedItem();
            if (selectedElective != null) {
                projekterListView.getItems().add(selectedElective);
                projekterNumberLabel.setText("ECTS:" + getProjektEcts());
            }
        });

        // tilføjer de forskellige dele i collums. delene bliver tilføjer fra toppen.
        HBox topRow = new HBox(10,
                new VBox(5,programLabel, programComboBox, addProgramButton, programListView, programNumberLabel),
                new VBox(5,modulLabel, subject1ComboBox, addModulButton, modulListView, modulNumberLabel),
                new VBox(5, kurserLabel, subject2ComboBox, addKursusButton, kurserListView, kurserNumberLabel),
                new VBox(5, projekterLabel, electiveComboBox, addProjektButton, projekterListView, projekterNumberLabel)
        );

        //tilføjer det til scenen og giver lidt ekstra spacing.
        VBox mainLayout = new VBox(10, topRow);
        mainLayout.setPadding(new Insets(10));


        Scene scene = new Scene(mainLayout, 800, 450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
