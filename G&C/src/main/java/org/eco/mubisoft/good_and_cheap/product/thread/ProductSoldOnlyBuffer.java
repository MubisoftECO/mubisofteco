package org.eco.mubisoft.good_and_cheap.product.thread;

import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSoldOnlyBuffer extends ThreadBufferDefinition<ProductSoldOnlyDto> {
    private List<ProductSoldOnlyDto> buffer;

    public ProductSoldOnlyBuffer() {
        buffer = new ArrayList<>();
    }

    @Override
    public void put(ProductSoldOnlyDto productSoldOnlyDto) {
        this.getMutex().lock();
        if(buffer.size() == ThreadCapacityDefinition.MAX_PRODUCT_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.buffer.add(productSoldOnlyDto);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public ProductSoldOnlyDto get() {
        ProductSoldOnlyDto value = null;
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
