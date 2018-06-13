import {Routes} from "@angular/router";
import {NotFoundComponent} from "./not-found/not-found.component";
import {EventComponent} from "./event/event.component";
import {EventViewComponent} from "./event-view/event-view.component";
import {PhotosComponent} from "./photos/photos.component";
import {EventDetailsComponent} from "./event-details/event-details.component";
import {MusicComponent} from "./music/music.component";
import {DevelopersComponent} from "./developers/developers.component";
import {AuthenticationProcessComponent} from "./authentication-process/authentication-process.component";
import {PaymentComponent} from "./payment/payment.component";
import {ProcessPaymentComponent} from "./payment/process-payment/process-payment.component";
import {CancelPaymentComponent} from "./payment/cancel-payment/cancel-payment.component";

export const appRoutes: Routes = [


  {path: '', pathMatch: 'full', redirectTo: 'events'},
  {path: 'events', component: EventViewComponent},
  {
    path: 'events/:id', component: EventComponent,
    children: [
      {path: '', pathMatch: 'full', redirectTo: 'details'},
      {path: 'details', component: EventDetailsComponent},
      {path: 'photo', component: PhotosComponent},
      {path: 'music', component: MusicComponent},
      {path: 'payment', component: PaymentComponent}
    ]
  },
  {path: 'processPayment', component: ProcessPaymentComponent},
  {path: 'cancelPayment', component: CancelPaymentComponent},
  {path: 'authentication', component: AuthenticationProcessComponent},
  {path: 'devs', component: DevelopersComponent},
  {path: 'not-found', component: NotFoundComponent},
  {path: '**', redirectTo: 'not-found'},
];
