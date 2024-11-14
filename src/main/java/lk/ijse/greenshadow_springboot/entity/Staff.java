package lk.ijse.greenshadow_springboot.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
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
        private String designation;
        private String gender;
        private LocalDate joinedDate;
        private LocalDate dob;
        private String addressLine1;
        private String addressLine2;
        private String addressLine3;
        private String addressLine4;
        private String addressLine5;
        private String contactNo;
        @Column(unique = true)
        private String staffEmail;
        @Enumerated(EnumType.STRING)
        private Role role;

        @OneToOne(mappedBy = "staff")
        private User user;

        @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
        private List<Vehicle> vehicles;

        @ManyToMany(mappedBy = "staffMembers", cascade = CascadeType.ALL)
        private List<Field> fields;

        @OneToMany(mappedBy = "staff", cascade = CascadeType.ALL)
        private List<Equipment> equipments;

        @ManyToMany(mappedBy = "staffLogs", cascade = CascadeType.ALL)
        private List<Log> logs;

}


