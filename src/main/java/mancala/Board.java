package mancala;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private ArrayList<Pit> pitList;
    private ArrayList<Store> storeList;

    public Board(){
        setUpPits();
        setUpStores();
    }

    public int captureStones(int stoppingPoint) throws PitNotFoundException{
        if(stoppingPoint < 0 || stoppingPoint > getPits().size()){
            throw new PitNotFoundException("captureStones: Cannot capture from pit " + stoppingPoint + "\n");
        }

        Store currentPlayerStore = getStores().get(0);
        int pitToCapture = 0;
        int numStonesCaptured = 0;
        /* determine pit opposite to stoppingPoint */
        if(stoppingPoint >= 0 && stoppingPoint <= 5){
            pitToCapture = 11 - stoppingPoint;
            currentPlayerStore = getStores().get(0);
        } else if(stoppingPoint >= 6 && stoppingPoint <= 11){
            pitToCapture = 5 - (stoppingPoint % 6);
            currentPlayerStore = getStores().get(1);
        }

        /* add last stone to player store */
        getPits().get(stoppingPoint).setStones(0);
        currentPlayerStore.addStones();

        /* add opponent's stones to player store */
        try{
            numStonesCaptured = getNumStones(pitToCapture);
        }catch(PitNotFoundException e){
            System.out.println(e);
        }
        getPits().get(pitToCapture).setStones(0);
        for(int i = 0; i < numStonesCaptured; i++){
            currentPlayerStore.addStones();
        }
        return numStonesCaptured;
    }

    public int distributeStones(int startingPoint)throws PitNotFoundException{
        if(startingPoint < 0 || startingPoint >= getPits().size()){
            throw new PitNotFoundException("distributeStones: Pit " + startingPoint + " does not exist.\n");
        }
        
        int numStones = 0;
        
        try{
            numStones = getNumStones(startingPoint);
        }catch(PitNotFoundException e){
            System.out.println(e);
        }

        getPits().get(startingPoint).setStones(0);
        Store currentPlayerStore = storeList.get(0); // hardcoded for now
        int currentPit = startingPoint;
        int nextPit = (currentPit + 1) % 12;

        /* set current player store to add stones on distribution */
        if(startingPoint >= 0 && startingPoint <= 5){
            currentPlayerStore = storeList.get(0);
        } else if(startingPoint >= 6 && startingPoint <= 11){
            currentPlayerStore = storeList.get(1);
        }

        while(numStones > 0){
            /* checks if stone should be dropped in current player's store */
            if((currentPit == 5 && (startingPoint >= 0 && startingPoint <= 5) || (currentPit == 11 && (startingPoint >= 6 && startingPoint <= 11)))){
                currentPlayerStore.addStones();
                numStones--;
                currentPit = -1; // to indicate that we are currently in the store
                continue;
            }

            pitList.get(nextPit).addStone();
            currentPit = nextPit;
            nextPit = (nextPit + 1) % 12;
            numStones--;
        }
        /* return the pit last deposited into (store is not considered a pit)*/
        return currentPit;
    }

    public int getNumStones(int pitNum) throws PitNotFoundException{
        if(pitNum < 0 || pitNum >= getPits().size()){
            throw new PitNotFoundException("getNumStones: Pit " + pitNum + " does not exist.\n");
        }

        return getPits().get(pitNum).getStoneCount();
    }

    ArrayList<Pit> getPits(){
        return pitList;
    }

    ArrayList<Store> getStores(){
        return storeList;
    }

    void initializeBoard(){
        for(Pit pit:pitList){
            pit.setStones(4);
        }
    }

    public boolean isSideEmpty(int pitNum) throws PitNotFoundException{
        if(pitNum < 0 || pitNum >= getPits().size()){
            throw new PitNotFoundException("isSideEmpty: Pit " + pitNum + " does not exist.\n");
        }

        boolean isStillEmpty = true;
        int numPitsEmpty = 0;
        List<Pit> list = null;

        /* initialize list variable to the relevant sublist of pits */
        if(pitNum >= 0 && pitNum <= 5){
            list = getPits().subList(0, 6);
        }else if(pitNum >= 6 && pitNum <= 11){
            list = getPits().subList(6, 12);
        }

        /* loop continues while each pit is empty till end of sublist */
        while(isStillEmpty && numPitsEmpty < 6){
            for(Pit pit:list){
                if(pit.isPitEmpty()){
                    numPitsEmpty++;
                    continue;
                } else{
                    isStillEmpty = false;
                    break;
                }
            }
        }
    
        return isStillEmpty;
    }

    int moveStones(int startPit, Player player) throws InvalidMoveException{
        if(getPits().get(startPit).isPitEmpty()){
            throw new InvalidMoveException("moveStones: Please choose a non-empty pit.\n");
        }

        int lastPit = 12;
        try{
            lastPit = distributeStones(startPit);
        }catch(PitNotFoundException e){
            System.out.println(e);
        }


        return lastPit;
    }

    public void registerPlayers(Player one, Player two){
        /* set owner for each store */
        getStores().get(0).setOwner(one);
        getStores().get(1).setOwner(two);

        /* set store for each player */
        one.setStore(getStores().get(0));
        two.setStore(getStores().get(1));
    }

    public void resetBoard(){
        for(Pit pit:getPits()){
            pit.setStones(4);
        }
        for(Store store:getStores()){
            store.setStones(0);
        }
    }

    void setUpPits(){
        pitList = new ArrayList<Pit>(12);
        for(int i = 0; i < 12; i++){
            pitList.add(new Pit());
        }
    }

    void setUpStores(){
        storeList = new ArrayList<Store>(2);
        for(int i = 0; i < 2; i++){
            storeList.add(new Store());
        }
    }

    @Override
    public String toString(){
        return "Board toString() stub.";
    }

}
