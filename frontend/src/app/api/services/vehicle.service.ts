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

import { Vehicle } from '../models/vehicle';

@Injectable({
  providedIn: 'root',
})
export class VehicleService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getVehicle
   */
  static readonly GetVehiclePath = '/api/vehicles/{vehicleId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVehicle()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVehicle$Response(params: {
    vehicleId: number;
  }): Observable<StrictHttpResponse<Vehicle>> {

    const rb = new RequestBuilder(this.rootUrl, VehicleService.GetVehiclePath, 'get');
    if (params) {
      rb.path('vehicleId', params.vehicleId, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Vehicle>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getVehicle$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVehicle(params: {
    vehicleId: number;
  }): Observable<Vehicle> {

    return this.getVehicle$Response(params).pipe(
      map((r: StrictHttpResponse<Vehicle>) => r.body as Vehicle)
    );
  }

  /**
   * Path part for operation updateVehicle
   */
  static readonly UpdateVehiclePath = '/api/vehicles/{vehicleId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateVehicle()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVehicle$Response(params: {
    vehicleId: number;
    body: Vehicle
  }): Observable<StrictHttpResponse<Vehicle>> {

    const rb = new RequestBuilder(this.rootUrl, VehicleService.UpdateVehiclePath, 'put');
    if (params) {
      rb.path('vehicleId', params.vehicleId, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Vehicle>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `updateVehicle$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVehicle(params: {
    vehicleId: number;
    body: Vehicle
  }): Observable<Vehicle> {

    return this.updateVehicle$Response(params).pipe(
      map((r: StrictHttpResponse<Vehicle>) => r.body as Vehicle)
    );
  }

  /**
   * Path part for operation getAllVehicles
   */
  static readonly GetAllVehiclesPath = '/api/vehicles';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllVehicles()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVehicles$Response(params?: {
  }): Observable<StrictHttpResponse<Array<Vehicle>>> {

    const rb = new RequestBuilder(this.rootUrl, VehicleService.GetAllVehiclesPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<Vehicle>>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getAllVehicles$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVehicles(params?: {
  }): Observable<Array<Vehicle>> {

    return this.getAllVehicles$Response(params).pipe(
      map((r: StrictHttpResponse<Array<Vehicle>>) => r.body as Array<Vehicle>)
    );
  }

  /**
   * Path part for operation createVehicle
   */
  static readonly CreateVehiclePath = '/api/vehicles';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createVehicle()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVehicle$Response(params: {
    body: Vehicle
  }): Observable<StrictHttpResponse<Vehicle>> {

    const rb = new RequestBuilder(this.rootUrl, VehicleService.CreateVehiclePath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Vehicle>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createVehicle$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVehicle(params: {
    body: Vehicle
  }): Observable<Vehicle> {

    return this.createVehicle$Response(params).pipe(
      map((r: StrictHttpResponse<Vehicle>) => r.body as Vehicle)
    );
  }

}
