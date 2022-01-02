package org.eco.mubisoft.data.product.dao;

import org.eco.mubisoft.data.product.model.MeasurementUnit;
import org.eco.mubisoft.data.product.model.Product;
import org.eco.mubisoft.data.product.model.ProductFamily;
import org.eco.mubisoft.data.product.model.ProductType;
import org.eco.mubisoft.generator.connection.MySQLConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductMysqlDAO implements ProductDAO {

    private final MySQLConfig mySQLConfig;

    public ProductMysqlDAO() {
        this.mySQLConfig = MySQLConfig.getInstance();
    }

    @Override
    public void insertProductFamilies(List<ProductFamily> productFamilyList) {
        String sqlQuery = "INSERT INTO product_family(id, name_es, name_eu, name_en) VALUES(?, ?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        // Remove the previous data
        this.deleteProductFamilies();

        try {
            for (ProductFamily family : productFamilyList) {
                pStm = connection.prepareStatement(sqlQuery);
                pStm.setLong(1, family.getId());
                pStm.setString(2, family.getName_es());
                pStm.setString(3, family.getName_eu());
                pStm.setString(4, family.getName_en());
                pStm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mySQLConfig.disconnect(connection, pStm);
    }

    @Override
    public List<ProductFamily> getProductFamilies() {
        List<ProductFamily> productFamilies = new ArrayList<>();
        String sqlQuery = "SELECT * FROM product_family";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            ResultSet set = pStm.executeQuery();

            while (set.next()) {
                productFamilies.add(new ProductFamily(
                        set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4)
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
        return productFamilies;
    }

    @Override
    public void deleteProductFamilies() {
        String sqlQuery = "DELETE FROM product_family";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public void insertProductTypes(List<ProductType> productTypeList) {
        String sqlQuery = "INSERT INTO " +
                "product_type(id, name_en, name_es, name_eu, measurement_unit, product_family_id)" +
                " VALUES(?, ?, ?, ?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        // Remove the previous data
        this.deleteProductTypes();

        try {
            for (ProductType type : productTypeList) {
                pStm = connection.prepareStatement(sqlQuery);
                pStm.setLong(1, type.getId());
                pStm.setString(2, type.getName_en());
                pStm.setString(3, type.getName_es());
                pStm.setString(4, type.getName_eu());
                pStm.setString(5, type.getMeasurementUnit());
                pStm.setLong(6, type.getProductFamily());
                pStm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public List<ProductType> getProductTypes() {
        List<ProductType> productTypes = new ArrayList<>();
        String sqlQuery =
                "SELECT pt.id, pt.name_en, pt.name_es, pt.name_eu, pt.measurement_unit, " +
                        "pf.id, pf.name_en, pf.name_es, pf.name_eu " +
                "FROM product_type pt " +
                        "JOIN product_family pf on pf.id = pt.product_family_id";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            ResultSet set = pStm.executeQuery();

            while (set.next()) {
                productTypes.add(new ProductType(
                        set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        MeasurementUnit.getUnit(set.getString(5)),
                        new ProductFamily(
                                set.getLong(6),
                                set.getString(7),
                                set.getString(8),
                                set.getString(9)
                        )
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
        return productTypes;
    }

    @Override
    public void deleteProductTypes() {
        String sqlQuery = "DELETE FROM product_type";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public void insertProduct(Product product) {
        String sqlQuery = "INSERT INTO product (" +
                "id, name_en, name_es, name_eu, quantity, price, publish_date, expiration_date, removed_date, " +
                "remove_reason, product_type_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            pStm.setLong(1, product.getId());
            pStm.setString(2, product.getName_en());
            pStm.setString(3, product.getName_es());
            pStm.setString(4, product.getName_eu());
            pStm.setDouble(5, product.getQuantity());
            pStm.setDouble(6, product.getPrice());
            pStm.setDate(7, new Date(product.getPublishDate().getTime()));
            pStm.setDate(8, new Date(product.getExpirationDate().getTime()));
            pStm.setDate(9, new Date(product.getRemovedDate().getTime()));
            pStm.setString(10, product.getRemoveReason());
            pStm.setLong(11, product.getProductType());
            // pStm.setLong(12, product.getVendor());
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public void deleteProducts() {
        String sqlQuery = "DELETE FROM product";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public long getProductID() {
        String sqlQuery = "SELECT id FROM product ORDER BY id DESC LIMIT 1";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;
        long id = 1;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            ResultSet set = pStm.executeQuery();

            if (set.next()) {
                id = set.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
        return id;
    }

}
