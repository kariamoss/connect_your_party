import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {RouterModule} from "@angular/router";
import {appRoutes} from "./navigation";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule, MatSidenavModule, MatToolbarModule, MatCardModule, MatIconModule,
  MatGridList, MatGridListModule, MatChipsModule, MatCheckboxModule, MatFormFieldModule, MatInputModule,
  MatSnackBarModule
} from "@angular/material";
import { EventComponent } from './event/event.component';
import { EventViewComponent } from './event-view/event-view.component';
import { PhotosComponent } from './photos/photos.component';
import { EventCardComponent } from './event-card/event-card.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import {EventService} from "./services/events.service";
import { ShoppingListComponent } from './shopping-list/shopping-list.component';
import {ShoppingListService} from "./services/shopping-list.service";
import { ModuleListComponent } from './module-list/module-list.component';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    EventComponent,
    EventViewComponent,
    PhotosComponent,
    EventCardComponent,
    EventDetailsComponent,
    ShoppingListComponent,
    ModuleListComponent,
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatCardModule,
    MatIconModule,
    MatGridListModule,
    MatChipsModule,
    MatCheckboxModule,
    MatInputModule,
    MatFormFieldModule,
    MatSnackBarModule,
  ],
  providers: [EventService, ShoppingListService],
  bootstrap: [AppComponent]
})
export class AppModule { }
