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

import { Contract } from '../models/contract';

@Injectable({
  providedIn: 'root',
})
export class ContractService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getContract
   */
  static readonly GetContractPath = '/api/contracts/{contractId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getContract()` instead.
   *
   * This method doesn't expect any request body.
   */
  getContract$Response(params: {
    contractId: number;
  }): Observable<StrictHttpResponse<Contract>> {

    const rb = new RequestBuilder(this.rootUrl, ContractService.GetContractPath, 'get');
    if (params) {
      rb.path('contractId', params.contractId, {});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Contract>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getContract$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getContract(params: {
    contractId: number;
  }): Observable<Contract> {

    return this.getContract$Response(params).pipe(
      map((r: StrictHttpResponse<Contract>) => r.body as Contract)
    );
  }

  /**
   * Path part for operation updateContract
   */
  static readonly UpdateContractPath = '/api/contracts/{contractId}';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateContract()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateContract$Response(params: {
    contractId: number;
    body: Contract
  }): Observable<StrictHttpResponse<Contract>> {

    const rb = new RequestBuilder(this.rootUrl, ContractService.UpdateContractPath, 'put');
    if (params) {
      rb.path('contractId', params.contractId, {});
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Contract>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `updateContract$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateContract(params: {
    contractId: number;
    body: Contract
  }): Observable<Contract> {

    return this.updateContract$Response(params).pipe(
      map((r: StrictHttpResponse<Contract>) => r.body as Contract)
    );
  }

  /**
   * Path part for operation getAllContracts
   */
  static readonly GetAllContractsPath = '/api/contracts';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllContracts()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllContracts$Response(params?: {
  }): Observable<StrictHttpResponse<Array<Contract>>> {

    const rb = new RequestBuilder(this.rootUrl, ContractService.GetAllContractsPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<Contract>>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getAllContracts$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllContracts(params?: {
  }): Observable<Array<Contract>> {

    return this.getAllContracts$Response(params).pipe(
      map((r: StrictHttpResponse<Array<Contract>>) => r.body as Array<Contract>)
    );
  }

  /**
   * Path part for operation createContract
   */
  static readonly CreateContractPath = '/api/contracts';

  /**
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createContract()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createContract$Response(params: {
    body: Contract
  }): Observable<StrictHttpResponse<Contract>> {

    const rb = new RequestBuilder(this.rootUrl, ContractService.CreateContractPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Contract>;
      })
    );
  }

  /**
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createContract$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createContract(params: {
    body: Contract
  }): Observable<Contract> {

    return this.createContract$Response(params).pipe(
      map((r: StrictHttpResponse<Contract>) => r.body as Contract)
    );
  }

}
