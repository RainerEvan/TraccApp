import { TestBed } from '@angular/core/testing';

import { FcmsubscriptionService } from './fcmsubscription.service';

describe('FcmsubscriptionService', () => {
  let service: FcmsubscriptionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FcmsubscriptionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
