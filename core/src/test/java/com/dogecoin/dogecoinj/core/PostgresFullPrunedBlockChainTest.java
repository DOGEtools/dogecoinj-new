package com.dogecoin.dogecoinj.core;

import com.dogecoin.dogecoinj.store.BlockStoreException;
import com.dogecoin.dogecoinj.store.FullPrunedBlockStore;
import com.dogecoin.dogecoinj.store.PostgresFullPrunedBlockStore;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

/**
 * A Postgres implementation of the {@link AbstractFullPrunedBlockChainTest}
 */
@Ignore("enable the postgres driver dependency in the maven POM")
public class PostgresFullPrunedBlockChainTest extends AbstractFullPrunedBlockChainTest
{
    // Replace these with your postgres location/credentials and remove @Ignore to test
    // You can set up a fresh postgres with the command: create user bitcoinj superuser password 'password';
    private static final String DB_HOSTNAME = "localhost";
    private static final String DB_NAME = "dogecoinj_test";
    private static final String DB_USERNAME = "dogecoinj";
    private static final String DB_PASSWORD = "password";
    private static final String DB_SCHEMA = "blockstore_schema";

    // whether to run the test with a schema name
    private boolean useSchema = false;

    @After
    public void tearDown() throws Exception {
        ((PostgresFullPrunedBlockStore)store).deleteStore();
    }

    @Override
    public FullPrunedBlockStore createStore(NetworkParameters params, int blockCount)
            throws BlockStoreException {
        if(useSchema) {
            return new PostgresFullPrunedBlockStore(params, blockCount, DB_HOSTNAME, DB_NAME, DB_USERNAME, DB_PASSWORD, DB_SCHEMA);
        }
        else {
            return new PostgresFullPrunedBlockStore(params, blockCount, DB_HOSTNAME, DB_NAME, DB_USERNAME, DB_PASSWORD);
        }
    }

    @Override
    public void resetStore(FullPrunedBlockStore store) throws BlockStoreException {
        ((PostgresFullPrunedBlockStore)store).resetStore();
    }

    @Test
    public void testFirst100kBlocksWithCustomSchema() throws Exception {
        boolean oldSchema = useSchema;
        useSchema = true;
        try {
            super.testFirst100KBlocks();
        } finally {
            useSchema = oldSchema;
        }
    }
}
