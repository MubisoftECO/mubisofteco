package org.eco.mubisoft.good_and_cheap.product.thread;

import org.eco.mubisoft.good_and_cheap.analytic.domain.most_least.model.MostLessSoldDetail;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSoldOnlyTotalBuffer extends ThreadBufferDefinition<MostLessSoldDetail> {

    private List<MostLessSoldDetail> buffer;

    public ProductSoldOnlyTotalBuffer() {
         super();
         buffer = new ArrayList<>();
    }

    @Override
    public void put(MostLessSoldDetail mostLessSoldDetail) {
        this.getMutex().lock();
        if(buffer.size() == ThreadCapacityDefinition.MAX_PRODUCT_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer.add(mostLessSoldDetail);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public MostLessSoldDetail get() {
        MostLessSoldDetail value = null;
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

    public int getBufferSize() {
        return buffer.size();
    }
}
