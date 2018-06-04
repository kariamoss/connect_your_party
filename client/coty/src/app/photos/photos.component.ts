import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ParametersService} from "../services/parameters.service";
import {SelectorService} from "../services/selector.service";
import {Subscription} from "rxjs/internal/Subscription";
import {isUndefined} from "util";
import {Service} from "../../model/service.model";
import {TokenRetrieverService} from "../services/tokenRetriever.service";

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css'],
})
export class PhotosComponent implements OnInit, OnDestroy {

  id: number;
  module: string = 'photo';
  sub: Subscription;
  queryService: Service;
  code;

  constructor(private route: ActivatedRoute,
              private parameters: ParametersService,
              private selectorService: SelectorService,
              private tokenRetriever: TokenRetrieverService) { }

  ngOnInit() {
    this.id = this.parameters.sharedId;
    this.selectorService.updateServicesList(this.module);
    setTimeout(() => {
      this.sub = this.route.queryParams.subscribe(params => {
        this.queryService = this.selectorService.getServiceByName(this.module, params.service);
        this.selectorService.changeSelectedService(this.module, this.queryService);
        this.code = params.code;
        console.log(this.queryService);
        if (!isUndefined(this.code)) {
          this.tokenRetriever.retrieveToken(this.queryService, this.code, this.id);
        }
      });
    }, 1000);

  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }



}
