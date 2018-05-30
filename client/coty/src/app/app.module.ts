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
  MatSnackBarModule, MatChipsModule, MatRadioModule, MatDialogModule, MatGridListModule,
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
import { ShoppingListComponent } from './shopping-list/shopping-list.component';
import {ShoppingListService} from "./services/shopping-list.service";
import { ModuleListComponent } from './module-list/module-list.component';
import { PhotosListComponent } from './photos-list/photos-list.component';
import {HttpClientModule} from "@angular/common/http";
import { PhotoDialogComponent } from './photo-dialog/photo-dialog.component';

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
    PhotoDialogComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    FormsModule,
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
    MatGridListModule
  ],
  providers: [EventService, SelectorService, ShoppingListService],
  bootstrap: [AppComponent],
  entryComponents: [ServiceSelectorComponent,PhotoDialogComponent]
})
export class AppModule { }
