import {Routes} from "@angular/router";
import {NotFoundComponent} from "./not-found/not-found.component";

export const appRoutes: Routes = [

  { path: '', component:  NotFoundComponent},
  { path: 'not-found', component:  NotFoundComponent},
  { path: '**', redirectTo: 'not-found' }
];
