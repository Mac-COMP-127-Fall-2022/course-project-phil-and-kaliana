import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import SetGame.Card;
import SetGame.SetGameMain;

public class SetCheckerTest {
    SetGameMain testGame = new SetGameMain();
    
    // Test Checker 1 attribute the same

    @Test
    void checkSetTest1(){
        
        Card cardA = new Card(0, 1, 2, 1);
        Card cardB = new Card(1, 2, 0, 1);
        Card cardC = new Card(2, 0, 1, 1);

        assertTrue(testGame.checkIfSet(cardA, cardB, cardC));
    }

    // Test Checker 2 attribute the same
    @Test
    void checkSetTest2(){
        
        Card cardA = new Card(0, 2, 1, 1);
        Card cardB = new Card(1, 0, 1, 1);
        Card cardC = new Card(2, 1, 1, 1);

        assertTrue(testGame.checkIfSet(cardA, cardB, cardC));
    }

    // Test Checker 3 attribute the same

    @Test
    void checkSetTest3(){
        
        Card cardA = new Card(0, 1, 1, 1);
        Card cardB = new Card(1, 1, 1, 1);
        Card cardC = new Card(2, 1, 1, 1);

        assertTrue(testGame.checkIfSet(cardA, cardB, cardC));
    }

    // Test Checker 0 attribute the same
    @Test
    void checkSetTest0(){
        
        Card cardA = new Card(0, 0, 0, 0);
        Card cardB = new Card(1, 1, 1, 1);
        Card cardC = new Card(2, 2, 2, 2);

        assertTrue(testGame.checkIfSet(cardA, cardB, cardC));
    }

    // TestChecker NOT VALID
    @Test
    void checkSetTestFails(){
        
        Card cardA = new Card(0, 2, 1, 1);
        Card cardB = new Card(1, 1, 0, 3);
        Card cardC = new Card(2, 1, 0, 1);

        assertFalse(testGame.checkIfSet(cardA, cardB, cardC));
    }


}
