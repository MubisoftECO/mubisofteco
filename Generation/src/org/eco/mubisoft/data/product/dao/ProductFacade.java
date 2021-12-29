package org.eco.mubisoft.data.product.dao;

import org.eco.mubisoft.data.product.model.*;
import org.eco.mubisoft.data.user.dao.UserFacade;
import org.eco.mubisoft.data.user.model.AppUser;
import org.eco.mubisoft.generator.connection.FileReader;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ProductFacade {

    private final ProductDAO productDAO = new ProductMysqlDAO();
    private final FileReader fileReader = new FileReader();
    private final UserFacade userFacade = new UserFacade();

    private final List<String> families_es = Arrays.asList(
            "Lacteos",
            "Origen Animal",
            "Tuberculos y Legumbres",
            "Verduras y Hortalizas",
            "Frutas",
            "Cereales y Azucares",
            "Grasas y Aceites"
    );

    private final List<String> families_en = Arrays.asList(
            "Dairies",
            "Animal Origin",
            "Tubers and Legumes",
            "Vegetables",
            "Fruits",
            "Cereals and Sugars",
            "Fats and Oils"
    );

    private final List<String> families_eu = Arrays.asList(
            "Esnekiak",
            "Animali Jatorria",
            "Tuberkuluak eta Lekaleak",
            "Barazkiak",
            "Fruta",
            "Zerealak eta Azukreak",
            "Gatzak eta Olioak"
    );

    public void generateProductFamilies() {
        List<ProductFamily> productFamilies = new ArrayList<>();

        for (int i = 0; i < families_en.size(); i++) {
            productFamilies.add(new ProductFamily(
                    i + 1L, families_en.get(i), families_es.get(i), families_eu.get(i)
            ));
        }
        productDAO.insertProductFamilies(productFamilies);
    }

    public List<ProductFamily> getProductFamilies() {
        return productDAO.getProductFamilies();
    }

    public void generateProductTypes() {
        List<ProductTypeNames> typeNames = fileReader.getProductTypeNames();
        List<ProductFamily> productFamilies = this.getProductFamilies();
        List<ProductType> productTypeList = new ArrayList<>();
        AtomicLong id = new AtomicLong(1L);

        typeNames.forEach(name -> productTypeList.add(new ProductType(
                id.getAndIncrement(),
                name.en, name.es, name.eu,
                MeasurementUnit.getRandom(),
                productFamilies.get((int) (name.id - 1))
        )));
        productDAO.insertProductTypes(productTypeList);
    }

    public List<ProductType> getProductTypes() {
        return productDAO.getProductTypes();
    }

    public void generateProducts(int quantity) {
        List<ProductType> productTypes = this.getProductTypes();
        List<AppUser> appUsers = userFacade.getAppUsers();
        long currentMaxID = productDAO.getProductID();
        Random random = new Random();

        for (long id = currentMaxID; id < quantity + currentMaxID; id++) {
            ProductType type = productTypes.get(random.nextInt(productTypes.size()));
            int year = random.nextInt(5) + 2018;
            int month = random.nextInt(12);
            int day = random.nextInt(28) + 1;

            productDAO.insertProduct(new Product(
                    id + 1, type.getName_en(), type.getName_es(), type.getName_eu(),
                    type, random.nextDouble() * 20, random.nextDouble() * 10,
                    new GregorianCalendar(year, month, day).getTime(),
                    new GregorianCalendar(year, month, day + random.nextInt(3)).getTime(),
                    new GregorianCalendar(year, month, day + random.nextInt(3)).getTime(),
                    RemoveReason.getRandom(), appUsers.get(random.nextInt(appUsers.size()))
            ));
        }
    }

    public void deleteAll() {
        productDAO.deleteProducts();
        productDAO.deleteProductTypes();
        productDAO.deleteProductFamilies();
    }

}
