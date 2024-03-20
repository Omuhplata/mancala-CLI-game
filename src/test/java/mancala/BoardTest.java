package mancala;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setup(){
        board = new Board();
        board.initializeBoard();
    }

    @Test
    public void getNumStonesTest(){
        int numStones = 0;

        /* check num stones for an initialized pit */
        try{
            numStones = board.getNumStones(1);
        }catch(PitNotFoundException e){
            System.out.println(e);
        }
        assertEquals(4, numStones);
        
        /* check pit not found exception */
        assertThrows(PitNotFoundException.class, () -> board.getNumStones(12));

    }

    @Test
    public void resetBoardTest(){
        /* check num stones in pit 1 */
        Pit pitOne = board.getPits().get(1);
        assertEquals(4, pitOne.getStoneCount());

        /* add stone to pit 1 */
        pitOne.addStone();
        assertEquals(5, pitOne.getStoneCount());

        /* reset board and check num stones in pit 1 */
        board.resetBoard();
        assertEquals(4, pitOne.getStoneCount());
    }

    @Test
    public void isSideEmptyTest(){
        boolean result = false;
        /* set all pits on one side to zero */
        for(int i = 0; i < 6; i++){
            board.getPits().get(i).setStones(0);
        }

        /* check non empty side */
        try{
            result = board.isSideEmpty(11);
        }catch(PitNotFoundException e){
            System.out.println(e);
        }
        assertEquals(false, result);

        /* check empty side */
        try{
            result = board.isSideEmpty(1);
        }catch(PitNotFoundException e){
            System.out.println(e);
        }
        assertEquals(true, result);

        /* check pit not found exception */
        assertThrows(PitNotFoundException.class, () -> board.isSideEmpty(12));

    }

    @Test
    public void registerPlayersTest(){
        Player one = new Player();
        Player two = new Player();

        board.registerPlayers(one, two);

        /* check two-way connection between player one and store */
        assertEquals(board.getStores().get(0).getOwner(), one);
        assertEquals(board.getStores().get(0), one.getStore());

    }

    @Test
    public void distributeStonesTest(){
        //TBD
    }

    @Test
    public void captureStonesTest(){
        //TBD
    }


}
