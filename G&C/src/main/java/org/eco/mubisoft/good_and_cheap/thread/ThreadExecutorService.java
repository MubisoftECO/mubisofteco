package org.eco.mubisoft.good_and_cheap.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum ThreadExecutorService {
    INSTANCE;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    public ExecutorService getExecutorService() {
        return executorService;
    }

}
