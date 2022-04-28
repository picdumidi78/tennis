import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IPlayer, Player } from '../player.model';

import { PlayerService } from './player.service';

describe('Player Service', () => {
  let service: PlayerService;
  let httpMock: HttpTestingController;
  let elemDefault: IPlayer;
  let expectedResult: IPlayer | IPlayer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PlayerService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      firstname: 'AAAAAAA',
      lastname: 'AAAAAAA',
      country: 'AAAAAAA',
      age: 0,
      firstServePercentage: 0,
      servicePointsWon: 0,
      breakPointsSaved: 0,
      secondServeReturnPointsWon: 0,
      breakPointsConverted: 0,
      score: 0,
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a Player', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new Player()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Player', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstname: 'BBBBBB',
          lastname: 'BBBBBB',
          country: 'BBBBBB',
          age: 1,
          firstServePercentage: 1,
          servicePointsWon: 1,
          breakPointsSaved: 1,
          secondServeReturnPointsWon: 1,
          breakPointsConverted: 1,
          score: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Player', () => {
      const patchObject = Object.assign(
        {
          firstServePercentage: 1,
          servicePointsWon: 1,
          score: 1,
        },
        new Player()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Player', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          firstname: 'BBBBBB',
          lastname: 'BBBBBB',
          country: 'BBBBBB',
          age: 1,
          firstServePercentage: 1,
          servicePointsWon: 1,
          breakPointsSaved: 1,
          secondServeReturnPointsWon: 1,
          breakPointsConverted: 1,
          score: 1,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a Player', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addPlayerToCollectionIfMissing', () => {
      it('should add a Player to an empty array', () => {
        const player: IPlayer = { id: 123 };
        expectedResult = service.addPlayerToCollectionIfMissing([], player);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(player);
      });

      it('should not add a Player to an array that contains it', () => {
        const player: IPlayer = { id: 123 };
        const playerCollection: IPlayer[] = [
          {
            ...player,
          },
          { id: 456 },
        ];
        expectedResult = service.addPlayerToCollectionIfMissing(playerCollection, player);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Player to an array that doesn't contain it", () => {
        const player: IPlayer = { id: 123 };
        const playerCollection: IPlayer[] = [{ id: 456 }];
        expectedResult = service.addPlayerToCollectionIfMissing(playerCollection, player);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(player);
      });

      it('should add only unique Player to an array', () => {
        const playerArray: IPlayer[] = [{ id: 123 }, { id: 456 }, { id: 67213 }];
        const playerCollection: IPlayer[] = [{ id: 123 }];
        expectedResult = service.addPlayerToCollectionIfMissing(playerCollection, ...playerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const player: IPlayer = { id: 123 };
        const player2: IPlayer = { id: 456 };
        expectedResult = service.addPlayerToCollectionIfMissing([], player, player2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(player);
        expect(expectedResult).toContain(player2);
      });

      it('should accept null and undefined values', () => {
        const player: IPlayer = { id: 123 };
        expectedResult = service.addPlayerToCollectionIfMissing([], null, player, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(player);
      });

      it('should return initial array if no Player is added', () => {
        const playerCollection: IPlayer[] = [{ id: 123 }];
        expectedResult = service.addPlayerToCollectionIfMissing(playerCollection, undefined, null);
        expect(expectedResult).toEqual(playerCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
