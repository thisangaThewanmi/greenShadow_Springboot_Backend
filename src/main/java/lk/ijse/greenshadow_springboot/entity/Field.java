package lk.ijse.greenshadow_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.geo.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "feild")

public class Field {
    @Id
    private String FieldId;
    private String name;
    private Point location;
    private Double size;
    @Column(columnDefinition = "LONGTEXT")
    private String image1;
    @Column(columnDefinition = "LONGTEXT")
    private String image2;

    @ManyToMany
    @JoinTable(
            name = "field-staff-details",
            joinColumns = @JoinColumn(name = "FieldId"),
            inverseJoinColumns = @JoinColumn(name = "staffId")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)  // This will delete the entry in the join table when a Staff is deleted

    private List<Staff> staffMembers;

    @OneToMany(mappedBy = "field")
    private List<Equipment> equipments;


    @OneToMany(mappedBy = "field", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Crop> crops ;

    @ManyToMany(mappedBy = "fieldIds", cascade = CascadeType.ALL)
    private List<Log> logs;
}
