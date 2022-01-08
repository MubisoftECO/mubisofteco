package org.eco.mubisoft.data.recipe.model;

public class Flag {

    private Long id;
    private String name_en;
    private String name_es;
    private String name_eu;

    public Flag(Long id, String name_en, String name_es, String name_eu) {
        this.id = id;
        this.name_en = name_en;
        this.name_es = name_es;
        this.name_eu = name_eu;
    }

    public Flag(String name_en, String name_es, String name_eu) {
        this.name_en = name_en;
        this.name_es = name_es;
        this.name_eu = name_eu;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName_en() {
        return name_en;
    }

    public String getName_es() {
        return name_es;
    }

    public String getName_eu() {
        return name_eu;
    }
}
