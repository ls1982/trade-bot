package auction;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommonBidderTest {

	private static int PRICE = 10;
	private CommonBidder bidder;

	@Before
	public void setup() {
		bidder = new CommonBidder((quantity, self, other) -> PRICE);
	}

	@Test
	public void shouldInitBidderSuccessfully() {
		bidder.init(1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowAnExceptionWhenQuantityZero() {
		bidder.init(0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailWhenCashZero() {
		bidder.init(1, 0);
	}

	@Test
	public void shouldGetCurrentBidSuccessfully() {
		bidder.init(1, 1);

		Assert.assertEquals(PRICE, bidder.placeBid());
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailIfNotInitializedWhenPlaceBid() {
		bidder.placeBid();
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailIfOwnBidIsNegative() {
		bidder.bids(-1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldFailIfOtherBidIsNegative() {
		bidder.bids(1, -1);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldFailIfNotInitializedWhenBids() {
		bidder.bids(1, 1);
	}

	@Test
	public void shouldAddSelfQuantityIfYouBidWins() {
		bidder.init(10, 10);

		bidder.bids(2, 1);

		Assert.assertEquals(2, bidder.self.getQuantity());
		Assert.assertEquals(8, bidder.self.getCash());
		Assert.assertEquals(0, bidder.other.getQuantity());
		Assert.assertEquals(9, bidder.other.getCash());
		Assert.assertEquals(8, bidder.quantity);
	}

	@Test
	public void shouldAddOtherQuantityIfOtherBidWins() {
		bidder.init(10, 10);

		bidder.bids(1, 2);

		Assert.assertEquals(0, bidder.self.getQuantity());
		Assert.assertEquals(9, bidder.self.getCash());
		Assert.assertEquals(2, bidder.other.getQuantity());
		Assert.assertEquals(8, bidder.other.getCash());
		Assert.assertEquals(8, bidder.quantity);
	}

	@Test
	public void shouldAddBothQuantityIfBidEqual() {
		bidder.init(10, 10);

		bidder.bids(2, 2);

		Assert.assertEquals(1, bidder.self.getQuantity());
		Assert.assertEquals(8, bidder.self.getCash());
		Assert.assertEquals(1, bidder.other.getQuantity());
		Assert.assertEquals(8, bidder.other.getCash());
		Assert.assertEquals(8, bidder.quantity);
	}
}
