import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ParametersService} from "../services/parameters.service";
import {SelectorService} from "../services/selector.service";

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css'],
})
export class PhotosComponent implements OnInit {

  id: number;
  module: string = 'photo';

  constructor(private route: ActivatedRoute,
              private parameters: ParametersService,
              private selectorService: SelectorService) { }

  ngOnInit() {
    this.id = this.parameters.sharedId;
    this.selectorService.updateServicesList(this.module);
    /*
    setTimeout(() => {
      this.sub = this.route.queryParams.subscribe(params => {
        this.queryService = this.selectorService.getServiceByName(this.module, params.service);
        this.selectorService.changeSelectedService(this.module, this.queryService);
        this.code = params.code;
        if (!isUndefined(this.code)) {
          this.tokenRetriever.retrieveToken(this.queryService, this.code, this.id, this.module);
        }
      });
    }, 1000);
    */
  }



}
