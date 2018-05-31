import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { NotFoundComponent } from './not-found/not-found.component';
import {RouterModule} from "@angular/router";
import {appRoutes} from "./navigation";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule, MatSidenavModule, MatToolbarModule, MatCardModule, MatIconModule,
  MatCheckboxModule, MatFormFieldModule, MatInputModule,
  MatSnackBarModule, MatChipsModule, MatRadioModule, MatDialogModule, MatGridListModule, MatProgressSpinnerModule,
} from "@angular/material";
import { EventComponent } from './event/event.component';
import { EventViewComponent } from './event-view/event-view.component';
import { PhotosComponent } from './photos/photos.component';
import { EventCardComponent } from './event-view/event-card/event-card.component';
import { EventDetailsComponent } from './event-details/event-details.component';
import { ServiceSelectorComponent } from './service-selector/service-selector.component';
import {EventService} from "./services/events.service";
import {SelectorService} from "./services/selector.service";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { ShoppingListComponent } from './shopping-list/shopping-list.component';
import {ShoppingListService} from "./services/shopping-list.service";
import { ModuleListComponent } from './event/module-list/module-list.component';
import { PhotosListComponent } from './photos/photos-list/photos-list.component';
import { AddPhotoComponent } from './photos/add-photo/add-photo.component';
import { PhotoDialogComponent } from './photos/photo-dialog/photo-dialog.component';
import { MusicComponent } from './music/music.component';

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
    ShoppingListComponent,
    ModuleListComponent,
    PhotosListComponent,
    AddPhotoComponent,
    PhotoDialogComponent,
    MusicComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MatButtonModule,
    MatToolbarModule,
    MatCardModule,
    MatIconModule,
    MatCheckboxModule,
    MatInputModule,
    MatFormFieldModule,
    MatSnackBarModule,
    MatRadioModule,
    MatChipsModule,
    MatDialogModule,
    MatGridListModule,
    MatProgressSpinnerModule,
  ],
  providers: [
    EventService,
    SelectorService,
    ShoppingListService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [ServiceSelectorComponent, PhotoDialogComponent, AddPhotoComponent]
})
export class AppModule { }
