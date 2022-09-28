
package general;

/**
 * a Counter keeps count of something.
 * can increase and decrease the count,
 * and return the count value.
 * @author user1
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public class Counter {

    private int count;

    /**
     * A constructor that initialize
     * the counter with a starting value
     * to count from.
     * @param number
     */
    public Counter(int number) {
        count = number;
    }

    /**
     * Adds a number to current count.
     * @param number the number to add.
     */
    public void increase(int number) {
        count += number;
    }
    /**
     * subtracts a number from current count.
     * @param number the number to subtract.
     */
    public void decrease(int number) {
        count -= number;
    }
    /**
     * gets the current count.
     * @return the current count.
     */
    public int getValue() {
        return count;
    }
 }
