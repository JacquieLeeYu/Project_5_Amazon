/**
 * <h1>Profitable</h1>
 * <p>
 * This interface represents something that can be used to make a profit. Along
 * with returning total profits it must also be able to provide a report.
 *
 * @author Jacquie Yu, Siddarth Pillai
 * @version 2018-12-06
 */
public interface Profitable {

    double getProfit();

    String report();

}