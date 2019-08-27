package auction;

import auction.model.AuctionParticipant;
import auction.strategy.PricingStrategy;
import auction.util.Assert;

/**
 * Simple implementation for {@link Bidder} interface, that uses {@link PricingStrategy}
 * to calculate next bid
 *
 * @author Alexey Smirnov
 */
public class CommonBidder implements Bidder {
    protected PricingStrategy strategy;
    protected AuctionParticipant self;
    protected AuctionParticipant other;
    protected int quantity;

    public CommonBidder(PricingStrategy strategy) {
        Assert.argumentNotNull(strategy, "Auction strategy is not specified");
        this.strategy = strategy;
    }

    /**
     * Initialize the bidder for a new auction.
     *
     * @param quantity amount of quantity units of product х
     * @param cash     cash amount for each auction participant
     */
    @Override
    public void init(int quantity, int cash) {
        Assert.argumentIsPositive(quantity, "Quantity should be greater than 0");
        Assert.argumentIsPositive(cash, "Cash limit should be greater than 0");

        this.quantity = quantity;
        this.self = new AuctionParticipant(cash);
        this.other = new AuctionParticipant(cash);
    }

    /**
     * Retrieves current bid according to {@link PricingStrategy}
     *
     * @return current bid
     */
    @Override
    public int placeBid() {
        checkInitialized();

        return strategy.calculateNextBid(quantity, self, other);
    }

    /**
     * Updates bidder's state with own and other participant's bids
     *
     * @param own own bid
     * @param other other's bid
     */
    @Override
    public void bids(int own, int other) {
        Assert.argumentIsNonNegative(own, "Own bid should be greater than 0");
        Assert.argumentIsNonNegative(other, "Other's bid should be greater than 0");

        checkInitialized();

        this.self.pay(own);
        this.other.pay(other);
        this.quantity -= 2;

        if (own > other) {
            this.self.addQuantity(2);
        } else if (own < other) {
            this.other.addQuantity(2);
        } else {
            this.self.addQuantity(1);
            this.other.addQuantity(1);
        }
    }

    /**
     * Checks if the auction participants are initialized
     */
    private void checkInitialized() {
        Assert.stateNotNull(self, "Self participant is not initiated. You should call init(...) first");
        Assert.stateNotNull(other, "Other participant is not initiated. You should call init(...) first");
    }
}
