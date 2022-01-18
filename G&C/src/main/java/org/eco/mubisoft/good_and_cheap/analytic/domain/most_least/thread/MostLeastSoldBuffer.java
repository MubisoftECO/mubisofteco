package org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.thread;


import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MostLeastSoldBuffer extends ThreadBufferDefinition<MostLeastSold> {
    private List<MostLeastSold> buffer;

    public MostLeastSoldBuffer() {
        super();
        this.buffer = new ArrayList<>();
    }

    @Override
    public void put(MostLeastSold mostLeastSold) {
        this.getMutex().lock();
        if(buffer.size() == ThreadCapacityDefinition.MAX_MOST_LEAST_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer.add(mostLeastSold);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public MostLeastSold get() {
        MostLeastSold value = null;
        this.getMutex().lock();
        if(buffer.size() == 0) {
            try {
                this.getIsEmpty().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        value = buffer.remove(0);
        this.getIsFull().signal();
        this.getMutex().unlock();
        return value;
    }

    @Override
    public int getBufferSize() {
        return buffer.size();
    }

}