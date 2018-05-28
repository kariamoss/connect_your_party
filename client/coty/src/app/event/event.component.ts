import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ParametersService} from "../services/parameters.service";

@Component({
  selector: 'app-event',
  templateUrl: './event.component.html',
  styleUrls: ['./event.component.css'],
  providers: [ParametersService]
})
export class EventComponent implements OnInit {

  id: number;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private parameters: ParametersService) { }

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.parameters.sharedId = this.id;
    if (this.id > 10) {
      this.router.navigate(['/not-found']);
      return;
    }
  }

}
