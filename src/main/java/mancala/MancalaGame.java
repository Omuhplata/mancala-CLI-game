package mancala;
import java.util.ArrayList;

public class MancalaGame {
    private Board board;
    private ArrayList<Player> playerList = new ArrayList<>();
    private Player currentPlayer;
    private int currentPlayerIndex;

    public Board getBoard(){
        return board;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException{
        if(pitNum < 0 || pitNum >= getBoard().getPits().size()){
            throw new PitNotFoundException("getNumStones: Pit " + pitNum + " does not exist.\n");
        }      
        return getBoard().getPits().get(pitNum).getStoneCount();
    }

    public ArrayList<Player> getPlayers(){
        return playerList;
    }

    public int getStoreCount(Player player) throws NoSuchPlayerException{
        if(!getPlayers().contains(player)){
            throw new NoSuchPlayerException("getStoreCount: That player does not exist.\n");
        }
        return player.getStoreCount();
    }

    public Player getWinner() throws GameNotOverException{
        if(!isGameOver()){
            throw new GameNotOverException("getWinner: Game is not over yet.\n");
        }

        Player winner = null;
        if(board.getStores().get(0).getTotalStones() > board.getStores().get(1).getTotalStones()){
            winner = getPlayers().get(0);
        }else if(board.getStores().get(1).getTotalStones() > board.getStores().get(0).getTotalStones()){
            winner = getPlayers().get(1);
        }
        /* else it's a draw; winner points to null */
        return winner;
    }

    public boolean isGameOver(){
        try{
            if(getBoard().isSideEmpty(1) || getBoard().isSideEmpty(11)){
                return true;
            }
        }catch(PitNotFoundException e){
            System.out.println(e);
        }

        return false;
    }

    public int move(int startPit) throws InvalidMoveException{
        int finalPit = 0;
        boolean freeTurn = false;

        if(startPit < 0 || startPit >= getBoard().getPits().size()){
            throw new InvalidMoveException("move: Pit " + startPit + " doesn't exist.\n");
        }else if(!isOwnSidePit(startPit, currentPlayer)){
            throw new InvalidMoveException("move: Please choose a pit on your side.\n");
        }
        
        try{
            finalPit = getBoard().moveStones(startPit, currentPlayer);
        }catch(InvalidMoveException e){
            System.out.println(e);
            freeTurn = true; // redo current player's turn
        }

        /* player lands in their own store */
        if(finalPit == -1){
            freeTurn = true;
        }

        /* player lands in empty pit on their side */
        try{
            if(isOwnSidePit(finalPit, currentPlayer) && (getBoard().getPits().get(finalPit).getStoneCount() == 1)){
                getBoard().captureStones(finalPit);
            }
        }catch(PitNotFoundException e){
            System.out.println(e);
        }

        /* change the current player */
        if(!freeTurn){
            if(startPit >= 0 && startPit <= 5){
                setCurrentPlayer(getPlayers().get(1));
            }else if(startPit >= 6 && startPit <= 11){
                setCurrentPlayer(getPlayers().get(0));
            }
        }

        return 1;
    }

    public void setBoard(Board theBoard){
        board = theBoard;
        getBoard().initializeBoard();
    }

    public void setCurrentPlayer(Player player){
        currentPlayer = player;
    }

    public void setPlayers(Player onePlayer, Player twoPlayer){
        getPlayers().add(onePlayer);
        getPlayers().add(twoPlayer);
        getBoard().registerPlayers(onePlayer, twoPlayer);
    }

    public void startNewGame(){
        getBoard().resetBoard();
    }

    boolean isOwnSidePit(int pitIndex, Player player){
        if((pitIndex >= 0 && pitIndex <= 5) && (player == getPlayers().get(0))){
            return true;
        }else if((pitIndex >= 6 && pitIndex <= 11) && (player == getPlayers().get(1))){
            return true;
        }
        return false;
    }

    public void displayBoard(){
        System.out.println("\t\t______ " + getPlayers().get(1).getName() + " ______");
        System.out.println("\n\tPits:    11   10    9    8    7    6");
        System.out.print("\t\t");
        for(int i = 11; i > 5; i--){
            System.out.print("[" + getBoard().getPits().get(i).getStoneCount() + "]  ");
        }
        System.out.println();

        System.out.println("\t["+ getBoard().getStores().get(1).getTotalStones() +"]\t\t\t\t\t[" + getBoard().getStores().get(0).getTotalStones() + "]");
        

        System.out.print("\t\t");
        for(int i = 0; i < 6; i++){
            System.out.print("[" + getBoard().getPits().get(i).getStoneCount() + "]  ");
        }
        System.out.println();

        System.out.println("\tPits:    0    1    2    3    4    5\n");
        System.out.println("\t\t______ " + getPlayers().get(0).getName() + " ______\n");
    }

    @Override
    public String toString(){
        return "MancalaGame toString() stub.";
    }

}
