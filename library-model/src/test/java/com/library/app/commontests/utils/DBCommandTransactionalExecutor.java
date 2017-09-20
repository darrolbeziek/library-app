package com.library.app.commontests.utils;

import org.junit.Ignore;

import javax.persistence.EntityManager;

import static org.junit.Assert.fail;

@Ignore
public class DBCommandTransactionalExecutor {

    private EntityManager em;

    public DBCommandTransactionalExecutor(EntityManager em) {
        this.em = em;
    }

    public <T> T executeCommand(DBCommand<T> dbCommand) {
        try {
            em.getTransaction().begin();
            T toReturn = dbCommand.execute();
            em.getTransaction().commit();
            em.clear();
            return toReturn;
        }
        catch (final Exception e) {
            fail("This exception should not have been thrown");
            e.printStackTrace();
            em.getTransaction().rollback();
            throw  new IllegalStateException(e);
        }

    }
}
