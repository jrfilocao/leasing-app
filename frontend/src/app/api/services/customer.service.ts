/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

import { Customer } from '../models/customer';

@Injectable({
  providedIn: 'root',
})
export class CustomerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getCustomer
   */
  static readonly GetCustomerPath = '/api/customers/{customerId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getCustomer()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCustomer$Response(params: {
    customerId: number;
  }): Observable<StrictHttpResponse<Customer>> {

    const rb = new RequestBuilder(this.rootUrl, CustomerService.GetCustomerPath, 'get');
    if (params) {
      rb.path('customerId', params.customerId, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Customer>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getCustomer$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCustomer(params: {
    customerId: number;
  }): Observable<Customer> {

    return this.getCustomer$Response(params).pipe(
      map((r: StrictHttpResponse<Customer>) => r.body as Customer)
    );
  }

  /**
   * Path part for operation updateCustomer
   */
  static readonly UpdateCustomerPath = '/api/customers/{customerId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateCustomer()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateCustomer$Response(params: {
    customerId: number;
    body: Customer
  }): Observable<StrictHttpResponse<Customer>> {

    const rb = new RequestBuilder(this.rootUrl, CustomerService.UpdateCustomerPath, 'put');
    if (params) {
      rb.path('customerId', params.customerId, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Customer>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `updateCustomer$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateCustomer(params: {
    customerId: number;
    body: Customer
  }): Observable<Customer> {

    return this.updateCustomer$Response(params).pipe(
      map((r: StrictHttpResponse<Customer>) => r.body as Customer)
    );
  }

  /**
   * Path part for operation getAllCustomers
   */
  static readonly GetAllCustomersPath = '/api/customers';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllCustomers()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCustomers$Response(params?: {
  }): Observable<StrictHttpResponse<Array<Customer>>> {

    const rb = new RequestBuilder(this.rootUrl, CustomerService.GetAllCustomersPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<Customer>>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getAllCustomers$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCustomers(params?: {
  }): Observable<Array<Customer>> {

    return this.getAllCustomers$Response(params).pipe(
      map((r: StrictHttpResponse<Array<Customer>>) => r.body as Array<Customer>)
    );
  }

  /**
   * Path part for operation createCustomer
   */
  static readonly CreateCustomerPath = '/api/customers';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createCustomer()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCustomer$Response(params: {
    body: Customer
  }): Observable<StrictHttpResponse<Customer>> {

    const rb = new RequestBuilder(this.rootUrl, CustomerService.CreateCustomerPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Customer>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createCustomer$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCustomer(params: {
    body: Customer
  }): Observable<Customer> {

    return this.createCustomer$Response(params).pipe(
      map((r: StrictHttpResponse<Customer>) => r.body as Customer)
    );
  }

}
