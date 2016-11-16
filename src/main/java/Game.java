import java.util.*;

/**
 * Created by doug on 10/24/16
 */
public class Game {

	Stack<Card> stock = null;
	List<Card> foundation = null;
	List<AbstractCard> tableaux = new ArrayList<>();

	List<Move> moves = new ArrayList<>();

	public Game(Stack<Card> stock, List<Card> foundation, List<AbstractCard> tableaux) {
		this.stock = stock;
		this.foundation = foundation;
		this.tableaux = tableaux;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();

		// add the foundation
		for(Card card : foundation){
			sb.append(card).append("\t");
		}
		sb.append("\n\n");

		// maximum size for any column can never be more than 52. (couldn't hit this either, but let's roll with it
		for(int x = 0 ; x < 52 ; x++) {
			boolean foundSomething = false;

			for (AbstractCard card : tableaux) {
				String placeholder = "       ";

				// is it null? continue!!!
				if(card != null) {
					AbstractCard child = card.getNthChild(x);
					if (child != null) {
						foundSomething = true;
						placeholder = child.toString();
					}
				}

				sb.append(placeholder);
				sb.append("\t");
			}

			sb.append("\n");
			if(!foundSomething) break;
		}
		sb.append("\n");

		// add the stock
		for(Card card : stock){
			sb.append(card).append("\t");
		}

		return sb.toString();
	}

	public List<Move> identifyMoves() throws Exception {

		List<Move> moves = new ArrayList<>();

		// loop over the tableau
		for(int fromTableau = 0 ; fromTableau < tableaux.size() ; fromTableau++){
			// get the card at the root of this tableau (IE: the card that is the tableau)
			AbstractCard card = tableaux.get(fromTableau);

			// is the card null? skip to the next tableau
			if(card == null) continue;

			// loop backwards through the cards in this tableau
			for(int fromIndex = card.size()-1 ; fromIndex >= 0 ; fromIndex--){
				// if the nth card is actually a card (not unknown)...
				if(card.getNthChild(fromIndex) instanceof Card){
					// then get the nth card
					Card moveThisCard = (Card) card.getNthChild(fromIndex);

					// loop over the tableaux again to find some targets
					for(int toTableau = 0 ; toTableau < tableaux.size() ; toTableau++){
						AbstractCard tableau = tableaux.get(toTableau);

						// is this card null? If so, the tableau is empty and the card can be moved here
						if(tableau == null){
							// we can move the card to this card
							//System.out.printf("found possible move!!: %s to empty tableau %s\n", moveThisCard, toTableau);

							// add this move to the possible moves
							moves.add(new TableauMove(this, fromTableau, fromIndex, toTableau));
						} else {

							// this is the card we might be moving to
							Card toThisCard = (Card) tableau.getEnd();

							// can we move to this card?
							if (moveThisCard.getValue() + 1 == toThisCard.getValue()) {
								// we can move the card to this card
								//System.out.printf("found possible move!!: %s to %s\n", moveThisCard, toThisCard);

								// add this move to the possible moves
								moves.add(new TableauMove(this, fromTableau, fromIndex, toTableau));
							}
						}
					}

					// get the parent card
					AbstractCard parent = moveThisCard.getParent();

					// is the parent a Card?
					if(parent instanceof Card){
						Card parentCard = (Card) parent;

						// is the parent card not faceup?
						// is the parent card not sequential?
						if(!parentCard.isFaceUp() || parentCard.getValue() != moveThisCard.getValue() + 1){
							break;
						}

					} else {
						// the parent is an unknown card. we can't do anything with it so let's break
						break;
					}

				} else {
					throw new Exception("Found previously unknown card!!!");
				}
			}
		}

		// I can draw from the stock if it's not empty
		if(!tableaux.contains(null) && stock.size() > 0){
			//System.out.printf("found possible move!!: draw from stock\n");
			moves.add(new DrawFromStockMove(this.copy()));
		}

		return moves;
	}

	public Game copy() {
		Stack<Card> newStock = new Stack<>();
		List<Card> newFoundation = new ArrayList<>();;
		List<AbstractCard> newTableaux = new ArrayList<>();;
		List<Move> newMoves = new ArrayList<>();

		// copy the stock
		for(Card card : stock){
			newStock.add(card.copy());
		}

		// copy the foundation
		for(Card card : foundation){
			newFoundation.add(card.copy());
		}

		// copy the tableaux
		for(AbstractCard card : tableaux){
			if(card != null) {
				newTableaux.add(card.copy());
			} else {
				newTableaux.add(null);
			}
		}

		Game newGame = new Game(newStock, newFoundation, newTableaux);

		for(Move move : moves){
			newMoves.add(move);
		}
		newGame.moves = newMoves;

		return newGame;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Game game = (Game) o;

		if (!stock.equals(game.stock)) return false;
		if (!foundation.equals(game.foundation)) return false;
		return tableaux.equals(game.tableaux);

	}

	@Override
	public int hashCode() {
		int result = stock.hashCode();
		result = 31 * result + foundation.hashCode();
		result = 31 * result + tableaux.hashCode();
		return result;
	}
}
