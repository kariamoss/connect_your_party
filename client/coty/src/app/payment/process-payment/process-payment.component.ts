import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {isUndefined} from "util";
import {APP_CONFIG, AppConfig} from "../../app-config.module";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/user.service";
import {Subscription} from "rxjs/internal/Subscription";

@Component({
  selector: 'app-process-payment',
  templateUrl: './process-payment.component.html',
  styleUrls: ['./process-payment.component.css']
})
export class ProcessPaymentComponent implements OnInit {

  updatePass: Subscription;
  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private httpClient : HttpClient,
              @Inject(APP_CONFIG) private config: AppConfig,
              private userService: UserService,

              ) { }

  ngOnInit() {
    let payerID;
    this.updatePass = this.route.queryParams.subscribe(params => payerID = params.PayerID);

    setTimeout(() => {
      this.sub = this.route.queryParams.subscribe(params => {
          let body = new URLSearchParams();
          body.set('PayerID', payerID);
          body.set('service', params.service);
          body.set('userId',this.userService.getCurrentUser().name);
          // '{"code": "'+ code +'", "serviceName": "' + params.service + '"}'
          this.httpClient.post('http://' + this.config.apiEndpoint + "/back-1.0-SNAPSHOT/payment/confirm", body.toString(),
            {
              headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).subscribe(
            data => {
              console.log('Success');
            },
            error => console.log(error)
          );

        setTimeout(() => {
          this.router.navigate(['/events/']);
        }, 30000);
      });
    }, 1000);



  }

}
