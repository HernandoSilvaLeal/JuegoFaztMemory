package juegomemoria;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import juegomemoria.Board;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class AppController {
    
    @FXML private Button reiniciar;
    
    @FXML
    private void reiniciar(ActionEvent event) throws FileNotFoundException {
        System.out.println("Boton oprimido");
    }

    @FXML
    public GridPane gameMatrix;

    Board board = new Board();

    Cell firstCard = null;
    Cell secondCard = null;


    @FXML
    public void initialize() throws FileNotFoundException {
        board.populateMatrix();

        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                FileInputStream input = new FileInputStream(
                        "C:/Cursos/Java/Fundamentos/FastMemoryJavaFX/src/imagenes/question.png");
                Image image = new Image(input);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(90);
                imageView.setFitHeight(90);
                imageView.setUserData(row+","+col);
                imageView.setOnMouseClicked(event -> {
                    try {
                        cardListener(event);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                gameMatrix.add(imageView, row, col);
            }
        }

    }

    public void cardListener(MouseEvent event) throws FileNotFoundException {
        Node sourceComponent = (Node) event.getSource();
        String rowAndColumn = (String)sourceComponent.getUserData();

        int rowSelected = Integer.parseInt(rowAndColumn.split(",")[0]);
        int colSelected = Integer.parseInt(rowAndColumn.split(",")[1]);

        String image = board.board[rowSelected][colSelected].value;

        FileInputStream imageFile = new FileInputStream(
                "C:/Cursos/Java/Fundamentos/FastMemoryJavaFX/src/imagenes/"+image+".bmp");
        Image selectedImage = new Image(imageFile);
        ((ImageView)sourceComponent).setImage(selectedImage);
        checkIfMatchingPairWasFound(rowSelected,colSelected);

    }

    public void checkIfMatchingPairWasFound(int rowSelected, int colSelected) throws FileNotFoundException {

        if(firstCard == null){
            firstCard = board.board[rowSelected][colSelected];
        }else if(secondCard ==null){
            secondCard = board.board[rowSelected][colSelected];
        }else {
            if(firstCard.value.equals(secondCard.value)){
                //matching pair
                board.board[firstCard.row][firstCard.col].wasGuessed = true;
                board.board[secondCard.row][secondCard.col].wasGuessed = true;
            } else {
                int indexFirstCardInList = (firstCard.row * 4) + firstCard.col;

                FileInputStream questionFile = new FileInputStream(
                        "C:/Cursos/Java/Fundamentos/FastMemoryJavaFX/src/imagenes/question.png");
                Image questionImage = new Image(questionFile);
                ((ImageView)gameMatrix.getChildren().get(indexFirstCardInList)).setImage(questionImage);

                int indexSecondCardInList = (secondCard.row *4) + secondCard.col;
                ((ImageView)gameMatrix.getChildren().get(indexSecondCardInList)).setImage(questionImage);
            }

            firstCard= board.board[rowSelected][colSelected];
            secondCard = null;

        }
    }
}
