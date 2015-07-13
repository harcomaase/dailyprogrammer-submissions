
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class THGame {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("How many players (2-8) ? ");
    int playerQuantity = scanner.nextInt();
    if (playerQuantity < 2 || playerQuantity > 8) {
      System.out.println("unsupported quantity of players. quitting.");
      return;
    }

    THGame game = new THGame(playerQuantity);
    game.dealFirstPlayerCardsAndCommunityCards();
    game.determineWinner();
  }

  private final List<Card> availableCards;
  private final List<Player> players;
  private final List<Card> communityCards;

  public THGame(int playerQuantity) {
    availableCards = new ArrayList<>();
    for (Color color : Color.values()) {
      for (Value value : Value.values()) {
        availableCards.add(new Card(color, value));
      }
    }
    Collections.shuffle(availableCards);
    players = new ArrayList<>();
    players.add(new Player("You"));
    for (int n = 1; n < playerQuantity; n++) {
      players.add(new Player("" + n));
    }
    communityCards = new ArrayList<>();
  }

  public void dealFirstPlayerCardsAndCommunityCards() {
    dealCardToPlayers();
    dealCardToPlayers();
    printPlayerCards();
    dealCommunityCardsAndInvokeAIFoldLogic();
  }

  private void dealCardToPlayers() {
    for (Player player : players) {
      player.getCards().add(getAndRemoveAvailableCard());
    }
  }

  private void dealCommunityCardsAndInvokeAIFoldLogic() {
    //flop
    getAndRemoveAvailableCard();
    communityCards.add(getAndRemoveAvailableCard());
    communityCards.add(getAndRemoveAvailableCard());
    communityCards.add(getAndRemoveAvailableCard());
    System.out.println("Flop:  " + communityCards);
    foldIfHandNotBetterThan(Hand.ONE_PAIR);
    //turn
    getAndRemoveAvailableCard();
    communityCards.add(getAndRemoveAvailableCard());
    System.out.println("Turn:  " + communityCards.subList(3, 4));
    foldIfHandNotBetterThan(Hand.TWO_PAIR);
    //river
    getAndRemoveAvailableCard();
    communityCards.add(getAndRemoveAvailableCard());
    System.out.println("River: " + communityCards.subList(4, 5));
  }

  private void foldIfHandNotBetterThan(Hand hand) {
    for (int n = 1; n < players.size(); n++) {
      Player player = players.get(n);
      if (player.hasFolded()) {
        continue;
      }
      if (Hand.determineHand(player.getCards(), communityCards).ordinal() > hand.ordinal()) {
        player.setFolded(true);
        System.out.println("Player '" + player + "' folded!");
      }
    }
  }

  private Card getAndRemoveAvailableCard() {
    return availableCards.remove(availableCards.size() - 1);
  }

  public void printPlayerCards() {
    for (Player player : players) {
      System.out.println("Player '" + player + "': " + player.getCards());
    }
    System.out.println();
  }

  public void determineWinner() {
    Hand highestHand = null;
    Player winner = null;
    for (Player player : players) {
      if (player.hasFolded()) {
        continue;
      }
      Hand hand = Hand.determineHand(player.getCards(), communityCards);
      if (highestHand == null || hand.ordinal() < highestHand.ordinal()) {
        highestHand = hand;
        winner = player;
      }
    }
    System.out.println("Winner: " + winner + ". " + highestHand.getDisplayName());
  }

  class Player {

    private final List<Card> cards = new ArrayList<>();
    private final String name;
    private boolean folded = false;

    public Player(String name) {
      this.name = name;
    }

    public List<Card> getCards() {
      return cards;
    }

    public boolean hasFolded() {
      return folded;
    }

    public void setFolded(boolean folded) {
      this.folded = folded;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  class Card {

    private final Color color;
    private final Value value;

    public Card(Color color, Value value) {
      this.color = color;
      this.value = value;
    }

    public Color getColor() {
      return color;
    }

    public Value getValue() {
      return value;
    }

    @Override
    public int hashCode() {
      int hash = 3;
      hash = 97 * hash + Objects.hashCode(this.color);
      hash = 97 * hash + Objects.hashCode(this.value);
      return hash;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      final Card other = (Card) obj;
      if (this.color != other.color) {
        return false;
      }
      if (this.value != other.value) {
        return false;
      }
      return true;
    }

    @Override
    public String toString() {
      return value + " of " + color;
    }
  }

  enum Color {

    DIAMONDS("diamonds"), HEARTS("hearts"), SPADES("spades"), CLUBS("clubs");

    private Color(String displayName) {
      this.displayName = displayName;
    }

    private final String displayName;

    @Override
    public String toString() {
      return displayName;
    }
  }

  enum Value {

    _2("two"), _3("three"), _4("four"), _5("five"), _6("six"), _7("seven"), _8("eight"), _9("nine"), _10("ten"), JACK("jack"), QUEEN("queen"), KING("king"), ACE("ace");

    private Value(String displayName) {
      this.displayName = displayName;
    }

    private final String displayName;

    @Override
    public String toString() {
      return displayName;
    }
  }

  enum Hand {

    STRAIGHT_FLUSH("straigh flush"), FOUR_OF_A_KIND("four of a kind"), FULL_HOUSE("full house"), FLUSH("flush"), STRAIGHT("straight"), THREE_OF_A_KIND("three of a kind"), TWO_PAIR("two pair"), ONE_PAIR("one pair"), HIGH_CARD("high card");

    private Hand(String displayName) {
      this.displayName = displayName;
    }

    private final String displayName;

    public String getDisplayName() {
      return displayName;
    }

    public static Hand determineHand(List<Card> playerCards, List<Card> communityCards) {
      List<Card> cards = new ArrayList<>();
      cards.addAll(playerCards);
      cards.addAll(communityCards);

      boolean flush = isFlush(cards);
      boolean straight = isStraight(cards);
      int[] maxValueQuantities = getMaxValueQuantities(cards);
      if (straight && flush) {
        return STRAIGHT_FLUSH;
      }
      if (hasFourOfAKind(maxValueQuantities)) {
        return FOUR_OF_A_KIND;
      }
      if (hasThreeOfAKind(maxValueQuantities) && hasTwoOfAKind(maxValueQuantities)) {
        return FULL_HOUSE;
      }
      if (straight) {
        return STRAIGHT;
      }
      if (flush) {
        return FLUSH;
      }
      if (hasThreeOfAKind(maxValueQuantities)) {
        return THREE_OF_A_KIND;
      }
      if (hasTwoOfAKind(maxValueQuantities)) {
        if (hasTwoPairs(cards, maxValueQuantities)) {
          return TWO_PAIR;
        }
        return ONE_PAIR;
      }
      return HIGH_CARD;
    }

    private static boolean isFlush(List<Card> cards) {
      int[] quantities = new int[Color.values().length];
      for (Card card : cards) {
        if (++quantities[card.getColor().ordinal()] == 5) {
          return true;
        }
      }
      return false;
    }

    private static boolean isStraight(List<Card> cards) {
      Collections.sort(cards, new Comparator<THGame.Card>() {

        @Override
        public int compare(Card c1, Card c2) {
          return c2.getValue().ordinal() - c1.getValue().ordinal();
        }
      });
      int inRow = 0;
      Value lastValue = Value.ACE;
      for (Card card : cards) {
        if (card.getValue().ordinal() == lastValue.ordinal() + 1) {
          inRow++;
        } else {
          inRow = 0;
        }
        if (inRow == 5) {
          return true;
        }
      }
      return false;
    }

    private static boolean hasFourOfAKind(int[] maxValueQuantities) {
      return hasXOfAKind(maxValueQuantities, 4);
    }

    private static boolean hasThreeOfAKind(int[] maxValueQuantities) {
      return hasXOfAKind(maxValueQuantities, 3);
    }

    private static boolean hasTwoOfAKind(int[] maxValueQuantities) {
      return hasXOfAKind(maxValueQuantities, 2);
    }

    private static boolean hasXOfAKind(int[] maxValueQuantities, int quantity) {
      for (int maxValueQuantity : maxValueQuantities) {
        if (maxValueQuantity == quantity) {
          return true;
        }
      }
      return false;
    }

    private static boolean hasTwoPairs(List<Card> cards, int[] maxValueQuantities) {
      List<Card> copyOfCards = new ArrayList<>(cards);
      for (Value value : Value.values()) {
        if (maxValueQuantities[value.ordinal()] == 2) {
          for (int n = copyOfCards.size() - 1; n >= 0; n--) {
            Card card = copyOfCards.get(n);
            if (card.getValue() == value) {
              copyOfCards.remove(n);
            }
          }
          break;
        }
      }
      return hasTwoOfAKind(getMaxValueQuantities(copyOfCards));
    }

    private static int[] getMaxValueQuantities(List<Card> cards) {
      int[] maxValueQuantities = new int[Value.values().length];
      for (Card card : cards) {
        maxValueQuantities[card.getValue().ordinal()]++;
      }
      return maxValueQuantities;
    }
  }
}
