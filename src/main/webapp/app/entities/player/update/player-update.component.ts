import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPlayer, Player } from '../player.model';
import { PlayerService } from '../service/player.service';

@Component({
  selector: 'jhi-player-update',
  templateUrl: './player-update.component.html',
})
export class PlayerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    firstname: [],
    lastname: [],
    country: [],
    age: [],
    firstServePercentage: [],
    servicePointsWon: [],
    breakPointsSaved: [],
    secondServeReturnPointsWon: [],
    breakPointsConverted: [],
    score: [],
  });

  constructor(protected playerService: PlayerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ player }) => {
      this.updateForm(player);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const player = this.createFromForm();
    if (player.id !== undefined) {
      this.subscribeToSaveResponse(this.playerService.update(player));
    } else {
      this.subscribeToSaveResponse(this.playerService.create(player));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlayer>>): void {
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

  protected updateForm(player: IPlayer): void {
    this.editForm.patchValue({
      id: player.id,
      firstname: player.firstname,
      lastname: player.lastname,
      country: player.country,
      age: player.age,
      firstServePercentage: player.firstServePercentage,
      servicePointsWon: player.servicePointsWon,
      breakPointsSaved: player.breakPointsSaved,
      secondServeReturnPointsWon: player.secondServeReturnPointsWon,
      breakPointsConverted: player.breakPointsConverted,
      score: player.score,
    });
  }

  protected createFromForm(): IPlayer {
    return {
      ...new Player(),
      id: this.editForm.get(['id'])!.value,
      firstname: this.editForm.get(['firstname'])!.value,
      lastname: this.editForm.get(['lastname'])!.value,
      country: this.editForm.get(['country'])!.value,
      age: this.editForm.get(['age'])!.value,
      firstServePercentage: this.editForm.get(['firstServePercentage'])!.value,
      servicePointsWon: this.editForm.get(['servicePointsWon'])!.value,
      breakPointsSaved: this.editForm.get(['breakPointsSaved'])!.value,
      secondServeReturnPointsWon: this.editForm.get(['secondServeReturnPointsWon'])!.value,
      breakPointsConverted: this.editForm.get(['breakPointsConverted'])!.value,
      score: this.editForm.get(['score'])!.value,
    };
  }
}
