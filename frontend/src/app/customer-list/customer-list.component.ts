import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Customer} from "../api/models/customer";
import {CustomerService} from "../api/services/customer.service";
import {Columns, Config, DefaultConfig} from "ngx-easy-table";

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {

  @ViewChild('actionTpl', { static: true }) actionTpl: TemplateRef<any>;

  customers: Customer[];
  configuration: Config;
  columns: Columns[];

  constructor(private customerService: CustomerService) {
  }

  ngOnInit(): void {
    this.configuration = { ...DefaultConfig };
    this.configuration.paginationEnabled = false;
    this.configuration.resizeColumn = true;
    this.configuration.fixedColumnWidth = false;
    this.columns = [
      { key: 'firstName', title: 'First Name' },
      { key: 'lastName', title: 'Last Name' },
      { key: 'birthdate', title: 'Birthdate' },
      { key: 'details', title: 'Details', cellTemplate: this.actionTpl },
    ];
    this.getCustomers();
  }

  getCustomers(): void {
    this.customerService.getAllCustomers()
        .subscribe(customers => (this.customers = customers));
  }
}