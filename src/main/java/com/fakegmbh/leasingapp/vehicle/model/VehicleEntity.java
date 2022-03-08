package com.fakegmbh.leasingapp.vehicle.model;

import com.fakegmbh.leasingapp.vehicletype.model.VehicleTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = VehicleEntity.VEHICLE_TYPE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleEntity {

    protected static final String VEHICLE_TYPE_TABLE = "vehicle";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;

    @NotNull
    @ManyToOne
    private VehicleTypeEntity vehicleType;

    @Min(1500)
    private int modelYear;

    @NotEmpty
    private String vin;

    @NotNull
    private BigDecimal price;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}