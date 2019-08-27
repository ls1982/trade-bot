package auction;

import auction.model.AuctionResult;
import auction.model.AuctionState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AuctionManagerTest {
    private AuctionManager manager;

    @Before
    public void setup() {
        manager = new AuctionManager();
    }

    @Test
    public void shouldNotProcessFinishedAuction() {
        AuctionState finishedState = new AuctionState(1, 1 , 20,
                1, 20, 1, true, AuctionResult.DRAW, AuctionResult.DRAW);

        AuctionState nextState = manager.processMove(finishedState, 10, 10);

        Assert.assertEquals(finishedState, nextState);
    }

    @Test
    public void shouldWinMove() {
        AuctionState previousState = new AuctionState(1, 10 , 20,
                1, 20, 1, false, AuctionResult.DRAW, null);

        AuctionState currentState = manager.processMove(previousState, 11, 10);

        Assert.assertEquals(AuctionResult.WIN, currentState.getMoveResult());
        Assert.assertEquals(8, currentState.getQuantity());
        Assert.assertEquals(3, currentState.getSelfQuantity());
        Assert.assertEquals(1, currentState.getOtherQuantity());
        Assert.assertEquals(9, currentState.getSelfCash());
        Assert.assertEquals(10, currentState.getOtherCash());
    }

    @Test
    public void shouldLoseMove() {
        AuctionState previousState = new AuctionState(1, 10 , 20,
                1, 20, 1, false, AuctionResult.DRAW, null);

        AuctionState currentState = manager.processMove(previousState, 11, 14);

        Assert.assertEquals(AuctionResult.LOSS, currentState.getMoveResult());
        Assert.assertEquals(8, currentState.getQuantity());
        Assert.assertEquals(1, currentState.getSelfQuantity());
        Assert.assertEquals(3, currentState.getOtherQuantity());
        Assert.assertEquals(9, currentState.getSelfCash());
        Assert.assertEquals(6, currentState.getOtherCash());
    }

    @Test
    public void shouldGetDrawMove() {
        AuctionState previousState = new AuctionState(1, 10 , 20,
                1, 20, 1, false, AuctionResult.DRAW, null);

        AuctionState currentState = manager.processMove(previousState, 0, 0);

        Assert.assertEquals(AuctionResult.DRAW, currentState.getMoveResult());
        Assert.assertEquals(8, currentState.getQuantity());
        Assert.assertEquals(2, currentState.getSelfQuantity());
        Assert.assertEquals(2, currentState.getOtherQuantity());
        Assert.assertEquals(20, currentState.getSelfCash());
        Assert.assertEquals(20, currentState.getOtherCash());
    }

    @Test
    public void shouldGetAuctionFinished() {
        AuctionState previousState = new AuctionState(1, 2, 20,
                1, 20, 1, false, AuctionResult.DRAW, null);

        AuctionState currentState = manager.processMove(previousState, 0, 0);

        Assert.assertTrue(currentState.isFinished());
        Assert.assertEquals(AuctionResult.DRAW, currentState.getFinalResult());
    }
}
