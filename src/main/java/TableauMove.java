/**
 * Created by doug on 10/24/16
 */
public class TableauMove extends Move {

	private Game game;
	private int fromTableau;
	private int fromIndex;
	private int toTableau;

	public TableauMove(Game game, int fromTableau, int fromIndex, int toTableau) {
		this.game = game;
		this.fromTableau = fromTableau;
		this.fromIndex = fromIndex;
		this.toTableau = toTableau;

		// this should throw an exception if I'm getting into a weird state.
		Card moveThisCard = (Card) game.tableaux.get(fromTableau).getNthChild(fromIndex);
	}

	public Game doMove() throws Exception {
		Game newGame = game.copy();
		//System.out.printf("<TABLEAU MOVE fromTableau=%s fromIndex=%s toTableau=%s>\n", fromTableau, fromIndex, toTableau);
		//System.out.println("before: ");
		//System.out.println(newGame);

		Card moveThisCard = (Card) newGame.tableaux.get(fromTableau).getNthChild(fromIndex);

		// detach the card being moved from its current parent, or remove it from the tableaux altogether
		if(moveThisCard.getParent() != null){
			// this card has a parent, remove it
			moveThisCard.getParent().setChild(null);

			if(moveThisCard.getParent() instanceof Card){
				Card temp = (Card) moveThisCard.getParent();
				if(!temp.isFaceUp()) temp.flip();
			} else {
				throw new Exception("Found previously unknown card when doing a move!!!");
			}

			moveThisCard.setParent(null);
		} else {
			// this is the root of a tableau
			newGame.tableaux.set(fromTableau, null);
		}

		// get the card to move the card to
		if(newGame.tableaux.get(toTableau) != null) {
			Card toThisCard = (Card) newGame.tableaux.get(toTableau).getEnd();

			// set the card being moved's parent
			moveThisCard.setParent(toThisCard);
		} else {
			newGame.tableaux.set(toTableau, moveThisCard);
		}

		newGame.moves.add(this);

		//System.out.println("after:");
		//System.out.println(newGame);
		//System.out.println("</TABLEAU MOVE>");

		return newGame;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TableauMove that = (TableauMove) o;

		if (fromTableau != that.fromTableau) return false;
		if (fromIndex != that.fromIndex) return false;
		if (toTableau != that.toTableau) return false;
		return game.equals(that.game);

	}

	@Override
	public int hashCode() {
		int result = game.hashCode();
		result = 31 * result + fromTableau;
		result = 31 * result + fromIndex;
		result = 31 * result + toTableau;
		return result;
	}
}
