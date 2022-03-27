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

  selectedCountry: any;

  models : string[] = [];

  // TODO these values should actually come from the database
  brands = [{
    id: 1, name: 'BMW', models: ['X1', 'X2', 'X3', 'X4', 'X5']
    },
    {
      id: 2, name: 'Mercedes-Benz', models: ['GT', 'C63', 'GLC63', 'A140']
    },
    {
      id: 3, name: 'VW', models: ['Golf', 'Jetta', 'Lamando', 'Passat']
    },
  ];

  constructor(
      private route: ActivatedRoute,
      private vehicleService: VehicleService,
      private location: Location
  ) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const productIdFromRoute = Number(routeParams.get('vehicleId'));
    this.vehicleService.getVehicle({vehicleId: productIdFromRoute})
                       .subscribe(vehicle => {
                         this.vehicle = vehicle;
                         this.models = this.brands.filter(x => x.name == this.vehicle!.brand)[0].models;
                       });
  }

  save(): void {
    this.vehicleService.updateVehicle({vehicleId: this.vehicle?.vehicleId!, body: this.vehicle!})
                       .subscribe(vehicle => this.vehicle = vehicle);
    this.goBack();
  }

  goBack(): void {
    this.location.back();
  }

  onChange(deviceValue: any) {
    this.models = this.brands.filter(x => x.name == deviceValue)[0].models;
    this.vehicle!.model = this.models[0];
  }
}