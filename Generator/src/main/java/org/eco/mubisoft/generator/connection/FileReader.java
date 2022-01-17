package org.eco.mubisoft.generator.connection;

import org.eco.mubisoft.generator.data.product.domain.model.ProductTypeNames;
import org.eco.mubisoft.generator.data.recipe.domain.model.RecipeNames;
import org.eco.mubisoft.generator.data.user.domain.model.AutonomousCommunity;
import org.eco.mubisoft.generator.data.user.domain.model.City;
import org.eco.mubisoft.generator.data.user.domain.model.Province;
import org.eco.mubisoft.generator.data.user.domain.repo.AutonomousCommunityRepository;
import org.eco.mubisoft.generator.data.user.domain.repo.CityRepository;
import org.eco.mubisoft.generator.data.user.domain.repo.ProvinceRepository;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileReader {

    private static final String PRODUCT_TYPE_FILE = "files/product/ProductTypes.csv";
    private static final String RECIPE_FILE = "files/recipe/RecipeNames.csv";
    private static final String AC_FILE = "files/location/ACNames.csv";
    private static final String CITY_FILE = "files/location/CityNames.csv";
    private static final String PROVINCE_FILE = "files/location/ProvinceNames.csv";

    public List<ProductTypeNames> getProductTypeNames() {
        List<ProductTypeNames> names = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(PRODUCT_TYPE_FILE), StandardCharsets.UTF_8)
        )) {
            String line = br.readLine();

            do {
                if (!line.contains("name_es")) {
                    String[] values = line.split(",");
                    names.add(new ProductTypeNames(
                            Long.parseLong(values[3]), values[0], values[1], values[2]
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

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(RECIPE_FILE), StandardCharsets.UTF_8)
        )) {
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

    public void getCities(Map<Long, Province> provinces, CityRepository cityRepo) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(CITY_FILE), StandardCharsets.UTF_8)
        )) {
            String line = br.readLine();

            do {
                if (!line.toLowerCase(Locale.ROOT).contains("id")) {
                    String[] values = line.split(",");
                    cityRepo.save(new City(
                                    provinces.get(Long.parseLong(values[0])),
                                    Integer.parseInt(values[1]),
                                    Integer.parseInt(values[2]),
                                    values[3]
                    ));
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Long, Province> getProvinces(Map<Long, AutonomousCommunity> communities, ProvinceRepository provinceRepo) {
        Map<Long, Province> provinces = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(PROVINCE_FILE), StandardCharsets.UTF_8)
        )) {
            String line = br.readLine();

            do {
                if (!line.toLowerCase(Locale.ROOT).contains("id")) {
                    String[] values = line.split(",");
                    Province province = provinceRepo.save(new Province(
                            communities.get(Long.parseLong(values[1])),
                            values[2]
                    ));
                    provinces.put(Long.parseLong(values[0]), province);
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return provinces;
    }

    public Map<Long, AutonomousCommunity> getAutonomousCommunities(AutonomousCommunityRepository acRepo) {
        Map<Long, AutonomousCommunity> autonomousCommunities = new HashMap<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(AC_FILE), StandardCharsets.UTF_8)
        )) {
            String line = br.readLine();

            do {
                if (!line.toLowerCase(Locale.ROOT).contains("id")) {
                    String[] values = line.split(",");
                    AutonomousCommunity ac = acRepo.save(new AutonomousCommunity(values[1]));
                    autonomousCommunities.put(Long.parseLong(values[0]), ac);
                }
            } while ((line = br.readLine()) != null);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return autonomousCommunities;
    }

}
