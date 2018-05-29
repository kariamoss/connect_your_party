import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {RouterModule} from "@angular/router";
import {appRoutes} from "./navigation";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule, MatSidenavModule, MatToolbarModule, MatCardModule, MatIconModule,
  MatGridList, MatGridListModule, MatDialogModule, MatRadioModule
} from "@angular/material";
import { EventComponent } from './event/event.component';
import { EventViewComponent } from './event-view/event-view.component';
import { PhotosComponent } from './photos/photos.component';
import { EventCardComponent } from './event-card/event-card.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import { ServiceSelectorComponent } from './service-selector/service-selector.component';
import {EventService} from "./services/events.service";
import {SelectorService} from "./services/selector.service";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    EventComponent,
    EventViewComponent,
    PhotosComponent,
    EventCardComponent,
    EventDetailsComponent,
    ServiceSelectorComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatCardModule,
    MatIconModule,
    MatGridListModule,
    MatDialogModule,
    MatRadioModule,
  ],
  providers: [
    EventService,
    SelectorService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [ServiceSelectorComponent]
})
export class AppModule { }
