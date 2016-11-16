import java.util.*;

/**
 * Created by doug on 10/24/16
 */
public class Main {


	public static void main(String[] args) throws Exception {

		// setup the game
		// stock
		Stack<Card> stock = new Stack<>();
		stock.addAll(Arrays.asList(
				new Card(3, Card.HEART, Card.FACEDOWN),
				new Card(8, Card.HEART, Card.FACEDOWN),
				new Card(9, Card.SPADE, Card.FACEDOWN),
				new Card(Card.JACK, Card.CLUB, Card.FACEDOWN),
				new Card(4, Card.HEART, Card.FACEDOWN),
				new Card(7, Card.DIAMOND, Card.FACEDOWN),
				new Card(Card.JACK, Card.DIAMOND, Card.FACEDOWN),
				new Card(8, Card.CLUB, Card.FACEDOWN),
				new Card(4, Card.CLUB, Card.FACEDOWN),
				new Card(4, Card.DIAMOND, Card.FACEDOWN),
				new Card(Card.ACE, Card.CLUB, Card.FACEDOWN),
				new Card(6, Card.HEART, Card.FACEDOWN),
				new Card(2, Card.CLUB, Card.FACEDOWN),
				new Card(Card.KING, Card.CLUB, Card.FACEDOWN),
				new Card(2, Card.SPADE, Card.FACEDOWN),
				new Card(3, Card.DIAMOND, Card.FACEDOWN),
				new Card(Card.QUEEN, Card.CLUB, Card.FACEDOWN),
				new Card(9, Card.CLUB, Card.FACEDOWN),
				new Card(Card.KING, Card.HEART, Card.FACEDOWN),
				new Card(7, Card.HEART, Card.FACEDOWN),
				new Card(Card.KING, Card.DIAMOND, Card.FACEDOWN),
				new Card(5, Card.CLUB, Card.FACEDOWN),
				new Card(Card.QUEEN, Card.HEART, Card.FACEDOWN),
				new Card(2, Card.HEART, Card.FACEDOWN)
		));

		// make the collection of tableaux
		Stack<AbstractCard> tableaux = new Stack<>();

		// tableau 0
		tableaux.add(new Card(10, Card.CLUB, Card.FACEUP));

		// tableau 1
		tableaux.add(
				new Card(7, Card.SPADE, Card.FACEDOWN)
						.setChild(new Card(5, Card.SPADE, Card.FACEUP))
						.getRoot()
		);

		// tableau 2
		tableaux.add(
				new Card(Card.ACE, Card.DIAMOND, Card.FACEDOWN)
						.setChild(new Card(10, Card.DIAMOND, Card.FACEDOWN))
						.setChild(new Card(Card.KING, Card.SPADE, Card.FACEUP))
						.getRoot()
		);

		// tableau 3
		tableaux.add(
				new UnknownCard()
						.setChild(new Card(3, Card.SPADE, Card.FACEDOWN))
						.setChild(new Card(5, Card.HEART, Card.FACEDOWN))
						.setChild(new Card(Card.JACK, Card.SPADE, Card.FACEUP))
						.getRoot()
		);

		// tableau 4
		tableaux.add(
				new Card(3, Card.CLUB, Card.FACEDOWN)
						.setChild(new Card(Card.QUEEN, Card.SPADE, Card.FACEDOWN))
						.setChild(new Card(Card.QUEEN, Card.DIAMOND, Card.FACEDOWN))
						.setChild(new Card(7, Card.CLUB, Card.FACEDOWN))
						.setChild(new Card(5, Card.DIAMOND, Card.FACEUP))
						.getRoot()
		);

		// tableau 5
		tableaux.add(
				new Card(9, Card.HEART, Card.FACEDOWN)
						.setChild(new Card(Card.ACE, Card.HEART, Card.FACEDOWN))
						.setChild(new Card(6, Card.CLUB, Card.FACEDOWN))
						.setChild(new Card(Card.JACK, Card.HEART, Card.FACEDOWN))
						.setChild(new Card(8, Card.SPADE, Card.FACEDOWN))
						.setChild(new Card(8, Card.DIAMOND, Card.FACEUP))
						.getRoot()
		);

		// tableau 6
		tableaux.add(
				new Card(Card.ACE, Card.SPADE, Card.FACEDOWN)
						.setChild(new Card(4, Card.SPADE, Card.FACEDOWN))
						.setChild(new Card(6, Card.SPADE, Card.FACEDOWN))
						.setChild(new Card(2, Card.DIAMOND, Card.FACEDOWN))
						.setChild(new Card(9, Card.DIAMOND, Card.FACEDOWN))
						.setChild(new Card(10, Card.HEART, Card.FACEDOWN))
						.setChild(new Card(10, Card.SPADE, Card.FACEUP))
						.getRoot()
		);

		// make the game
		Game game1 = new Game(stock, new ArrayList<>(), tableaux);

		//System.out.println(game1);

		// move state
		List<Move> movesToTry = game1.identifyMoves();
		Set<Move> movesThatHaveBeenTried = new HashSet<>();

		// while we have moves to try, try a move
		while(true){

			// break out of the loop if there are no more moves to try
			if(movesToTry.isEmpty()) break;

			/*// pick a random move
			int index = new Random().nextInt(movesToTry.size());

			// get this move to try (removing it from the queue)
			Move poll = movesToTry.get(index);
			movesToTry.remove(poll);*/
			Move poll = movesToTry.get(0);
			movesToTry.remove(0);

			// add this move to the set of moves tried
			movesThatHaveBeenTried.add(poll);

			// do the move
			Game result;
			//try {
				result = poll.doMove();
			//} catch (Exception e){
			//	continue;
			//}

			// add the new moves to try
			//System.out.println(result);

			for(Move moveToTry : result.identifyMoves()){
				if(!movesThatHaveBeenTried.contains(moveToTry) && !movesToTry.contains(moveToTry)){
					movesToTry.add(moveToTry);
					System.out.println( movesToTry.size());
				} else {
					System.out.println("found duplicate move");
				}
			}

		}

		int x = 0;
	}
}
