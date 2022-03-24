import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Columns, Config, DefaultConfig} from "ngx-easy-table";
import {Contract} from "../api/models/contract";
import {ContractService} from "../api/services/contract.service";

@Component({
  selector: 'app-contract-list',
  templateUrl: './contract-list.component.html',
  styleUrls: ['./contract-list.component.css']
})
export class ContractListComponent implements OnInit {
  @ViewChild('actionTpl', { static: true }) actionTpl: TemplateRef<any>;
  @ViewChild('customerTpl', { static: true }) customerTpl: TemplateRef<any>;
  @ViewChild('vehicleTpl', { static: true }) vehicleTpl: TemplateRef<any>;

  contracts: Contract[] = [];
  configuration: Config;
  columns: Columns[];

  constructor(private contractService: ContractService) { }

  ngOnInit(): void {
    this.configuration = { ...DefaultConfig };
    this.configuration.paginationEnabled = false;
    this.configuration.resizeColumn = true;
    this.configuration.fixedColumnWidth = false;
    this.columns = [
      { key: 'contractNumber', title: 'Contract No' },
      { key: 'customer', title: 'Customer', cellTemplate: this.customerTpl },
      { key: 'vehicle', title: 'Vehicle', cellTemplate: this.vehicleTpl },
      { key: 'vehicle.vin', title: 'VIN'},
      { key: 'monthlyRate', title: 'Monthly Rate' },
      { key: 'vehicle.price', title: 'Vehicle Price' },
      { key: 'details', title: 'Details', cellTemplate: this.actionTpl },
    ];
    this.getContracts();
  }

  getContracts(): void {
    this.contractService.getAllContracts()
        .subscribe(contracts => (this.contracts = contracts));
  }
}