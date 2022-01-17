package org.eco.mubisoft.generator.data.product.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eco.mubisoft.generator.data.product.domain.model.*;
import org.eco.mubisoft.generator.data.product.domain.repo.ProductFamilyRepository;
import org.eco.mubisoft.generator.data.product.domain.repo.ProductRepository;
import org.eco.mubisoft.generator.data.product.domain.repo.ProductTypeRepository;
import org.eco.mubisoft.generator.data.user.domain.model.AppUser;
import org.eco.mubisoft.generator.data.user.domain.repo.UserRepository;
import org.eco.mubisoft.generator.connection.FileReader;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceFacade implements ProductService {

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

    private final FileReader fileReader = new FileReader();
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductFamilyRepository productFamilyRepository;

    @Override
    public void generateProductTypes() {
        List<ProductTypeNames> typeNames = fileReader.getProductTypeNames();
        List<ProductFamily> productFamilies = this.getProductFamilies();
        AtomicLong id = new AtomicLong(1L);

        typeNames.forEach(name -> productTypeRepository.save(new ProductType(
                id.getAndIncrement(),
                name.getNameEs(), name.getNameEn(), name.getNameEu(),
                MeasurementUnit.getRandom().toString(),
                productFamilies.get((int) (name.getId() - 1))
        )));
    }

    @Override
    public void generateProductFamilies() {
        for (int i = 0; i < families_en.size(); i++) {
            productFamilyRepository.save(new ProductFamily(
                    i + 1L, families_en.get(i), families_es.get(i), families_eu.get(i)
            ));
        }
    }

    @Override
    public void generateProducts(int quantity) {
        List<ProductType> productTypes = this.getProductTypes();
        List<AppUser> appUsers = userRepository.findAll();
        Random random = new Random();

        for (long i = 0; i < quantity; i++) {
            ProductType type = productTypes.get(random.nextInt(productTypes.size()));
            int year = random.nextInt(5) + 2018;
            int month = random.nextInt(12);
            int day = random.nextInt(28) + 1;

            productRepository.save(new Product(
                    type.getNameEn(), type.getNameEs(), type.getNameEu(),
                    type, random.nextDouble() * 20, random.nextDouble() * 10,
                    new GregorianCalendar(year, month, day).getTime(),
                    new GregorianCalendar(year, month, day + random.nextInt(3)).getTime(),
                    new GregorianCalendar(year, month, day + random.nextInt(3)).getTime(),
                    RemoveReason.getRandom().toString(), appUsers.get(random.nextInt(appUsers.size()))
            ));

            // Display current %
            if (i / (quantity / 10) > 0 && i % (quantity / 10) == 0) {
                log.info("Generation at " + (i / (quantity / 10) * 10) + "%.");
            }
        }
    }

    @Override
    public List<ProductType> getProductTypes() {
        return productTypeRepository.findAll();
    }

    @Override
    public List<ProductFamily> getProductFamilies() {
        return productFamilyRepository.findAll();
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
        productTypeRepository.deleteAll();
        productFamilyRepository.deleteAll();
    }
}
