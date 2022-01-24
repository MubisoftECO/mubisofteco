package org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLeastSold;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MostLeastSoldBuffer extends ThreadBufferDefinition<MostLeastSold> {

    private final List<MostLeastSold> buffer;

    public MostLeastSoldBuffer() {
        this.buffer = new ArrayList<>();
    }

    @Override
    public void put(MostLeastSold mostLeastSold) {
        this.getMutex().lock();
        if(buffer.size() == ThreadCapacityDefinition.MAX_MOST_LEAST_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                log.warn("MostLeastSoldBuffer was interrupted while saving an element.");
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
                log.warn("MostLeastSoldBuffer was interrupted while getting an element.");
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