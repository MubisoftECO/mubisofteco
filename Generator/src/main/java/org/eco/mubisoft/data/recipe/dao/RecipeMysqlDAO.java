package org.eco.mubisoft.data.recipe.dao;

import org.eco.mubisoft.data.product.model.ProductType;
import org.eco.mubisoft.data.recipe.model.Flag;
import org.eco.mubisoft.data.recipe.model.Recipe;
import org.eco.mubisoft.data.recipe.model.Step;
import org.eco.mubisoft.generator.connection.MySQLConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeMysqlDAO implements RecipeDAO {

    private final MySQLConfig mySQLConfig;

    public RecipeMysqlDAO() {
        mySQLConfig = MySQLConfig.getInstance();
    }

    @Override
    public void insertRecipeFlags(List<Flag> flagList) {
        String sqlQuery = "INSERT INTO flag (id, name_en, name_es, name_eu) VALUES (?, ?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            for (long id = 0; id < flagList.size(); id++) {
                pStm = connection.prepareStatement(sqlQuery);
                pStm.setLong(1, id + 1);
                pStm.setString(2, flagList.get((int) id).getName_en());
                pStm.setString(3, flagList.get((int) id).getName_es());
                pStm.setString(4, flagList.get((int) id).getName_eu());
                pStm.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public List<Flag> getRecipeFlags() {
        List<Flag> flagList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM flag";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            ResultSet set = pStm.executeQuery();

            while(set.next()) {
                flagList.add(new Flag(
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
        return flagList;
    }

    @Override
    public void setRecipeFlag(Long recipeID, Collection<Flag> flagList) {
        String sqlQuery = "INSERT INTO recipe_recipe_flags (recipe_id, recipe_flags_id) VALUES (?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        for (Flag flag : flagList) {
            try {
                pStm = connection.prepareStatement(sqlQuery);
                pStm.setLong(1, recipeID);
                pStm.setLong(2, flag.getId());
                pStm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        mySQLConfig.disconnect(connection, pStm);
    }

    @Override
    public void deleteRecipeFlags() {
        // Delete flag relations
        String sqlQuery = "DELETE FROM recipe_recipe_flags";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);

        // Delete flags
        sqlQuery = "DELETE FROM flag";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public void setRecipeIngredients(Long recipeID, Collection<ProductType> ingredients) {
        String sqlQuery = "INSERT INTO recipe_ingredients (recipe_id, ingredients_id) VALUES (?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        for (ProductType ingredient : ingredients) {
            try {
                pStm = connection.prepareStatement(sqlQuery);
                pStm.setLong(1, recipeID);
                pStm.setLong(2, ingredient.getId());
                pStm.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        mySQLConfig.disconnect(connection, pStm);
    }

    @Override
    public void deleteRecipeIngredients() {
        String sqlQuery = "DELETE FROM recipe_ingredients";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public void insertRecipe(Recipe recipe) {
        String sqlQuery =
                "INSERT INTO recipe (id, title, description, language, time_in_minutes, author_id) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            pStm.setLong(1, recipe.getId());
            pStm.setString(2, recipe.getTitle());
            pStm.setString(3, recipe.getDescription());
            pStm.setString(4, recipe.getLanguage());
            pStm.setInt(5, recipe.getTimeInMinutes());
            pStm.setLong(6, recipe.getAuthor().getId());
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public void deleteRecipes() {
        // Delete ingredient relations
        String sqlQuery = "DELETE FROM recipe_ingredients";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);

        // Delete recipes
        sqlQuery = "DELETE FROM recipe";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public void insertRecipeStep(Step step) {
        String sqlQuery = "INSERT INTO step (id, description, step_num, recipe_id) VALUES (?, ?, ?, ?)";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            pStm.setLong(1, step.getId());
            pStm.setString(2, step.getDescription());
            pStm.setInt(3, step.getStepNum());
            pStm.setLong(4, step.getRecipe());
            pStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
    }

    @Override
    public void deleteRecipeSteps() {
        String sqlQuery = "DELETE FROM step";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

    @Override
    public long getRecipeID() {
        String sqlQuery = "SELECT id FROM recipe ORDER BY id DESC";
        Connection connection = mySQLConfig.connect();
        PreparedStatement pStm = null;
        long id = 1L;

        try {
            pStm = connection.prepareStatement(sqlQuery);
            ResultSet set = pStm.executeQuery();

            if (set.next()) {
                id = set.getLong(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySQLConfig.disconnect(connection, pStm);
        }
        return id;
    }

}
