package com.fakegmbh.leasingapp.vehicle;

import com.fakegmbh.leasingapp.vehicle.model.VehicleDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = {"/api/vehicles"}, produces = APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleDto createVehicle(@Valid @RequestBody final VehicleDto vehicle) {
        return vehicleService.createVehicle(vehicle);
    }

    @PutMapping(value = "/{vehicleId}", consumes = APPLICATION_JSON_VALUE)
    public VehicleDto updateVehicle(@PathVariable final Long vehicleId,
                                    @Valid @RequestBody final VehicleDto vehicle) {
        return vehicleService.updateVehicle(vehicleId, vehicle);
    }

    @GetMapping("/{vehicleId}")
    public VehicleDto getVehicle(@PathVariable final Long vehicleId) {
        return vehicleService.getVehicle(vehicleId);
    }

    @GetMapping
    public List<VehicleDto> getAllVehicles() {
        return vehicleService.getVehicles();
    }
}