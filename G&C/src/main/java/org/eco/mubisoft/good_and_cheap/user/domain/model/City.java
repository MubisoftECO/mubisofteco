package org.eco.mubisoft.good_and_cheap.user.domain.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class City {

    /**
     * <p><b>ID</b></p>
     * <p>ID of the City.</p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id")
    private Long id;

    /**
     * <p><b>Province</b></p>
     * <p>ID of the Province of the city.</p>
     */
    @ManyToOne
    @JoinColumn
    private Province province;

    /**
     * <p><b>cityCode</b></p>
     * <p>NOT UNIQUE, code of the city in the autonomous community. Each city has a code.
     * Two cities in different AC might have the same code.</p>
     */
    private int cityCode;

    /**
     * <p><b>CD</b></p>
     * <p>Contol Digit (Dígito de control). According to INE (Insituto Nacional de Estadística)
     * it is used to detect errors. INE has not published how to calculate it.</p>
     */
    private int CD;

    /**
     * <p><b>NAME</b></p>
     * <p>Name of the City.</p>
     */
    private String name;

}
