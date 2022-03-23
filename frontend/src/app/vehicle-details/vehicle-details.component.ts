import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

import {Vehicle} from "../api/models/vehicle";
import {VehicleService} from "../api/services/vehicle.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-vehicle-details',
  templateUrl: './vehicle-details.component.html',
  styleUrls: ['./vehicle-details.component.css']
})
export class VehicleDetailsComponent implements OnInit {

  vehicle: Vehicle | undefined;

  constructor(
      private route: ActivatedRoute,
      private vehicleService: VehicleService,
      private location: Location
  ) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const productIdFromRoute = Number(routeParams.get('vehicleId'));
    this.vehicleService.getVehicle({vehicleId: productIdFromRoute})
                       .subscribe(vehicle => this.vehicle = vehicle);
  }

  save(): void {
    this.vehicleService.updateVehicle({vehicleId: this.vehicle?.vehicleId!, body: this.vehicle!})
                       .subscribe(vehicle => this.vehicle = vehicle);
    this.goBack();
  }

  goBack(): void {
    this.location.back();
  }
}