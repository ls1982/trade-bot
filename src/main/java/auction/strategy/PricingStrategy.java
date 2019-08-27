package auction.strategy;

import auction.model.AuctionParticipant;

/**
 * Represents a pricing strategy, that calculates the next bid according to participants' state and quantity left
 *
 * @author Alexey Smirnov
 */
public interface PricingStrategy {
	/**
	 * Calculates the next bid according to participants current state
	 *
	 * @param quantityLeft unallocated quantity left to trade
	 * @param self self participant state object containing information about quantity won and cash left
	 * @param other competitor's state object containing information about quantity won and cash left
	 */
	int calculateNextBid(int quantityLeft, AuctionParticipant self, AuctionParticipant other);
}
