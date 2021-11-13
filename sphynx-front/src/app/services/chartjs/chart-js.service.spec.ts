import { TestBed } from '@angular/core/testing';

import { ChartJsService } from './chart-js.service';

describe('ChartJsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ChartJsService = TestBed.get(ChartJsService);
    expect(service).toBeTruthy();
  });
});
