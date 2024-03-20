package mancala;

public class Pit {
 
    private int numStones;
    private int pitIndex;

    public Pit(int stones){
        numStones = stones;
    }

    public Pit(){
        numStones = 4;
    }

    void addStone(){
        numStones++;
    }

    int getStoneCount(){
        return numStones;
    }

    int removeStones(){
        int countCopy = numStones;
        setStones(0);
        return countCopy;
    }

    void setStones(int stones){
        numStones = stones;
    }

    boolean isPitEmpty(){
        if(numStones == 0){
            return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "Pit toString() stub.";
    }

}
