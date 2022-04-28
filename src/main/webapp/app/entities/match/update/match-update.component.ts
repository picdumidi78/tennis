import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IMatch, Match } from '../match.model';
import { MatchService } from '../service/match.service';
import { IPlayer } from 'app/entities/player/player.model';
import { PlayerService } from 'app/entities/player/service/player.service';

@Component({
  selector: 'jhi-match-update',
  templateUrl: './match-update.component.html',
})
export class MatchUpdateComponent implements OnInit {
  isSaving = false;

  playersSharedCollection: IPlayer[] = [];

  editForm = this.fb.group({
    id: [],
    playerOneName: [],
    playerOneScore: [],
    playerTwoName: [],
    playerTwoScore: [],
    predication: [],
    players: [],
  });

  constructor(
    protected matchService: MatchService,
    protected playerService: PlayerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ match }) => {
      this.updateForm(match);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const match = this.createFromForm();
    if (match.id !== undefined) {
      this.subscribeToSaveResponse(this.matchService.update(match));
    } else {
      this.subscribeToSaveResponse(this.matchService.create(match));
    }
  }

  trackPlayerById(_index: number, item: IPlayer): number {
    return item.id!;
  }

  getSelectedPlayer(option: IPlayer, selectedVals?: IPlayer[]): IPlayer {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMatch>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(match: IMatch): void {
    this.editForm.patchValue({
      id: match.id,
      playerOneName: match.playerOneName,
      playerOneScore: match.playerOneScore,
      playerTwoName: match.playerTwoName,
      playerTwoScore: match.playerTwoScore,
      predication: match.predication,
      players: match.players,
    });

    this.playersSharedCollection = this.playerService.addPlayerToCollectionIfMissing(
      this.playersSharedCollection,
      ...(match.players ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.playerService
      .query()
      .pipe(map((res: HttpResponse<IPlayer[]>) => res.body ?? []))
      .pipe(
        map((players: IPlayer[]) =>
          this.playerService.addPlayerToCollectionIfMissing(players, ...(this.editForm.get('players')!.value ?? []))
        )
      )
      .subscribe((players: IPlayer[]) => (this.playersSharedCollection = players));
  }

  protected createFromForm(): IMatch {
    return {
      ...new Match(),
      id: this.editForm.get(['id'])!.value,
      playerOneName: this.editForm.get(['playerOneName'])!.value,
      playerOneScore: this.editForm.get(['playerOneScore'])!.value,
      playerTwoName: this.editForm.get(['playerTwoName'])!.value,
      playerTwoScore: this.editForm.get(['playerTwoScore'])!.value,
      predication: this.editForm.get(['predication'])!.value,
      players: this.editForm.get(['players'])!.value,
    };
  }
}
