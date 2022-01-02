package org.eco.mubisoft.data.recipe.dao;

import org.eco.mubisoft.data.recipe.model.Flag;
import org.eco.mubisoft.data.recipe.model.Recipe;
import org.eco.mubisoft.data.recipe.model.Step;
import org.eco.mubisoft.generator.connection.MySQLConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        return null;
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
    public void insertRecipe(Recipe recipe) {

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

    }

    @Override
    public void deleteRecipeSteps() {
        String sqlQuery = "DELETE FROM step";
        MySQLConfig.executeDelete(mySQLConfig, sqlQuery);
    }

}
