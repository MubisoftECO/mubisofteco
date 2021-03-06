package org.eco.mubisoft.good_and_cheap.product.thread;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@EqualsAndHashCode(callSuper = false)
public class ProductBuffer extends ThreadBufferDefinition<ProductDto> {
    private final List<ProductDto> buffer;

    public ProductBuffer() {
        buffer = new ArrayList<>();
    }

    @Override
    public void put(ProductDto productDto) throws InterruptedException {
        this.getMutex().lock();
        while(buffer.size() == ThreadCapacityDefinition.MAX_PRODUCT_CAPACITY) {
            this.getIsFull().await();
        }
        buffer.add(productDto);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public ProductDto get() throws InterruptedException {
        ProductDto value = null;
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
