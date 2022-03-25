import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Location} from "@angular/common";
import {Contract} from "../api/models/contract";
import {ContractService} from "../api/services/contract.service";

@Component({
  selector: 'app-contract-details',
  templateUrl: './contract-details.component.html',
  styleUrls: ['./contract-details.component.css']
})
export class ContractDetailsComponent implements OnInit {

  contract: Contract;

  constructor(
      private route: ActivatedRoute,
      private contractService: ContractService,
      private location: Location
  ) { }

  ngOnInit(): void {
    const routeParams = this.route.snapshot.paramMap;
    const contractIdFromRoute = Number(routeParams.get('contractId'));
    this.contractService.getContract({contractId: contractIdFromRoute})
        .subscribe(contract => this.contract = contract);
  }

  save(): void {
    this.contractService.updateContract({contractId: this.contract?.contractId!, body: this.contract!})
        .subscribe(contract => this.contract = contract);
    this.goBack();
  }

  goBack(): void {
    this.location.back();
  }
}