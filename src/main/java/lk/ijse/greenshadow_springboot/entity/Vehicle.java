package lk.ijse.greenshadow_springboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



    @Entity
    @Table(name = "vehicle")
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public class Vehicle {
        @Id
        private String vehicleId;
        private String plateNumber;
        private String category;
        private String fuelType;
        private String status;
        private String remarks;

        @ManyToOne
        @JoinColumn(name = "staffId")
        private Staff staff;

    }

