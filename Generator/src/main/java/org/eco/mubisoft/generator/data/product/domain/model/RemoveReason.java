package org.eco.mubisoft.generator.data.product.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum RemoveReason {

    SOLD, EXPIRED, OTHER;

    public static RemoveReason getRandom() {
        List<RemoveReason> removeReasons = Arrays.asList(values());
        return removeReasons.get(new Random().nextInt(removeReasons.size()));
    }

}
