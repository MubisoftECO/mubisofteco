package org.eco.mubisoft.generator.connection;

import org.eco.mubisoft.data.product.model.ProductTypeNames;
import org.eco.mubisoft.data.recipe.model.RecipeNames;
import org.eco.mubisoft.data.user.model.AutonomousCommunity;
import org.eco.mubisoft.data.user.model.City;
import org.eco.mubisoft.data.user.model.Province;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class FileReader {

    private static final String PRODUCT_TYPE_FILE = "files/product/ProductTypes.csv";
    private static final String RECIPE_FILE = "files/recipe/RecipeNames.csv";
    private static final String AC_FILE = "files/location/ACNames.csv";
    private static final String CITY_FILE = "files/location/CityNames.csv";
    private static final String PROVINCE_FILE = "files/location/ProvinceNames.csv";

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

    public List<City> getCities() {
        Map<Long, Province> provinces = this.getProvinces();
        List<City> cities = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(CITY_FILE))) {
            String line = br.readLine();
            long id = 1L;

            do {
                if (!line.toLowerCase(Locale.ROOT).contains("id")) {
                    String[] values = line.split(",");
                    cities.add(
                            new City(
                                    id++,
                                    provinces.get(Long.parseLong(values[0])),
                                    Integer.parseInt(values[1]),
                                    Integer.parseInt(values[2]),
                                    values[3]
                            )
                    );
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    private Map<Long, Province> getProvinces() {
        Map<Long, AutonomousCommunity> communities = this.getAutonomousCommunities();
        Map<Long, Province> provinces = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(PROVINCE_FILE))) {
            String line = br.readLine();

            do {
                if (!line.toLowerCase(Locale.ROOT).contains("id")) {
                    String[] values = line.split(",");
                    provinces.put(
                            Long.parseLong(values[0]),
                            new Province(
                                    Long.parseLong(values[0]),
                                    communities.get(Long.parseLong(values[1])),
                                    values[2]
                            )
                    );
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return provinces;
    }

    private Map<Long, AutonomousCommunity> getAutonomousCommunities() {
        Map<Long, AutonomousCommunity> autonomousCommunities = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new java.io.FileReader(AC_FILE))) {
            String line = br.readLine();

            do {
                if (!line.toLowerCase(Locale.ROOT).contains("id")) {
                    String[] values = line.split(",");
                    autonomousCommunities.put(
                            Long.parseLong(values[0]),
                            new AutonomousCommunity(Long.parseLong(values[0]), values[1])
                    );
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return autonomousCommunities;
    }

}
