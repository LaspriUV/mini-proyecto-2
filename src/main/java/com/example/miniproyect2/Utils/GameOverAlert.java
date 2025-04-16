package com.example.miniproyect2.Utils;

import com.example.miniproyect2.controller.GameOverAlertController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOverAlert {
    public static boolean GameOverAlert(Stage ownerStage) {
        try {
            FXMLLoader loader = new FXMLLoader(GameOverAlert.class.getResource("/com/example/miniproyect2/gameOverAlert.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("¡Perdiste!");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(ownerStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            GameOverAlertController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();

            return controller.isAgainPlay();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}