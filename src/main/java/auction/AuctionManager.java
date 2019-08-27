package auction;

import auction.model.AuctionResult;
import auction.model.AuctionState;

/**
 * Performs the auction logic processing the moves
 *
 * @author Alexey Smirnov
 */
public class AuctionManager {
	/**
	 * Processing the auction's current move according to current auction state and participants' bids.
	 *
	 * @param currentState current auction state
	 * @param ownBid your current bid
	 * @param otherBid competitor's bid
	 *
	 * @return new {@link AuctionState}
	 */
	public AuctionState processMove(AuctionState currentState, int ownBid, int otherBid) {

		if (currentState.isFinished()) {
			return currentState;
		}

		if (ownBid > currentState.getSelfCash()) {
			ownBid = currentState.getSelfCash();
		}

		if (otherBid > currentState.getOtherCash()) {
			otherBid = currentState.getOtherCash();
		}

		int move = currentState.getMove();
		int selfCash = currentState.getSelfCash();
		int otherCash = currentState.getOtherCash();
		int selfScore = currentState.getSelfQuantity();
		int otherScore = currentState.getOtherQuantity();
		int quantity = currentState.getQuantity();

		boolean finished = false;

		AuctionResult finalResult = null;
		AuctionResult moveResult;

		selfCash -= ownBid;
		otherCash -= otherBid;

		final int quantityStep = quantity < 2 ? quantity : 2;

		quantity -= quantityStep;

		if (ownBid > otherBid) {
			selfScore += quantityStep;
			moveResult = AuctionResult.WIN;
		} else if (ownBid < otherBid) {
			otherScore += quantityStep;
			moveResult = AuctionResult.LOSS;
		} else {
			selfScore += 1;
			otherScore += 1;
			moveResult = AuctionResult.DRAW;
		}

		move++;

		if (quantity == 0) {
			finished = true;

			if (selfScore > otherScore) {
				finalResult = AuctionResult.WIN;
			} else if (selfScore < otherScore) {
				finalResult = AuctionResult.LOSS;
			} else {
				finalResult = AuctionResult.DRAW;
			}
		}

		return new AuctionState(move, quantity, selfCash, selfScore,
				otherCash, otherScore, finished, moveResult, finalResult);
	}
}
