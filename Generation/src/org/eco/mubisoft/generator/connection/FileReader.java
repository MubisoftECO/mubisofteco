package org.eco.mubisoft.generator.connection;

import org.eco.mubisoft.data.product.dao.ProductFacade;
import org.eco.mubisoft.data.product.model.ProductTypeNames;
import org.eco.mubisoft.data.recipe.model.RecipeNames;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FileReader {

    private static final String PRODUCT_TYPE_FILE = "files/product/ProductTypes.csv";
    private static final String RECIPE_FILE = "files/recipe/RecipeNames.csv";

    public List<ProductTypeNames> getProductTypeNames() {
        List<ProductTypeNames> names = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(PRODUCT_TYPE_FILE))) {
            String line = br.readLine();

            do {
                if (!line.contains("name_es")) {
                    String[] values = line.split(",");
                    names.add(new ProductTypeNames(
                            values[0], values[1], values[2],
                            Long.parseLong(values[3])
                    ));
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

    public List<RecipeNames> getRecipeNames() {
        List<RecipeNames> names = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(RECIPE_FILE))) {
            String line = br.readLine();

            do {
                if (!line.toLowerCase(Locale.ROOT).contains("name_es")) {
                    String[] values = line.split(",");
                    names.add(new RecipeNames(
                            values[0], values[1], values[2],
                            values[3], values[4], values[5]
                    ));
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return names;
    }

}
