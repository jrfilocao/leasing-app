package com.fakegmbh.leasingapp.vehicletype.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;

@Entity
@Table(name = VehicleTypeEntity.VEHICLE_TYPE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleTypeEntity {

    protected static final String VEHICLE_TYPE_TABLE = "vehicle_type";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleTypeId;

    @NotEmpty
    private String brand;

    @NotEmpty
    private String model;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

}