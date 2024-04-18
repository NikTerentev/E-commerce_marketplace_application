package org.example.lab_1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories", schema = "public", catalog = "e-commerce")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность категории")
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    @Schema(description = "Идентификатор", example = "1")
    private Integer id;

    @Column(name = "name", nullable = false, length = 450)
    @Schema(description = "Название", example = "Смартфоны")
    private String name;

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
