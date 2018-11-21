/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_functionality;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Daniel
 */
public class WorldOfZuul {
      

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Game.class, args);
        Game game = new Game();
    }
    
}
