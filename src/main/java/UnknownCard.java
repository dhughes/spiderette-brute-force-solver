/**
 * Created by doug on 10/24/16
 */
public class UnknownCard extends AbstractCard {

	public String toString(){
		return "[????]";
	}

	@Override
	public AbstractCard copy() {
		UnknownCard card = new UnknownCard();
		if(getChild() != null) card.setChild(getChild().copy());

		return card;
	}

	@Override
	public boolean equals(Object o) {
		return true;
	}

	@Override
	public int hashCode() {
		return 31;
	}
}
