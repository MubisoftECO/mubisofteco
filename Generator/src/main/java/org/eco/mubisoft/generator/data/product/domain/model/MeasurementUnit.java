package org.eco.mubisoft.generator.data.product.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public enum MeasurementUnit {

    UNIT, G, L, CM3;

    public static MeasurementUnit getRandom() {
        List<MeasurementUnit> measurementUnits = Arrays.asList(values());
        return measurementUnits.get(new Random().nextInt(measurementUnits.size()));
    }

    public static MeasurementUnit getUnit(String unit) {
        switch (unit) {
            case "UNIT": return UNIT;
            case "G": return G;
            case "L": return L;
            case "CM3": return CM3;
            default: return null;
        }
    }
}
