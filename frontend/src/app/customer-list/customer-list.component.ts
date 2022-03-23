import { Component, OnInit } from '@angular/core';
import {Customer} from "../api/models/customer";
import {CustomerService} from "../api/services/customer.service";

@Component({
  selector: 'app-customer-list',
  templateUrl: './customer-list.component.html',
  styleUrls: ['./customer-list.component.css']
})
export class CustomerListComponent implements OnInit {

  customers: Customer[] = [];

  constructor(private customerService: CustomerService) { }

  ngOnInit(): void {
    this.getCustomers();
  }

  getCustomers(): void {
    this.customerService.getAllCustomers()
        .subscribe(customers => (this.customers = customers));
  }
}
