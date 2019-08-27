package auction;

import auction.model.AuctionResult;
import auction.model.AuctionState;
import auction.strategy.OptimalPriceStrategy;

import java.io.Console;

/**
 * Executable class to provide the command-line interface for an auction game.
 * Reads initial values and bids from console and do auction.
 *
 * @author Alexey Smirnov
 */
public class AuctionMain {
    /**
     * Do all main stuff
     */
    public static void main(String[] args) {
        final Console console = System.console();

        if (console == null) {
            System.out.println("ERROR: Console has not been found!");
            System.exit(-1);
        }

        int quantity = readNumber(console, "Enter the quantity of the product: ");
        int cash = readNumber(console, "Enter the initial cash value: ");

        final Bidder bidder = new CommonBidder(new OptimalPriceStrategy());

        bidder.init(quantity, cash);

        console.printf("****************************************\n");
        console.printf("********THE AUCTION HAS STARTED*********\n");
        console.printf("****************************************\n");

        final AuctionManager auctionManager = new AuctionManager();

        AuctionState auctionState = new AuctionState(1, quantity, cash, 0, cash, 0,
                false, null, null);

        do {
            console.printf("Мove %d\n", auctionState.getMove());

            final int ownBid = readNumber(console, "Enter your bid: ");
            if (ownBid > auctionState.getSelfCash()) {
                console.printf("You can't bid more than your cash. Try again.\n");
                continue;
            }

            final int otherBid = bidder.placeBid();

            console.printf("Your bid is %d, other bid is %d\n", ownBid, otherBid);

            auctionState = auctionManager.processMove(auctionState, ownBid, otherBid);

            bidder.bids(otherBid, ownBid);

            printMoveResults(console, auctionState);

        } while (!auctionState.isFinished());

        printFinalResults(console, auctionState.getFinalResult());
    }

    /**
     * Prints move's results
     *
     * @param console      system console
     * @param auctionState current auction's state
     */
    private static void printMoveResults(Console console, AuctionState auctionState) {
        switch (auctionState.getMoveResult()) {
            case WIN:
                console.printf("You WIN! Your score is %d, your cash is %d.\n", auctionState.getSelfQuantity(), auctionState.getSelfCash());
                break;
            case LOSS:
                console.printf("You LOSE! Your score is %d, your cash is %d.\n", auctionState.getSelfQuantity(), auctionState.getSelfCash());
                break;
            case DRAW:
                console.printf("DRAW! Your score is %d, your cash is %d.\n", auctionState.getSelfQuantity(), auctionState.getSelfCash());
                break;
            default:
                throw new IllegalStateException("Unexpected auction result!");
        }
    }

    /**
     * Prints auction's final results
     *
     * @param console system console
     * @param result  final {@link AuctionResult}
     */
    private static void printFinalResults(Console console, AuctionResult result) {
        console.printf("****************************************\n");
        console.printf("********THE AUCTION HAS FINISHED********\n");

        switch (result) {
            case WIN:
                console.printf("*****CONGRATULATIONS!!! YOU WIN!!!******\n");
                break;
            case LOSS:
                console.printf("************SORRY, YOU LOSE!!!**********\n");
                break;
            case DRAW:
                console.printf("******************DRAW!!!***************\n");
                break;
            default:
                throw new IllegalStateException("Unexpected auction result!");
        }

        console.printf("****************************************\n");
    }

    /**
     * Reads and validates number from console
     *
     * @param console console
     * @param hint    hint message for input
     * @return number as int
     */
    private static int readNumber(Console console, String hint) {
        String numStr = console.readLine(hint);

        while (!isInteger(numStr)) {
            numStr = console.readLine("Wrong number! Try again: ");
        }

        return Integer.parseInt(numStr);
    }

    /**
     * Checks if string value is a number
     *
     * @param s validated string
     * @return {@code true} if param is integer
     */
    private static boolean isInteger(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
