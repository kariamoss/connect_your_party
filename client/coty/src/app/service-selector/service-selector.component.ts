import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from "rxjs/internal/Subscription";
import {PhotoServiceSelectorService} from "../services/photoServiceSelector.service";
import {MatDialogRef} from "@angular/material";

@Component({
  selector: 'app-service-selector',
  templateUrl: './service-selector.component.html',
  styleUrls: ['./service-selector.component.css']
})
export class ServiceSelectorComponent implements OnInit, OnDestroy {

  services: any[];
  serviceSubscription: Subscription;

  constructor(private serviceSelector: PhotoServiceSelectorService,
              public dialogRef: MatDialogRef<ServiceSelectorComponent>,) { }

  ngOnInit() {
    this.serviceSubscription = this.serviceSelector.servicesSubject.subscribe(
      (services: any[]) => {
        this.services = services;
      }
    );
    this.serviceSelector.emitServicesSubject();
  }

  closeDialog(): void {
    this.dialogRef.close('Pizza!');
  }

  ngOnDestroy(): void {
    this.serviceSubscription.unsubscribe();
  }

}
