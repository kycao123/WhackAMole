package com.example.whackamole;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * This HolePane class represents one of the holes in the Whack-a-Mole game. There are three images
 * that each hole would display including an empty hole, mole popped out of hole, and mole going back inside
 * of hole. This class provides methods to update the states of the mole and to handle the whack action
 * in which it would display an "Ouch!!" over the image if the mole has been clicked on or whacked. This
 * text can be cleared with a separate method.
 */
public class HolePane extends StackPane {
   Image emptyImage;
   Image outImage;
   Image inImage;
   ImageView view;
   Text text;

   /**
    * This constructor initializes the three images representing the mole's states. It sets the
    * default image to the empty state and the text as empty.
    */
   public HolePane() {
      emptyImage = new Image("file:empty.png");
      outImage = new Image("file:out.png");
      inImage = new Image("file:in.png");

      view = new ImageView(emptyImage);

      text = new Text();
      text.setText("");

      getChildren().addAll(view, text);
   }

   /**
    * Sets the image to the empty image to represent a hole with no mole shown.
    */
   public void hide() {
      view.setImage(emptyImage);
   }

   /**
    * Sets the image to the out image in which the mole is popped out of the hole.
    */
   public void popOut() {
      view.setImage(outImage);
   }

   /**
    * Sets the image to the in image which shows the mole is going back into the hole.
    */
   public void popIn() {
      view.setImage(inImage);
   }

   /**
    * Returns true if the mole is out with the out image and also sets text to "Ouch!!" and calls popIn().
    * Returns false if the mole is not out of the hole.
    *
    * @return true if the mole is out and has been whacked or else false
    */
   public boolean whack() {
      if (view.getImage() == outImage) {
         text.setText("Ouch!!");
         popIn();
         return true;
      } else {
         return false;
      }
   }

   /**
    * Clears any text shown such as removing "Ouch!!".
    */
   public void clearText() {
      text.setText("");
   }
}