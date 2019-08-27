package auction.model;

/** Representation of auction's state */
public final class AuctionState {
	private final int move;
	private final int quantity;
	private final int selfCash;
	private final int selfQuantity;
	private final int otherCash;
	private final int otherQuantity;
	private final boolean finished;
	private final AuctionResult moveResult;
	private final AuctionResult finalResult;

	public AuctionState(int move, int quantity, int selfCash, int selfQuantity,
						int otherCash, int otherQuantity, boolean finished,
						AuctionResult moveResult,
						AuctionResult finalResult) {
		this.move = move;
		this.quantity = quantity;
		this.selfCash = selfCash;
		this.selfQuantity = selfQuantity;
		this.otherCash = otherCash;
		this.otherQuantity = otherQuantity;
		this.finished = finished;
		this.moveResult = moveResult;
		this.finalResult = finalResult;
	}

	/**
	 * @return current move's number
	 * */
	public int getMove() {
		return move;
	}

	/**
	 * @return current quantity
	 * */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @return current self cash
	 * */
	public int getSelfCash() {
		return selfCash;
	}

	/**
	 * @return current self quantity
	 * */
	public int getSelfQuantity() {
		return selfQuantity;
	}

	/**
	 * @return current competitor's cash
	 * */
	public int getOtherCash() {
		return otherCash;
	}

	/**
	 * @return current competitor's quantity bought
	 * */
	public int getOtherQuantity() {
		return otherQuantity;
	}

	/**
	 * @return {@code true},if auction is finished
	 * */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * @return current move's result
	 * */
	public AuctionResult getMoveResult() {
		return moveResult;
	}

	/**
	 * @return final auction's result. May return {@code null},
	 * if the auction has not finished yet.
	 * */
	public AuctionResult getFinalResult() {
		return finalResult;
	}
}
