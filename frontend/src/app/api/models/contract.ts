/* tslint:disable */
/* eslint-disable */
import { Customer } from './customer';
import { Vehicle } from './vehicle';
export interface Contract {
  contractId?: number;
  contractNumber: string;
  customer: Customer;
  monthlyRate: number;
  vehicle: Vehicle;
}
