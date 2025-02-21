/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author axcee
 */
public class hoveer {
    private Button selectedButton = null;
      public void addHoverEffect(Button button) {
        // Hover effect
        button.setOnMouseEntered((MouseEvent event) -> {
            if (button != selectedButton) {
                button.setStyle("-fx-background-color: #E2DADA; -fx-text-fill: black;");
            }
        });

        // Mouse exit effect
        button.setOnMouseExited((MouseEvent event) -> {
            if (button != selectedButton) {
                button.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");
            }
        });

        // Click effect (select button)
        button.setOnAction((ActionEvent event) -> {
            if (selectedButton != null) {
                // Reset previous button style
                selectedButton.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");
            }
            selectedButton = button; // Set the new selected button
            button.setStyle("-fx-background-color: #E2DADA; -fx-text-fill: black;"); // Selected color
        });
    }
}
