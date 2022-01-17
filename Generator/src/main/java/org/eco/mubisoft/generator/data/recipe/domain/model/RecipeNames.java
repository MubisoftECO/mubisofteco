package org.eco.mubisoft.generator.data.recipe.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecipeNames {

    private String name_en;
    private String name_es;
    private String name_eu;
    private String article_en;
    private String article_es;
    private String article_eu;

    public void setName_en(String product) {
        switch (this.article_en) {
            case "before":
                this.name_en = product + " " + this.name_en;
                break;
            case "after":
                this.name_en = this.name_en + " " + product;
                break;
            case "with":
                this.name_en = this.name_en + " with " + product;
                break;
            default:
                this.name_en = product;
                break;
        }
    }

    public void setName_es(String product) {
        switch (this.article_es) {
            case "de":
            case "con":
                this.name_es = this.name_es + " " + this.article_es + " " + product;
                break;
            case "-":
            case "al":
            case "a la":
                this.name_es = product + " " +
                        ((!this.article_es.equals("-")) ? this.article_es : "")
                        + " " + this.name_es;
                break;
        }
    }

    public void setName_eu(String product) {
        switch (this.article_eu) {
            case "before":
                this.name_eu = product + " " + this.name_eu;
                break;
            case "after":
                this.name_eu = this.name_eu + " " + product;
                break;
            case "rekin":
                this.name_eu = this.name_eu + " " + product + this.article_eu;
                break;
        }
    }

    public String getNameForLanguage(String language, String product) {
        switch (language) {
            case "en":
                this.setName_en(product);
                return this.name_en;
            case "es":
                this.setName_es(product);
                return this.name_es;
            case "eu":
                this.setName_eu(product);
                return this.name_eu;
            default: return null;
        }
    }

}
