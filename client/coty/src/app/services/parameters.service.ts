import {Injectable} from "@angular/core";

/**
 * Service to share the event id (possibly other variables) through a router-outlet
 */
@Injectable()
export class ParametersService {

  sharedId: number;

}
