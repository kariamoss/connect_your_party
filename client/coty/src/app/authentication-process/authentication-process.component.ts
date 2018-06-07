import {Component, OnDestroy, OnInit} from '@angular/core';
import {isUndefined} from "util";
import {SelectorService} from "../services/selector.service";
import {TokenRetrieverService} from "../services/tokenRetriever.service";
import {Subscription} from "rxjs/internal/Subscription";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-authentication-process',
  templateUrl: './authentication-process.component.html',
  styleUrls: ['./authentication-process.component.css']
})
export class AuthenticationProcessComponent implements OnInit, OnDestroy {

  updatePass: Subscription;
  sub: Subscription;

  constructor(private selectorService: SelectorService,
              private tokenRetriever: TokenRetrieverService,
              private route: ActivatedRoute,
              private router: Router,) {
  }

  ngOnInit() {
    let state, module, id, queryService, code;
    this.updatePass = this.route.queryParams.subscribe(params => {
      state = params.state.trim();
      id = state.split("\/")[0];
      module = state.split("\/")[1];
      this.selectorService.updateServicesList(module);
    });
    setTimeout(() => {
      this.sub = this.route.queryParams.subscribe(params => {
        queryService = this.selectorService.getServiceByName(module, params.service);
        this.selectorService.changeSelectedService(module, queryService);
        code = params.code;
        if (!isUndefined(code)) {
          this.tokenRetriever.retrieveToken(queryService, code, id, module);
        }
        setTimeout(() => {
          this.router.navigate(['/events/' + state]);
        }, 1000);
      });
    }, 1000);
  }

  ngOnDestroy(): void {
    this.updatePass.unsubscribe();
    this.sub.unsubscribe();
  }

}
