package org.eco.mubisoft.good_and_cheap.user.thread;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EqualsAndHashCode(callSuper = false)
public class UserBuffer extends ThreadBufferDefinition<Long> {

    private final List<Long> buffer;

    public UserBuffer() {
        this.buffer = new ArrayList<>();
    }

    @Override
    public void put(Long id) throws InterruptedException {
        this.getMutex().lock();
        while(buffer.size() == ThreadCapacityDefinition.MAX_USER_CAPACITY) {
            this.getIsFull().await();
        }
        if(!buffer.contains(id)) {
            buffer.add(id);
            this.getIsEmpty().signal();
        }
        this.getMutex().unlock();
    }

    @Override
    public Long get() throws InterruptedException {
        Long value;
        this.getMutex().lock();

        while(buffer.isEmpty()) {
            this.getIsEmpty().await();
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
