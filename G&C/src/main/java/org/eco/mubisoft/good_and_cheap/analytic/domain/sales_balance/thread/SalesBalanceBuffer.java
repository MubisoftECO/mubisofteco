package org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.thread;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EqualsAndHashCode(callSuper = false)
public class SalesBalanceBuffer extends ThreadBufferDefinition<SalesBalance> {

    private final List<SalesBalance> buffer;
    public SalesBalanceBuffer() {
        super();
        buffer = new ArrayList<>();
    }

    @Override
    public void put(SalesBalance salesBalance) throws InterruptedException {
        this.getMutex().lock();
        while(buffer.size() == ThreadCapacityDefinition.MAX_SALES_BALANCE_CAPACITY) {
            this.getIsFull().await();
        }
        buffer.add(salesBalance);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public SalesBalance get() throws InterruptedException {
        SalesBalance value;
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
