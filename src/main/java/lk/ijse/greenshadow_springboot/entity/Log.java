package lk.ijse.greenshadow_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "log")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log {
    @Id
    private String logId;
    private String logDetails;
    private Date date;
    @Column(columnDefinition = "LONGTEXT")
    private String image2;

    @ManyToMany
    @JoinTable(
            name = "staff_logs_details",
            joinColumns = @JoinColumn(name = "logId"),
            inverseJoinColumns = @JoinColumn(name = "staffId")
    )
    private List<Staff> staffIds ;


    @ManyToMany
    @JoinTable(
            name = "field_logs",
            joinColumns = @JoinColumn(name = "logId"),
            inverseJoinColumns = @JoinColumn(name = "FieldId")
    )
    private List<Field> fieldIds;

    @ManyToMany
    @JoinTable(
            name = "crop_logs",
            joinColumns = @JoinColumn(name = "logId"),
            inverseJoinColumns = @JoinColumn(name = "cropId")
    )
    private List<Crop> cropIds;

}

