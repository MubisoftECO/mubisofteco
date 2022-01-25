package org.eco.mubisoft.good_and_cheap.product.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductDto;
import org.eco.mubisoft.good_and_cheap.product.dto.ProductSoldOnlyDto;
import org.eco.mubisoft.good_and_cheap.user.domain.model.AppUser;

import javax.persistence.*;
import java.util.Date;

@SqlResultSetMapping(
        name = "ProductDetailsMapping",
        classes = @ConstructorResult(
                targetClass = ProductDto.class,
                columns = {
                        @ColumnResult(name = "nameEn"),
                        @ColumnResult(name = "nameEs"),
                        @ColumnResult(name = "nameEu"),
                        @ColumnResult(name = "total"),
                        @ColumnResult(name = "reason")
                }
        )
)

@SqlResultSetMapping(
        name = "ProductMostLessMapping",
        classes = @ConstructorResult(
                targetClass = ProductSoldOnlyDto.class,
                columns = {
                        @ColumnResult(name = "nameEn"),
                        @ColumnResult(name = "nameEs"),
                        @ColumnResult(name = "nameEu"),
                        @ColumnResult(name = "price"),
                        @ColumnResult(name = "quantity")
                }
        )
)

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id")
    private Long id;
    private String nameEs;
    private String nameEn;
    private String nameEu;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductType productType;
    private Double price;
    private Double quantity;
    private Date publishDate;
    private Date expirationDate;
    private Date removedDate;
    private String removeReason;
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser vendor;

}
