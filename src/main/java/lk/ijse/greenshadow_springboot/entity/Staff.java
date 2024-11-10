package lk.ijse.greenshadow_springboot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "staff")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Staff {
        @Id
        private String staffId;
        private String firstName;
        private String lastName;
        private String email;
        private Date dob;
        private String address;
        private String contact;
        private Date joinDate;
        @Enumerated(EnumType.STRING)
        private Role role;


        @ManyToMany
        @JoinTable(
                name = "staff_fields_detail",
                joinColumns = @JoinColumn(name = "staff_id"),
                inverseJoinColumns = @JoinColumn(name = "field_id")
        )
        private List<Field> fields;

        @OneToMany(mappedBy = "staff")
        private List<Vehicle> vehicles;


    }


