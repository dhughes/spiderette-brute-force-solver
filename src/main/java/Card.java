/**
 * Created by doug on 10/24/16
 */
public class Card extends AbstractCard {

	public static int CLUB = 0;
	public static int DIAMOND = 1;
	public static int HEART = 2;
	public static int SPADE = 3;

	public static int ACE = 1;
	public static int JACK = 11;
	public static int QUEEN = 12;
	public static int KING = 13;

	public static boolean FACEUP = true;
	public static boolean FACEDOWN = false;

	private int value;
	private int suit;
	private boolean direction;

	public Card(int value, int suit, boolean direction) {
		this.value = value;
		this.suit = suit;
		this.direction = direction;
	}

	public boolean isFaceUp() {
		return direction;
	}

	public void setDirection(boolean direction) {
		this.direction = direction;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public char suitChar(){
		switch (suit){
			case 0:
				return 'C';
			case 1:
				return 'D';
			case 2:
				return 'H';
			default:
				return 'S';
		}
	}

	public String valueStr(){
		switch (value){
			case 13:
				return "K";
			case 12:
				return "Q";
			case 11:
				return "J";
			case 1:
				return "A";
			default:
				return String.valueOf(value);
		}
	}

	public String toString(){
		if(direction == FACEUP){
			return String.format("[%2s %s]", valueStr(), suitChar());
		} else {
			return "[****]";
		}
	}

	public Card copy() {
		Card card = new Card(this.value, this.suit, this.direction);
		if(getChild() != null) card.setChild(getChild().copy());

		return card;
	}

	public void flip() {
		this.direction = !this.direction;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		Card card = (Card) o;

		if (value != card.value) return false;
		if (suit != card.suit) return false;

		if(this.getChild() != null && !this.getChild().equals(card.getChild())) return false;

		return direction == card.direction;
	}

	@Override
	public int hashCode() {
		int result = value;
		result = 31 * result + suit;
		result = 31 * result + (direction ? 1 : 0);
		if(getChild() != null) result = 31 * result + getChild().hashCode();

		return result;
	}
}
