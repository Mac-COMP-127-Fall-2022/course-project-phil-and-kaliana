package SetGame;

import java.util.ArrayList;
import java.util.List;

public class SetUI {
    public ArrayList<List<Integer>> board;
    public ArrayList<List<Integer>> gameDeck;

    public void startGame(){
        clearBoard();
        gameDeck = createDeck();
        for(int i = 0; i < 5; i++) {
            addCards();
        }
    }

    private void clearBoard() {
        board.clear();
    }

    private void addCards() {
        for (int i = 0; i < 3; i++) {
            board.add(gameDeck.indexOf(0), null);
        }
    }

    boolean isFull() {
        return (board.size() >= 12);
    }

    void checkBoard() {
        if (!this.isFull()){
            if (gameDeck.size() >= 3) {
                addCards();
            }
        }
    }


    public ArrayList<List<Integer>> createDeck() {
        ArrayList<List<Integer>> cardDeck = new ArrayList<>();
        for (int c = 0; c < 3; c++) {
            for (int n = 0; n < 3; n++) {
                for (int s = 0; s < 3; s++) {
                    for (int f = 0; f < 3; f++) {
                        cardDeck.add(List.of(c, n, s, f));
                    }
                }
            }
        }
        return cardDeck;
    }
}
