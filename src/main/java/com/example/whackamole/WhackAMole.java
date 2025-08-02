package com.example.whackamole;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This WhackAMole class represents a simple Whack-a-Mole game using event-driven programming.
 * The program displays a 3x3 grid of holes and the game can started or stopped using the
 * respective buttons. Each hole is represented by a HolePane and a mole would pop in and out
 * of the holes randomly. If the mole is clicked on while popped out, it will be pushed back
 * into the hole and the game will display an "Ouch!!" text.The current mole location is tracked
 * using a location index (0 to 8), and mole behavior is scheduled using a Timer and a TimerTask
 * (MolePopper).
 */
public class WhackAMole extends Application {
   private HolePane[][] holePanes = new HolePane[3][3];
   private int locationIndex = -1;
   private Timer timer;
   private MolePopper molePopper;

   /**
    * Sets up and displays the 3x3 grid of holes with start and stop buttons at the bottom. It also
    * sets up the event handlers. When Start is clicked, all holes and text would be cleared in case, and
    * a timer begins while moles pop up at different holes randomly. When the Stop is clicked, the timer
    * is cancelled. Each HolePane has an onMousePressed() handler that checks if the mole was whacked.
    * If so, it retrieves the source and cancels the timer to stop the game.
    *
    * @param stage the primary stage for displaying the application
    */
   @Override
   public void start(Stage stage) {
      // create start and stop buttons
      Button startButton = new Button("Start");
      Button stopButton = new Button("Stop");

      // create HBox pane and put in buttons
      HBox hBox = new HBox(10);
      hBox.setAlignment(Pos.CENTER);
      hBox.getChildren().addAll(startButton, stopButton);

      // create a grid pane and put in the 9 HolePane objects in a 3x3 configuration
      GridPane gridPane = new GridPane();
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            HolePane holePane = new HolePane();
            holePanes[i][j] = holePane;
            gridPane.add(holePane, j, i);
         }
      }

      // create a border pane and set the hBox pane at the bottom and gridPane at the center
      BorderPane borderPane = new BorderPane();
      borderPane.setBottom(hBox);
      borderPane.setCenter(gridPane);

      // set the handlers, start will clear all holes and start popping up moles
      startButton.setOnAction(e -> {
         clearAllHoles();

         molePopper = new MolePopper();
         timer = new Timer();
         timer.schedule(molePopper, 0, 600);
      });

      // stop will cancel the timer
      stopButton.setOnAction(e -> timer.cancel());

      // on each hole pressed, will handle whack action and cancel timer if whack() returns true
      for (HolePane[] row : holePanes) {
         for (HolePane holePane : row) {
            holePane.setOnMousePressed(e -> {
               HolePane source = (HolePane) e.getSource();
               if (source.whack()) {
                  timer.cancel();
               }
            });
         }
      }

      // create a scene that is 561 (width) x 400 (height) and show the scene
      Scene scene = new Scene(borderPane, 561, 400);
      stage.setTitle("Whack a Mole");
      stage.setScene(scene);
      stage.show();
   }

   /**
    * Clears all holes and removing any "Ouch!!" text from when mole was whacked. This
    * is usually called at start of game.
    */
   private void clearAllHoles() {
      for (HolePane[] row : holePanes) {
         for (HolePane holePane : row) {
            holePane.clearText();
            holePane.hide();
         }
      }
   }

   /**
    * Inner class called MolePopper which extends TimerTask that hides the mole in the current
    * location if exists. Then it randomly selects a new mole location from 0 to 8 for the mole to pop out from.
    */
   class MolePopper extends TimerTask {
      /**
       * Hides the current mole and pops out a new mole in a random hole.
       */
      @Override
      public void run() {
         // find current mole location if exists and hide mole
         if (locationIndex != -1) {
            int index = 0;
            for (int i = 0; i < 3; i++) {
               for (int j = 0; j < 3; j++) {
                  if (index == locationIndex) {
                     holePanes[i][j].hide();
                  }
                  index++;
               }
            }
         }

         // generate a random number from 0 to 8 for the new mole location
         Random rand = new Random();
         locationIndex = rand.nextInt(9);

         // find new mole location and pop out mole
         int index = 0;
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
               if (index == locationIndex) {
                  holePanes[i][j].popOut();
               }
               index++;
            }
         }
      }
   }
}