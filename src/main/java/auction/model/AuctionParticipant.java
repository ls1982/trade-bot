package auction.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the state of the auction participant
 *
 * @author Alexey Smirnov
 */
public class AuctionParticipant {
    private int cash;
    private int quantity;
    private List<Integer> bidHistory = new ArrayList<>();

    public AuctionParticipant(int cash) {
        this.cash = cash;
    }

    /**
     * @return participan's current cash
     * */
    public int getCash() {
        return cash;
    }

    /**
     * @return participan's current quantity bought
     * */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Decrease cash with price and adds history record
     *
     * @param price price to pay
     * */
    public void pay(int price) {
        if (cash >= price) {
            this.cash -= price;
            this.bidHistory.add(price);
        } else {
            throw new IllegalArgumentException(String.format("Can't pay %d. Lack of money.", price));
        }
    }

    /**
     * Adds bought quantity after auction move win
     *
     * @param quantity quantity bought
     * */
    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    /**
     * @return read only list of participant's bids
     * */
    public List<Integer> getBidHistory() {
        return Collections.unmodifiableList(bidHistory);
    }

    /**
     * @return last participant's bid. May return {@code null} if history is empty
     * */
    public Integer getLastBid() {
        return this.bidHistory.isEmpty() ? null : this.bidHistory.get(this.bidHistory.size() - 1);
    }
}
