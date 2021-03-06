import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {NotFoundComponent} from './not-found/not-found.component';
import {RouterModule} from "@angular/router";
import {appRoutes} from "./navigation";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule,
  MatToolbarModule,
  MatCardModule,
  MatIconModule,
  MatCheckboxModule,
  MatFormFieldModule,
  MatInputModule,
  MatSnackBarModule,
  MatChipsModule,
  MatRadioModule,
  MatDialogModule,
  MatGridListModule,
  MatProgressSpinnerModule,
  MatTabsModule, MatProgressBarModule,
} from "@angular/material";
import {EventComponent} from './event/event.component';
import {EventViewComponent} from './event-view/event-view.component';
import {PhotosComponent} from './photos/photos.component';
import {EventCardComponent} from './event-view/event-card/event-card.component';
import {EventDetailsComponent} from './event-details/event-details.component';
import {ServiceSelectorComponent} from './service-selector/service-selector.component';
import {EventService} from "./services/events.service";
import {SelectorService} from "./services/selector.service";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {ShoppingListComponent} from './shopping-list/shopping-list.component';
import {ShoppingListService} from "./services/shopping-list.service";
import {ModuleListComponent} from './event/module-list/module-list.component';
import {PhotosListComponent} from './photos/photos-list/photos-list.component';
import {AddPhotoComponent} from './photos/add-photo/add-photo.component';
import {PhotoDialogComponent} from './photos/photo-dialog/photo-dialog.component';
import {MusicComponent} from './music/music.component';
import {PhotoService} from "./services/photo.service";
import {AppConfigModule} from "./app-config.module";
import {DevelopersComponent} from './developers/developers.component';
import { SubmitDevComponent } from './developers/submit-dev/submit-dev.component';
import {AuthenticationProcessComponent} from "./authentication-process/authentication-process.component";
import {MusicPlayerComponent} from "./music/music-player/music-player.component";
import {MusicSearchComponent} from "./music/music-search/music-search.component";
import { MusicListComponent } from './music/music-list/music-list.component';
import { UserSwitchComponent } from './user-switch/user-switch.component';
import {UserService} from "./services/user.service";
import { PaymentComponent } from './payment/payment.component';
import { ProcessPaymentComponent } from './payment/process-payment/process-payment.component';
import { CancelPaymentComponent } from './payment/cancel-payment/cancel-payment.component';

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
    DevelopersComponent,
    SubmitDevComponent,
    AuthenticationProcessComponent,
    MusicSearchComponent,
    MusicPlayerComponent,
    MusicListComponent,
    UserSwitchComponent,
    PaymentComponent,
    ProcessPaymentComponent,
    CancelPaymentComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppConfigModule,
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
    MatGridListModule,
    MatProgressSpinnerModule,
    MatTabsModule,
    MatProgressBarModule,
  ],
  providers: [
    UserService,
    EventService,
    SelectorService,
    ShoppingListService,
    PhotoService,
  ],
  bootstrap: [AppComponent],
  entryComponents: [ServiceSelectorComponent, PhotoDialogComponent, AddPhotoComponent,SubmitDevComponent,UserSwitchComponent]
})
export class AppModule {
}
