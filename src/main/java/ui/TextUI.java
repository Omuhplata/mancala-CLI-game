package ui;
import java.util.Scanner;

import mancala.Board;
import mancala.GameNotOverException;
import mancala.InvalidMoveException;
import mancala.MancalaGame;
import mancala.Player;

public class TextUI {
    private static MancalaGame game = new MancalaGame();
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Mancala\n");
        System.out.print("Enter Player 1's name: ");
        String player1Name = scanner.nextLine();
        System.out.print("Enter Player 2's name: ");
        String player2Name = scanner.nextLine();

        Player player1 = new Player(player1Name);
        Player player2 = new Player(player2Name);
        game.setBoard(new Board());
        game.setPlayers(player1, player2);
        game.setCurrentPlayer(player1);

        /* display the board and get input from user to make move */
        while(!(game.isGameOver())){
            System.out.println("Current Board:\n");
            game.displayBoard();
            System.out.println("It's " + game.getCurrentPlayer().getName() + "'s turn.");
            System.out.print("Choose a pit to move (0-5 for Player 1, 6-11 for Player 2): ");
            int pitIndex = scanner.nextInt();
            scanner.nextLine();

            try{
                game.move(pitIndex);
            }catch(InvalidMoveException e){
                System.out.println(e);
            }

        }

        /* print the final state of the board and result of the game */
        System.out.println("\nFinal board:");
        game.displayBoard();

        try{
            if(game.getWinner() == null){
                System.out.println("It's a draw!");
            }else{
                System.out.println(game.getWinner().getName() + " wins!");
            }
        }catch(GameNotOverException e){
            System.out.println(e);
        }

    }
}
