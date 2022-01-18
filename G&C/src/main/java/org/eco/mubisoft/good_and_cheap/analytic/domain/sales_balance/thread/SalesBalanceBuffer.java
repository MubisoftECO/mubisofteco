package org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.thread;

import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SalesBalanceBuffer extends ThreadBufferDefinition<SalesBalance> {

    private List<SalesBalance> buffer;
    public SalesBalanceBuffer() {
        super();
        buffer = new ArrayList<>();
    }

    @Override
    public void put(SalesBalance salesBalance) {
        this.getMutex().lock();
        while(buffer.size() == ThreadCapacityDefinition.MAX_SALES_BALANCE_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer.add(salesBalance);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public SalesBalance get() {
        SalesBalance value = null;
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
