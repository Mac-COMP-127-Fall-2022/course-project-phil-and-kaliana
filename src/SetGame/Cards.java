package SetGame;

import java.util.ArrayList;
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


    // public ArrayList<List<Integer>> CreateDeck() {
    //     ArrayList<List<Integer>> cardDeck = new ArrayList<>();
    //     for (int c = 0; c < 3; c++) {
    //         for (int n = 0; n < 3; n++) {
    //             for (int s = 0; s < 3; s++) {
    //                 for (int f = 0; f < 3; f++) {
    //                     cardDeck.add(List.of(c, n, s, f));
    //                 }
    //             }
    //         }
    //     }
    //     return cardDeck;
    // }

    boolean checkIfSet(List<Integer> cardA, List<Integer> cardB, List<Integer> cardC) {
        return (checkTrait(cardA, cardB, cardC, 0) &&
                checkTrait(cardA, cardB, cardC, 1) &&
                checkTrait(cardA, cardB, cardC, 2)
        );
    }

    boolean checkTrait(List<Integer> cardA, List<Integer> cardB, List<Integer> cardC, Integer t) {
        if ((cardA.indexOf(t) == cardB.indexOf(t)) && (cardA.indexOf(t) == cardC.indexOf(t))) {
            return true;
        }
        else if (!(cardA.indexOf(t) == cardB.indexOf(t)) &&
                 !(cardA.indexOf(t) == cardC.indexOf(t)) &&
                 !(cardB.indexOf(t) == cardC.indexOf(t))) {
            return true;
        } else {
            return false;
        }
    }
}
