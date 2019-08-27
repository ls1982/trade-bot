package auction;


import auction.model.AuctionResult;
import auction.model.AuctionState;
import auction.strategy.OptimalPriceStrategy;
import org.junit.Assert;
import org.junit.Test;

/** Integration test, emulating auction workflow */
public class AuctionTest {

	private static final int INITIAL_QUANTITY = 20;
	private static final int INITIAL_CASH = 60;
	
	@Test
	public void shouldLose() {
		final AuctionResult expectedResult = AuctionResult.LOSS;

		Assert.assertEquals(expectedResult, runTest(INITIAL_QUANTITY, INITIAL_CASH, 8));
		Assert.assertEquals(expectedResult, runTest(INITIAL_QUANTITY, INITIAL_CASH, 9));
		Assert.assertEquals(expectedResult, runTest(INITIAL_QUANTITY, INITIAL_CASH, 13));
		Assert.assertEquals(expectedResult, runTest(INITIAL_QUANTITY, INITIAL_CASH, 14));
	}

	@Test
	public void shouldBeDraw() {
		final AuctionResult expectedResult = AuctionResult.DRAW; 
		
		Assert.assertEquals(expectedResult, runTest(INITIAL_QUANTITY, INITIAL_CASH, 10));
		Assert.assertEquals(expectedResult, runTest(INITIAL_QUANTITY, INITIAL_CASH, 11));
		Assert.assertEquals(expectedResult, runTest(INITIAL_QUANTITY, INITIAL_CASH, 12));
	}

	private AuctionResult runTest(int quantity, int cash, int ownBid) {
		final Bidder bidder = new CommonBidder(new OptimalPriceStrategy());

		bidder.init(quantity, cash);

		final AuctionManager auctionManager = new AuctionManager();

		AuctionState auctionState = new AuctionState(1, quantity, cash, 0, cash, 0,
				false, null, null);

		do {
			int otherBid = bidder.placeBid();
			if (auctionState.getSelfCash() < ownBid) {
				ownBid = auctionState.getSelfCash();
			}
			if (auctionState.getOtherCash() < otherBid) {
				otherBid = auctionState.getOtherCash();
			}
			auctionState = auctionManager.processMove(auctionState, ownBid, otherBid);

			bidder.bids(otherBid, ownBid);
		} while (!auctionState.isFinished());

		return auctionState.getFinalResult();
	}
}
