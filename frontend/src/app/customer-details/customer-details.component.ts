import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {Customer} from "../api/models/customer";
import {CustomerService} from "../api/services/customer.service";

@Component({
  selector: 'app-customer-details',
  templateUrl: './customer-details.component.html',
  styleUrls: ['./customer-details.component.css']
})
export class CustomerDetailsComponent implements OnInit {

  customer: Customer | undefined;

  constructor(
      private route: ActivatedRoute,
      private customerService: CustomerService,
      private location: Location
  ) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const customerIdFromRoute = Number(routeParams.get('customerId'));
    this.customerService.getCustomer({customerId: customerIdFromRoute})
                        .subscribe(customer => this.customer = customer);
  }

  save(): void {
    this.customerService.updateCustomer({customerId: this.customer?.customerId!, body: this.customer!})
                        .subscribe(customer => this.customer = customer);
    this.goBack();
  }

  goBack(): void {
    this.location.back();
  }
}
