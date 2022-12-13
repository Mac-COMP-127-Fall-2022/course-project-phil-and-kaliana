package SetGame;

import java.util.ArrayList;
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

        // Adds 12 random cards from the deck
        for (int i = 0; i < 12; i++) {
            int index = rand.nextInt(deck.size());
            board.add(deck.get(index));
            deck.remove(index);
        }
    
        // Refreshes board UI
        refreshBoard();

        add(cardsLeft);
        refreshLabels();

        // When a card is clicked, click card
        canvas.onClick((event) -> {
            GraphicsObject elementAt = canvas.getElementAt(event.getPosition());
            if (elementAt != null && elementAt.getClass() == Rectangle.class) {
                for (Card card : board) {
                    if (card.getCardBase() == elementAt) {
                        doCardClick(card);

                        return;
                    }
                }
            }     
        });

        // When key 3 is pressed and the board <= 21 add three cards
        canvas.onKeyDown((key) -> {
            if (key.getKey() == Key.NUM_3 && board.size() + 3 <= 21) {
                addCards();
            }
        });
    }

    private void refreshLabels() {
        cardsLeft.setText("Cards Used: " + usedCards.size() + "     " + "Cards Left: " + deck.size());
        cardsLeft.setPosition(
            canvas.getWidth()/2 - cardsLeft.getWidth()/2,
            50
        );
        canvas.draw();
    }

    // After the card is clicked...
    private void doCardClick(Card cardClicked) {
        Rectangle cardBase = cardClicked.getCardBase();
        // If it is already selected, deselect it and remove it from the selectedCards list
        if (selectedCards.contains(cardClicked)) {
            cardBase.setStrokeColor(Color.BLACK);
            cardBase.setStrokeWidth(Card.SHAPE_AND_CARD_STROKE_WIDTH);

            selectedCards.remove(cardClicked);
        } else { // otherwise, if not already selected, select it and add to selectedCards list
            cardBase.setStrokeColor(Color.RED);
            cardBase.setStrokeWidth(Card.SHAPE_AND_CARD_STROKE_WIDTH + 1);

            selectedCards.add(cardClicked);
        }

        canvas.draw();

        // Once selectedCards list has 3 cards, check if it's a set
        if (selectedCards.size() == 3) {
            if (checkIfSet(
                selectedCards.get(0),
                selectedCards.get(1),
                selectedCards.get(2)
            )) { // If so, remove the cards from the list and add them to used cards
                for (Card card : selectedCards) {
                    board.remove(card);
                    usedCards.add(card);
                    remove(card);
                }
                checkBoard();
            } else { // Otherwise, if not a set, deselect them
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

    // When checking a set of 3 cards, check each trait
    public boolean checkIfSet(Card cardA, Card cardB, Card cardC) {
        return (checkTrait(cardA, cardB, cardC, 0) &&
                checkTrait(cardA, cardB, cardC, 1) &&
                checkTrait(cardA, cardB, cardC, 2) &&
                checkTrait(cardA, cardB, cardC, 3)
        );
    }

    // For each trait
    boolean checkTrait(Card cardA, Card cardB, Card cardC, Integer t) {
        // Checks if they're all the same
        if ((cardA.getTrait(t) == cardB.getTrait(t)) && (cardA.getTrait(t) == cardC.getTrait(t))) {
            return true;
        }
        // or if they're all different
        else if (!(cardA.getTrait(t) == cardB.getTrait(t)) &&
                 !(cardA.getTrait(t) == cardC.getTrait(t)) &&
                 !(cardB.getTrait(t) == cardC.getTrait(t))) {
            return true;
        } else {
            // Otherwise
            return false;
        }
    }

    // Adds 3 random cards from the deck
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

    // Checks if the board needs to add 3 cards
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

    // Refreshes Board UI
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
