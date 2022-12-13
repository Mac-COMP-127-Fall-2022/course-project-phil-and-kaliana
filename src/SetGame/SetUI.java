package SetGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import edu.macalester.graphics.*;
import edu.macalester.graphics.events.Key;

import java.awt.Color;


public class SetUI extends GraphicsGroup {
    private ArrayList<Card> board = new ArrayList<>();
    private ArrayList<Card> deck;
    private ArrayList<Card> usedCards = new ArrayList<>();

    private ArrayList<Card> selectedCards = new ArrayList<>();

    private GraphicsText cardsLeft = new GraphicsText("");

    private static final int BOARD_PADDING = 20;

    private Random rand = new Random();
    private final CanvasWindow canvas;

    public SetUI(ArrayList<Card> deck, CanvasWindow canvas) {
        this.deck = deck;
        this.canvas = canvas;

        for (int i = 0; i < 12; i++) {
            int index = rand.nextInt(deck.size());
            board.add(deck.get(index));
            deck.remove(index);
        }
        
        refreshBoard();

        add(cardsLeft);
        refreshLabels();

        canvas.onClick((event) -> {
            GraphicsObject elementAt = canvas.getElementAt(event.getPosition());
            // System.out.println(elementAt.getClass());
            if (elementAt != null && elementAt.getClass() == Rectangle.class) {
                for (Card card : board) {
                    if (card.getCardBase() == elementAt) {
                        doCardClick(card);

                        return;
                    }
                }
            }     
        });

        canvas.onKeyDown((key) -> {
            if (key.getKey() == Key.NUM_3 && board.size() + 3 <= 21) {
                addCards();
            }
        });
    }

    private void refreshLabels() {
        cardsLeft.setText("Cards Left: " + deck.size() + "     " + "Used Cards: " + usedCards.size());
        cardsLeft.setPosition(
            canvas.getWidth()/2 - cardsLeft.getWidth()/2,
            50
        );
        canvas.draw();
    }

    private void doCardClick(Card cardClicked) {
        Rectangle cardBase = cardClicked.getCardBase();
        if (selectedCards.contains(cardClicked)) {
            cardBase.setStrokeColor(Color.BLACK);
            cardBase.setStrokeWidth(Card.SHAPE_AND_CARD_STROKE_WIDTH);

            selectedCards.remove(cardClicked);
        } else {
            cardBase.setStrokeColor(Color.RED);
            cardBase.setStrokeWidth(Card.SHAPE_AND_CARD_STROKE_WIDTH + 1);

            selectedCards.add(cardClicked);
        }

        canvas.draw();

        if (selectedCards.size() == 3) {
            if (checkIfSet(
                selectedCards.get(0),
                selectedCards.get(1),
                selectedCards.get(2)
            )) {
                for (Card card : selectedCards) {
                    board.remove(card);
                    usedCards.add(card);
                    remove(card);
                }
                checkBoard();
            } else {
                for (Card card : selectedCards) {
                    card.getCardBase().setStrokeColor(Color.BLACK);
                    card.getCardBase().setStrokeWidth(Card.SHAPE_AND_CARD_STROKE_WIDTH);
                }
            }
            selectedCards.clear();
        }
    }

    private void clearBoard() {
        board.clear();
    }

    public boolean checkIfSet(Card cardA, Card cardB, Card cardC) {
        return (checkTrait(cardA, cardB, cardC, 0) &&
                checkTrait(cardA, cardB, cardC, 1) &&
                checkTrait(cardA, cardB, cardC, 2) &&
                checkTrait(cardA, cardB, cardC, 3)
        );
    }

    boolean checkTrait(Card cardA, Card cardB, Card cardC, Integer t) {
        if ((cardA.getTrait(t) == cardB.getTrait(t)) && (cardA.getTrait(t) == cardC.getTrait(t))) {
            return true;
        }
        else if (!(cardA.getTrait(t) == cardB.getTrait(t)) &&
                 !(cardA.getTrait(t) == cardC.getTrait(t)) &&
                 !(cardB.getTrait(t) == cardC.getTrait(t))) {
            return true;
        } else {
            return false;
        }
    }

    private void addCards() {
        for (int i = 0; i < 3; i++) {
            int choice = rand.nextInt(deck.size());
            board.add(deck.get(choice));
            deck.remove(choice);
        }
        refreshBoard();
        refreshLabels();
    }

    private boolean isFull() {
        return (board.size() >= 12);
    }

    private void checkBoard() {
        if (!this.isFull()){
            if (deck.size() >= 3) {
                addCards();
            }
            refreshBoard();
        } else {
            refreshBoard();
        }
    }

    private void refreshBoard() {
        double x;
        int y = 100;
        int cardIndex = 0;
        for (int row = 0; row < 3; row++) {
            x = (canvas.getWidth() - Card.CARD_WIDTH * (board.size()/3 + 1) + BOARD_PADDING * (board.size()/3))/2.0;
            for (int column = 0; column < board.size()/3; column++) {
                Card card = board.get(cardIndex);

                add(card);
                card.setPosition(x, y);

                x += Card.CARD_WIDTH + BOARD_PADDING;
                cardIndex++;
            }
            y += Card.CARD_HEIGHT + BOARD_PADDING;
        }
        // (canvas.getWidth() - board.size()/3 * Card.CARD_WIDTH + (board.size()/3 - 1) * BOARD_PADDING)/2
    }
}
