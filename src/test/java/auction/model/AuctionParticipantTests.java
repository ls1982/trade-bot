package auction.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuctionParticipantTests {

    private AuctionParticipant auctionParticipant;

    @Before
    public void setup() {
        auctionParticipant = new AuctionParticipant(50);
    }

    @Test
    public void shouldPaySuccessfully() {
        auctionParticipant.pay(30);

        Assert.assertEquals(20, auctionParticipant.getCash());
        Assert.assertEquals(30, auctionParticipant.getLastBid().intValue());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        auctionParticipant.pay(100);
    }
}
