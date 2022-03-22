import { Component, OnInit } from '@angular/core';

import { Vehicle } from '../api/models/vehicle'
import { VehicleService } from '../api/services/vehicle.service'

@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css']
})
export class VehicleListComponent implements OnInit {

  vehicles: Vehicle[] = [];

  constructor(private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.getVehicles();
  }

  getVehicles(): void {
    this.vehicleService.getAllVehicles()
                       .subscribe(vehicles => (this.vehicles = vehicles));
  }
}
