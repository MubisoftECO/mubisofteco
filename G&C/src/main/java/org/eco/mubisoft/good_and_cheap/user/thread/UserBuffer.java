package org.eco.mubisoft.good_and_cheap.user.thread;

import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserBuffer extends ThreadBufferDefinition<Long> {

    private List<Long> buffer;
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
                e.printStackTrace();
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
