
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
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
    game.printCurrentStatus();
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
    for (int n = 0; n < playerQuantity; n++) {
      players.add(new Player("" + n));
    }
    communityCards = new ArrayList<>();
  }

  public void dealFirstPlayerCardsAndCommunityCards() {
    dealCardToPlayers();
    dealCardToPlayers();
    dealCommunityCards();
  }

  private void dealCardToPlayers() {
    for (Player player : players) {
      player.getCards().add(getAndRemoveAvailableCard());
    }
  }

  private void dealCommunityCards() {
    //flop
    getAndRemoveAvailableCard();
    communityCards.add(getAndRemoveAvailableCard());
    communityCards.add(getAndRemoveAvailableCard());
    communityCards.add(getAndRemoveAvailableCard());
    //turn
    getAndRemoveAvailableCard();
    communityCards.add(getAndRemoveAvailableCard());
    //river
    getAndRemoveAvailableCard();
    communityCards.add(getAndRemoveAvailableCard());
  }

  private Card getAndRemoveAvailableCard() {
    return availableCards.remove(availableCards.size() - 1);
  }

  public void printCurrentStatus() {
    for (Player player : players) {
      System.out.println("Player '" + player + "': " + player.getCards());
    }
    System.out.println();
    System.out.println("Flop:  " + communityCards.subList(0, 3));
    System.out.println("Turn:  " + communityCards.subList(3, 4));
    System.out.println("River: " + communityCards.subList(4, 5));
  }

  class Player {

    private final List<Card> cards = new ArrayList<>();
    private final String name;

    public Player(String name) {
      this.name = name;
    }

    public List<Card> getCards() {
      return cards;
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
}
