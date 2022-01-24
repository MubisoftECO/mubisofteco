package org.eco.mubisoft.good_and_cheap.thread;

import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Data
public abstract class ThreadBufferDefinition<T> {
    private Lock mutex = new ReentrantLock();
    private Condition isFull = mutex.newCondition();
    private Condition isEmpty = mutex.newCondition();

    protected abstract void put(T t);
    protected abstract T get();
    protected abstract int getBufferSize();
}
