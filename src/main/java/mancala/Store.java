package mancala;

public class Store {

    private Player owner;
    private int numStones;

    public Store(Player storeOwner, int stones){
        setOwner(storeOwner);
        numStones = stones;
    }

    public Store(){
        numStones = 0;
    }

    void addStones(){ // supposed to take int param amount
        numStones++;
    }

    int emptyStore(){
        int countCopy = numStones;
        setStones(0);
        return countCopy;
    }

    Player getOwner(){
        return owner;
    }

    int getTotalStones(){
        return numStones;
    }

    void setOwner(Player player){
        owner = player;
    }

    void setStones(int stones){
        numStones = stones;
    }

    @Override
    public String toString(){
        return "Store toString() stub.";
    }
}
