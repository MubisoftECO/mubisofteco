package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EqualsAndHashCode(callSuper = false)
public class ProductSoldOnlyTotalBuffer extends ThreadBufferDefinition<MostLessSoldDetail> {

    private final List<MostLessSoldDetail> buffer;

    public ProductSoldOnlyTotalBuffer() {
        buffer = new ArrayList<>();
    }

    @Override
    public void put(MostLessSoldDetail mostLessSoldDetail) {
        this.getMutex().lock();
        while (buffer.size() == ThreadCapacityDefinition.MAX_PRODUCT_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                log.warn("ProductSoldOnlyTotalBuffer was interrupted while saving an element.");
                Thread.currentThread().interrupt();
            }
        }
        this.buffer.add(mostLessSoldDetail);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public MostLessSoldDetail get() {
        MostLessSoldDetail value;
        this.getMutex().lock();
        while (buffer.isEmpty()) {
            try {
                this.getIsEmpty().await();
            } catch (InterruptedException e) {
                log.warn("ProductSoldOnlyTotalBuffer was interrupted while getting an element.");
                Thread.currentThread().interrupt();
            }
        }
        value = buffer.remove(0);
        this.getIsFull().signal();
        this.getMutex().unlock();
        return value;
    }

    public int getBufferSize() {
        return buffer.size();
    }
}
