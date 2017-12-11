package com.elastic.tasks;

/**
 * Tasks implementing this interface will have the ability to rollback its
 * state.
 *
 * This means that if any problem happens in the update problem, this tasks will
 * revert all to its previous state.
 *
 * @author David Rodriguez Losada
 */
public interface IRollbackableTask {

    public void rollback();
}