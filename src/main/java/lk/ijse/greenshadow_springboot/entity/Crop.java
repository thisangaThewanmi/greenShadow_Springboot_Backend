package lk.ijse.greenshadow_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "crop")
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Crop {
    @Id
    private String cropId;

    private String commonName;
    private String specificName;
    private String category;
    private String season;
    @Column(columnDefinition = "LONGTEXT")
    private String image1;

    @ManyToOne
    @JoinColumn(name = "field_id")
    private Field field;
}
