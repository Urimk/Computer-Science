
package listener;

/**
 * Hit Notifier adds and removes listeners to a
 * list of listeners that will be notified when
 * the object is hit.
 * @author Uri Knoll
 * @version  %I%, %G%
 * @since 17.0.2
 */
public interface HitNotifier {
    /**
     * Add hl as a listener to hit events.
     * @param hl the listener to add.
     */
    void addHitListener(HitListener hl);
    /**
     * Remove hl from the list of listeners to hit events.
     * @param hl the listener to remove.
     */
    void removeHitListener(HitListener hl);
 }
