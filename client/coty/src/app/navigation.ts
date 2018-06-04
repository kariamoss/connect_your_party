import {Routes} from "@angular/router";
import {NotFoundComponent} from "./not-found/not-found.component";
import {EventComponent} from "./event/event.component";
import {EventViewComponent} from "./event-view/event-view.component";
import {PhotosComponent} from "./photos/photos.component";
import {EventDetailsComponent} from "./event-details/event-details.component";
import {ShoppingListComponent} from "./shopping-list/shopping-list.component";
import {MusicComponent} from "./music/music.component";
import {AuthenticationFinishComponent} from "./authentication-finish/authentication-finish.component";

export const appRoutes: Routes = [


  {path: '', pathMatch: 'full',redirectTo: 'events'},
  {path: 'events', component: EventViewComponent},
  {
    path: 'events/:id', component: EventComponent,
    children: [
      {path: '', pathMatch: 'full',redirectTo: 'details'},
      {path: 'details', component: EventDetailsComponent},
      {path: 'photos', component: PhotosComponent, children:
          [
            {path: 'authentication-finish', component: AuthenticationFinishComponent}
          ]
      },
      {path: 'shopping', component: ShoppingListComponent},
      {path: 'music',component: MusicComponent}
    ]
  },
  {path: 'not-found', component: NotFoundComponent},
  {path: '**', redirectTo: 'not-found'}
];
