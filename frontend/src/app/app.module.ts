import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'ngx-easy-table';

import { AppComponent } from './app.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { HttpClientModule } from '@angular/common/http';
import { VehicleListComponent } from './vehicle-list/vehicle-list.component';
import { OverviewComponent } from './overview/overview.component';
import { VehicleDetailsComponent } from './vehicle-details/vehicle-details.component';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerDetailsComponent } from './customer-details/customer-details.component';
import { ContractListComponent } from './contract-list/contract-list.component';
import { ContractDetailsComponent } from './contract-details/contract-details.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatNativeDateModule } from "@angular/material/core";
import { MatInputModule } from "@angular/material/input";

@NgModule({
  imports: [
    BrowserModule,
    HttpClientModule,
    ReactiveFormsModule,
    TableModule,
    RouterModule.forRoot([
      {path: '', component: OverviewComponent},
      {path: 'vehicles/:vehicleId', component: VehicleDetailsComponent},
      {path: 'customers/:customerId', component: CustomerDetailsComponent},
      {path: 'contracts/:contractId', component: ContractDetailsComponent},
    ]),
    FormsModule,
    BrowserAnimationsModule,
    MatDatepickerModule,
    MatFormFieldModule,
    MatNativeDateModule,
    MatInputModule,
    BrowserAnimationsModule
  ],
  declarations: [
    AppComponent,
    TopBarComponent,
    VehicleListComponent,
    OverviewComponent,
    VehicleDetailsComponent,
    CustomerListComponent,
    CustomerDetailsComponent,
    ContractListComponent,
    ContractDetailsComponent,
  ],
  providers: [
    MatDatepickerModule,
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }


/*
Copyright Google LLC. All Rights Reserved.
Use of this source code is governed by an MIT-style license that
can be found in the LICENSE file at https://angular.io/license
*/