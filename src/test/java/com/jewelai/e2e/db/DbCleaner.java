package com.jewelai.e2e.db;

import java.util.UUID;

public interface DbCleaner {
    void cleanup(UUID testRunId);
}
