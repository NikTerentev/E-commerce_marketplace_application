package org.example.lab_1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products", schema = "public", catalog = "e-commerce")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность товара")
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    @Schema(description = "Идентификатор", example = "1")
    private Integer id;

    @Column(name = "name", nullable = false, length = 450)
    @Schema(description = "Название", example = "Товар")
    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "price", nullable = false, precision = 0)
    @Schema(description = "Цена", example = "12000")
    private Double price;

    @Column(name = "image", nullable = false, length = 450)
    @Schema(description = "Название изображения")
    private String image;

    @ManyToOne
    @JoinColumn(name="characteristics_id", nullable = false)
    private Characteristics characteristics;
}
