package SetGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cards {
    private Integer color;
    private Integer num;
    private Integer shape;
    private Integer fill;

    public Cards(Integer color, Integer num, Integer shape, Integer fill) {
        this.color = color;
        this.num = num;
        this.shape = shape;
        this.fill = fill;

    }


    List<Cards> Deck = new ArrayList<>();


    public List<Cards> CreateDeck() {
        List<Cards> cardDeck = new ArrayList<>();
        for (int c = 0; c < 3; c++) {
            for (int n = 0; n < 3; n++) {
                for (int s = 0; s < 3; s++) {
                    for (int f = 0; f < 3; f++) {
                        cardDeck.add(Cards(c, n, s, f)); // ????????????????????
                    }
                }
            }
        }
        return cardDeck;
    }

}
