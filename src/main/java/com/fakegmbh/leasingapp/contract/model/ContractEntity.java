package com.fakegmbh.leasingapp.contract.model;

import com.fakegmbh.leasingapp.customer.model.CustomerEntity;
import com.fakegmbh.leasingapp.vehicle.model.VehicleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = ContractEntity.CONTRACT_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractEntity {

    protected static final String CONTRACT_TABLE = "leasing_contract";
    private static final String VEHICLE_ID = "vehicle_id";
    private static final String CUSTOMER_ID = "customer_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contractId;

    @NotEmpty
    private String contractNumber;

    @NotNull
    private BigDecimal monthlyRate;

    @NotNull
    @OneToOne
    @JoinColumn(name= VEHICLE_ID)
    private VehicleEntity vehicle;

    @NotNull
    @ManyToOne
    @JoinColumn(name= CUSTOMER_ID)
    private CustomerEntity customer;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;
}