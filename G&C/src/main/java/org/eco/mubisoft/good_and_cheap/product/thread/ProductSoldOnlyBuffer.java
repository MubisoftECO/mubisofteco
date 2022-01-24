package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ProductSoldOnlyBuffer extends ThreadBufferDefinition<ProductSoldOnlyDto> {

    private final List<ProductSoldOnlyDto> buffer;

    public ProductSoldOnlyBuffer() {
        buffer = new ArrayList<>();
    }

    @Override
    public void put(ProductSoldOnlyDto productSoldOnlyDto) throws InterruptedException {
        this.getMutex().lock();
        if(buffer.size() == ThreadCapacityDefinition.MAX_PRODUCT_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                log.warn("ProductSoldOnlyBuffer was interrupted while saving an element.");
                throw new InterruptedException("ProductSoldOnlyBuffer was interrupted while saving an element.");
            }
        }
        this.buffer.add(productSoldOnlyDto);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public ProductSoldOnlyDto get() throws InterruptedException {
        ProductSoldOnlyDto value;
        this.getMutex().lock();
        if(buffer.size() == 0) {
            try {
                this.getIsEmpty().await();
            } catch (InterruptedException e) {
                log.warn("ProductSoldOnlyBuffer was interrupted while getting an element.");
                throw new InterruptedException("ProductSoldOnlyBuffer was getting while saving an element.");
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
