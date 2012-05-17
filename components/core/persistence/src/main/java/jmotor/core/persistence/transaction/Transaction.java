package jmotor.core.persistence.transaction;

/**
 * Component:
 * Description:
 * Date: 11-9-22
 *
 * @author Andy.Ai
 */
public interface Transaction {
    void begin();

    void rollback();

    void commit();

    boolean wasCommitted();

    boolean wasRolledBack();
}
