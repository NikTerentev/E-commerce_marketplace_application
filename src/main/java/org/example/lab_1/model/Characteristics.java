package org.example.lab_1.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Сущность характеристик")
public class Characteristics {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    @Schema(description = "Идентификатор", example = "1")
    private Integer id;

    @Column(name = "release_date", nullable = true)
    @Schema(description = "Дата выхода", example = "2024-01-06")
    private Date releaseDate;

    @Column(name = "ram_volume", nullable = true)
    @Schema(description = "Количество оперативной памяти", example = "6")
    private Integer ramVolume;

    @Column(name = "storage_volume", nullable = true)
    @Schema(description = "Количество постоянной памяти", example = "128")
    private Integer storageVolume;

    @Column(name = "diagonal", nullable = true, precision = 2)
    @Schema(description = "Диагональ", example = "6.80")
    private BigDecimal diagonal;

    @Column(name = "processor", nullable = true, length = 255)
    @Schema(description = "Процессор", example = "Qualcomm Snapdragon 8 Gen 3")
    private String processor;


    @Column(name = "weight", nullable = true, precision = 2)
    @Schema(description = "Вес", example = "232.00")
    private BigDecimal weight;


    @Override
    public String toString() {
        return "Characteristics{" +
                "id=" + id +
                ", releaseDate=" + releaseDate +
                ", ramVolume=" + ramVolume +
                ", storageVolume=" + storageVolume +
                ", diagonal=" + diagonal +
                ", processor='" + processor + '\'' +
                ", weight=" + weight +
                '}';
    }

}
