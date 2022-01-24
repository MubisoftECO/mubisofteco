package org.eco.mubisoft.good_and_cheap.user.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserBuffer extends ThreadBufferDefinition<Long> {

    private final List<Long> buffer;

    public UserBuffer() {
        super();
        this.buffer = new ArrayList<>();
    }

    @Override
    public void put(Long id) {
        this.getMutex().lock();
        while(buffer.size() == ThreadCapacityDefinition.MAX_USER_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                log.warn("UserBuffer was interrupted while saving an element.");
                break;
            }
        }
        if(!buffer.contains(id)) {
            buffer.add(id);
            this.getIsEmpty().signal();
        }
        this.getMutex().unlock();
    }

    @Override
    public Long get() {
        Long value = null;
        this.getMutex().lock();

        while(buffer.size() == 0) {
            try {
                this.getIsEmpty().await();
            } catch (InterruptedException e) {
                log.warn("UserBuffer was interrupted while getting an element.");
                break;
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
