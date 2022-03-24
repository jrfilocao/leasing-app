import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';

import { Vehicle } from '../api/models/vehicle'
import { VehicleService } from '../api/services/vehicle.service'
import {Columns, Config, DefaultConfig} from "ngx-easy-table";

@Component({
  selector: 'app-vehicle-list',
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css']
})
export class VehicleListComponent implements OnInit {

  @ViewChild('actionTpl', { static: true }) actionTpl: TemplateRef<any>;

  vehicles: Vehicle[] = [];
  configuration: Config;
  columns: Columns[];

  constructor(private vehicleService: VehicleService) { }

  ngOnInit(): void {
    this.configuration = { ...DefaultConfig };
    this.configuration.paginationEnabled = false;
    this.configuration.resizeColumn = true;
    this.configuration.fixedColumnWidth = false;
    this.columns = [
      { key: 'brand', title: 'Brand' },
      { key: 'model', title: 'Model' },
      { key: 'modelYear', title: 'Model Year' },
      { key: 'vin', title: 'VIN' },
      { key: 'price', title: 'Price' },
      { key: 'details', title: 'Details', cellTemplate: this.actionTpl },
    ];
    this.getVehicles();
  }

  getVehicles(): void {
    this.vehicleService.getAllVehicles()
                       .subscribe(vehicles => (this.vehicles = vehicles));
  }
}
