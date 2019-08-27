package auction.strategy;

import auction.model.AuctionParticipant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OptimalPriceStrategyTest {

    private static final int INITIAL_QUANTITY = 20;
    private static final int INITIAL_CASH = 60;

    private OptimalPriceStrategy strategy;

    @Before
    public void setup() {
        strategy = new OptimalPriceStrategy();
    }

    @Test
    public void shouldPlaceFirstBidCorrectly() {
        AuctionParticipant self = new AuctionParticipant(INITIAL_CASH);
        AuctionParticipant other = new AuctionParticipant(INITIAL_CASH);

        final int bid = strategy.calculateNextBid(INITIAL_QUANTITY, self, other);
        Assert.assertEquals(10, bid);
    }

    @Test
    public void shouldBidZeroIfOutOfCash() {
        AuctionParticipant self = new AuctionParticipant(0);
        AuctionParticipant other = new AuctionParticipant(INITIAL_CASH);

        final int bid = strategy.calculateNextBid(INITIAL_QUANTITY, self, other);
        Assert.assertEquals(0, bid);
    }

    @Test
    public void shouldBidOneIfOtherOutOfCash() {
        AuctionParticipant self = new AuctionParticipant(INITIAL_CASH);
        self.pay(10);
        AuctionParticipant other = new AuctionParticipant(0);
        other.pay(0);

        final int bid = strategy.calculateNextBid(INITIAL_QUANTITY, self, other);
        Assert.assertEquals(1, bid);
    }

    @Test
    public void shouldBidOptimalPriceIfOtherHasLowCash() {
        AuctionParticipant self = new AuctionParticipant(INITIAL_CASH);
        AuctionParticipant other = new AuctionParticipant(INITIAL_CASH);

        strategy.calculateNextBid(INITIAL_QUANTITY, self, other);

        self.pay(27);
        self.addQuantity(4);

        other.pay(30);
        other.pay(6);
        other.addQuantity(4);

        final int bid = strategy.calculateNextBid(12, self, other);
        Assert.assertEquals(9, bid);
    }

    @Test
    public void shouldBidZeroIfOtherWasteCash() {
        AuctionParticipant self = new AuctionParticipant(INITIAL_CASH);
        self.pay(10);
        AuctionParticipant other = new AuctionParticipant(INITIAL_CASH);
        other.pay(30);

        final int bid = strategy.calculateNextBid(INITIAL_QUANTITY, self, other);
        Assert.assertEquals(0, bid);
    }

}
