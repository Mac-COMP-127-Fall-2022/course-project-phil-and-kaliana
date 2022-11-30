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

2. checkBoard()
    1. if !Board.isFull()
        1. if deck.length >= 3
            1. addCards()

3. button that adds cards
    1. addCards()

## setUI
### Instance Variables
- `List<Cards>` board

### Instance Methods
1. public `void` startGame()
    1. createDeck()
    2. for i in 4;
        1. addCards()

2. public `boolean` isFull()
    1. return (boardLength >= 12)

3. addCards() // adds 3 cards
    1. cstyle loop 3
        1. add deck at index 0 to board
        2. remove index 0 from deck

4. clearBoard()
    1. 

## Card

### Instance Variables
- priv `List<Color>` cardColor
- priv `List<Int>` cardNum
- priv `List<Int>` cardShape
- priv `List<Int>` cardFill

- Deck = createDeck()

### Instance Methods
1. private `List<List<Int>>>` CreateDeck
    1. cardDeck = []
    2. for c in cardColor
        1. for n in cardNum
            1. for s in cardShape
                1. for f in cardfill
                    1. add card with attributes [c, n, s, f]
    3. return cardDeck



## SetChecker

1. checkIfSet(Card CardA, Card CardB, Card CardC)
    1. colorCheck = checkTrait(CardA, CardB, CardC, c)
    2. numberCheck = checkTrait(CardA, CardB, CardC, n)
    3. shapeCheck = checkTrait(CardA, CardB, CardC, s)
    4. fillCheck = checkTrait(CardA, CardB, CardC, f)
    5. if (colorCheck && numberCheck, shapeCheck, fillCheck),
        1. return true
    2. else
        2. return false

2. checkTrait(CardA, CardB, CardC, trait)
    1. if (CardA[t] == cardB[t]) && (CardA[t] == cardC[t]) // if all of this trait are same
        1. return true
    2. if else (!(CardA[t] == cardB[t]) &&
                !(CardA[t] == cardC[t]) &&
                !(CardB[t] == cardC[t])) // if all of this trait are different
        1. return true
    3. else
        1. return false