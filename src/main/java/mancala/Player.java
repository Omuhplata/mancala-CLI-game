package mancala;

public class Player {

    private String playerName;
    private Store playerStore;

    public Player(String name){
        setName(name);
    }

    public Player(){
        setName("playerX");
    }

    public String getName(){
        return playerName;
    }

    public Store getStore(){
        return playerStore;
    }

    public int getStoreCount(){
        return playerStore.getTotalStones();
    }

    void setName(String name){
        playerName = name;
    }

    void setStore(Store store){
        playerStore = store;
    }

    @Override
    public String toString(){
        return "Player toString() stub.";
    }

}
