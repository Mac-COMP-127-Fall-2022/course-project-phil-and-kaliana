# SET

## Main Class

### Instance Variables

- priv `board` setUI
- priv `setChecker` setchecker
- cards `cards` cards

## Instance Methods
1. public `void` main()
    1. create board ui
    2. createDeck

3. button that adds cards (() ->
            addCards())

## setUI
### Instance Variables
- `List<Cards>` board = []

### Instance Methods
1. public `void` startGame()
    1. createDeck()
    2. clearBoard()
    3. for (i = 0, i < 5); // cstyle loop 4 
        1. addCards()

2. public `boolean` isFull()
    1. return (board.size >= 12)

3. addCards() // adds 3 cards
    1. cstyle loop 3
        1. add deck at index 0 to board
            1. check for earliest empty board space
            2. place card in empty space
        2. remove index 0 from deck

4. clearBoard()
    1. board = []

5. checkBoard()
    1. if !Board.isFull()
        1. if deck.length >= 3
            1. addCards()
    # OR
    1. if Board.contains(null)
        1. if deck.length >= 3
            1. addCards()

6. cardUI()
//     make Shape with Fill and Color, Number times --> graphics group

7. when a set is made (callback)
    1. set the three cards to null
    2. if !isFull()
        1. addCards()
    3. remove any null cards

## Cards

### Instance Variables
- priv `List<Int>` cardColors = [1, 2, 3]
- priv `List<Int>` cardNums = [1, 2, 3]
- priv `List<Int>` cardShapes = [1, 2, 3]
- priv `List<Int>` cardFills = [1, 2, 3]

- public Deck = createDeck()

### Instance Methods
1. `List<Int>` Card(Color c, Number n, Shape s, Fill f)
    1. this.color = c
    2. this.number = n
    3. this.shape = s
    4. this.fill = f

2. private `List<List<Int>>>` CreateDeck()
    1. cardDeck = []
    2. for c in cardColor
        1. for n in cardNum
            1. for s in cardShape
                1. for f in cardfill
                    1. add card with attributes [c, n, s, f]
    3. return cardDeck // creates every unique card and adds to deck

1. checkIfSet(Card CardA, Card CardB, Card CardC)
    1. return (checkTrait(CardA, CardB, CardC, c) &&
               checkTrait(CardA, CardB, CardC, n) &&
               checkTrait(CardA, CardB, CardC, s) &&
               checkTrait(CardA, CardB, CardC, f)) // returns whether each trait (color, number, shape, fill) is valid, thus the set is valid

2. checkTrait(CardA, CardB, CardC, trait)
    1. if (CardA[t] == cardB[t]) && (CardA[t] == cardC[t]) // if all of this trait are same
        1. return true
    2. if else (!(CardA[t] == cardB[t]) &&
                !(CardA[t] == cardC[t]) &&
                !(CardB[t] == cardC[t])) // if all of this trait are different
        1. return true
    3. else
        1. return false


### Notes
// rules and display is different // model vs view
// merge cards and checker?
// generate cards with card attributes list
// randomize deck order at some point (when creating deck?)
// cardUI(Color c, Number n, Shape s, Fill f)
//     make Shape with Fill and Color, Number times --> graphics group

## 
// LATE