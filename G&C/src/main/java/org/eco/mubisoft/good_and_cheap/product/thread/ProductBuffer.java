package org.eco.mubisoft.good_and_cheap.product.thread;

import org.eco.mubisoft.good_and_cheap.analytic.domain.sales_balance.model.SalesBalance;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.thread.ThreadBufferDefinition;
import org.eco.mubisoft.good_and_cheap.thread.ThreadCapacityDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductBuffer extends ThreadBufferDefinition<ProductDto> {
    private List<ProductDto> buffer;

    public ProductBuffer() {
        super();
        buffer = new ArrayList<>();
    }

    @Override
    public void put(ProductDto productDto) {
        this.getMutex().lock();
        while(buffer.size() == ThreadCapacityDefinition.MAX_PRODUCT_CAPACITY) {
            try {
                this.getIsFull().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        buffer.add(productDto);
        this.getIsEmpty().signal();
        this.getMutex().unlock();
    }

    @Override
    public ProductDto get() {
        ProductDto value = null;
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
