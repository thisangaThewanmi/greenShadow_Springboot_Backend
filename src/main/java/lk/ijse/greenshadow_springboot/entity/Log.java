package lk.ijse.greenshadow_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.HashSet;
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
            joinColumns = @JoinColumn(name = "log_id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private Set<Staff> staffLogs = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "field_logs",
            joinColumns = @JoinColumn(name = "log_id"),
            inverseJoinColumns = @JoinColumn(name = "field_id")
    )
    private Set<Field> fieldLogs = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "crop_logs",
            joinColumns = @JoinColumn(name = "log_id"),
            inverseJoinColumns = @JoinColumn(name = "crop_id")
    )
    private Set<Crop> cropLogs = new HashSet<>();

}

