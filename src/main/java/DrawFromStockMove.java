/**
 * Created by doug on 10/24/16
 */
public class DrawFromStockMove extends Move {

	private Game game;

	public DrawFromStockMove(Game game) {
		this.game = game;
	}

	public Game doMove(){
		Game newGame = game.copy();

		//System.out.println("<DRAW FROM STOCK>");
		//System.out.println("before: ");
		//System.out.println(newGame);

		for(int x = 0 ; x < 7 ; x++){
			if(newGame.stock.size() > 0) {
				Card card = newGame.stock.pop();
				card.flip();
				if(newGame.tableaux.get(x) != null){
					newGame.tableaux.get(x).getEnd().setChild(card);
				} else {
					newGame.tableaux.set(x, card);
				}

			}
		}

		newGame.moves.add(this);

		//System.out.println("after:");
		//System.out.println(newGame);
		//System.out.println("</DRAW FROM STOCK>");

		return newGame;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DrawFromStockMove that = (DrawFromStockMove) o;

		return game.equals(that.game);

	}

	@Override
	public int hashCode() {
		return game.hashCode();
	}
}
