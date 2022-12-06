package juegomemoria;

import java.util.Random;

public class Board {

    public Cell[][] board = new Cell[4][4];

    public void populateMatrix(){

        board = new Cell[4][4];//Se inicializa la matriz
        String[] images = {"1", "2", "3", "4", "5", "6", "7", "8"};//Se crea la matriz con las imagenes disponibles
        Random randomGenerator = new Random();
        
        //Se verifica que la matriz este llena 
        while(!isBoardFull()){
            int randomImageIndex = randomGenerator.nextInt(images.length);
            String randomImageSelected = images[randomImageIndex];

            int randomRow1 = randomGenerator.nextInt(4);
            int randomCol1 = randomGenerator.nextInt(4);
            while(board[randomRow1][randomCol1]!=null){
                randomRow1 = randomGenerator.nextInt(4);
                randomCol1 = randomGenerator.nextInt(4);
            }

            int randomRow2 = randomGenerator.nextInt(4);
            int randomCol2 = randomGenerator.nextInt(4);
            while((randomRow1 == randomRow2 && randomCol1 == randomCol2)
                    || board[randomRow2][randomCol2]!=null){
                randomRow2 = randomGenerator.nextInt(4);
                randomCol2 = randomGenerator.nextInt(4);
            }

            board[randomRow1][randomCol1] = new Cell(randomImageSelected,randomRow1, randomCol1);
            board[randomRow2][randomCol2] = new Cell(randomImageSelected,randomRow2, randomCol2);

        }

    }

    
    private boolean isBoardFull() {
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(board[i][j]==null){
                    return false;
                }
            }
        }
        return true;
    }
}