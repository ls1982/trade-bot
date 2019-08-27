package auction.strategy;

import auction.model.AuctionParticipant;

/**
 * Pricing strategy based on calculated optimal price.
 * <br>
 * <b>Optimal price</b> is a price, which let us to buy little bit more than half of product spending all cash.
 * <p>
 * When the auction begins, we set the optimal price trying to make our competitor beat it, bidding higher price.
 * If our competitor is paying more than optimal price - he is wasting his money, so let him do it bidding 0;
 * </p>
 * <p>
 * Having wasted all the cash, our competitor can only pay 0. So we can buy any amount of the product, bidding only 1.
 * </p>
 *
 * @author Alexey Smirnov
 */
public class OptimalPriceStrategy implements PricingStrategy {

	private static final int LOWEST_POSSIBLE_PRICE = 1;

	/** {@inheritDoc} */
	@Override
	public int calculateNextBid(int quantityLeft, AuctionParticipant self, AuctionParticipant other) {

		final int targetQuantity = ((quantityLeft + self.getQuantity() + other.getQuantity()) / 2) + 1;

		if (self.getBidHistory().isEmpty() && other.getBidHistory().isEmpty()) {
			return 2 * self.getCash() / targetQuantity; // setting the initial price when auction starts
		}

		if (self.getCash() == 0) { // if we are out of money, we bid 0
			return 0;
		}

		if (other.getCash() == 0 || other.getLastBid() == 0) { // if competitor is out of cash - we can bid 1 and always win ))
			return LOWEST_POSSIBLE_PRICE;
		}

		final int selfQuantityToWin = targetQuantity - self.getQuantity();
		final int selfOptimalPrice = selfQuantityToWin > 0 ? 2 * self.getCash() / selfQuantityToWin : 0;

		if (selfOptimalPrice > other.getCash()) { // competitor can't bid our optimal price = 100% win
			return selfOptimalPrice;
		}

		if (selfOptimalPrice < other.getLastBid()) { // competitor is wasting money - let's save our cash, bidding 0
			return 0;
		}

		return selfOptimalPrice;
	}
}
