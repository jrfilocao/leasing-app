import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { TableModule } from 'ngx-easy-table';

import { AppComponent } from './app.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import { ProductListComponent } from './product-list/product-list.component';
import { ProductAlertsComponent } from './product-alerts/product-alerts.component';
import { ProductDetailsComponent } from './product-details/product-details.component';
import { CartComponent } from './cart/cart.component';
import { HttpClientModule } from '@angular/common/http';
import { ShippingComponent } from './shipping/shipping.component';
import { VehicleListComponent } from './vehicle-list/vehicle-list.component';
import { OverviewComponent } from './overview/overview.component';
import { VehicleDetailsComponent } from './vehicle-details/vehicle-details.component';
import { CustomerListComponent } from './customer-list/customer-list.component';
import { CustomerDetailsComponent } from './customer-details/customer-details.component';

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
      {path: 'products/:productId', component: ProductDetailsComponent},
      {path: 'cart', component: CartComponent},
      {path: 'shipping', component: ShippingComponent},
    ]),
    FormsModule
  ],
  declarations: [
    AppComponent,
    TopBarComponent,
    ProductListComponent,
    ProductAlertsComponent,
    ProductDetailsComponent,
    CartComponent,
    ShippingComponent,
    VehicleListComponent,
    OverviewComponent,
    VehicleDetailsComponent,
    CustomerListComponent,
    CustomerDetailsComponent
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