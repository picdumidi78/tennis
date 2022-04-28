import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IMatch, Match } from '../match.model';
import { MatchService } from '../service/match.service';
import { Resultat } from 'app/entities/enumerations/resultat.model';

@Component({
  selector: 'jhi-match-update',
  templateUrl: './match-update.component.html',
})
export class MatchUpdateComponent implements OnInit {
  isSaving = false;
  resultatValues = Object.keys(Resultat);

  editForm = this.fb.group({
    id: [],
    playerOneName: [],
    playerOneScore: [],
    playerOneOdd: [],
    playerTwoName: [],
    playerTwoScore: [],
    playerTwoOdd: [],
    prediction: [],
    actualResult: [],
    betAmount: [],
    potentialGain: [],
    gain: [],
  });

  constructor(protected matchService: MatchService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ match }) => {
      this.updateForm(match);
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
      playerOneOdd: match.playerOneOdd,
      playerTwoName: match.playerTwoName,
      playerTwoScore: match.playerTwoScore,
      playerTwoOdd: match.playerTwoOdd,
      prediction: match.prediction,
      actualResult: match.actualResult,
      betAmount: match.betAmount,
      potentialGain: match.potentialGain,
      gain: match.gain,
    });
  }

  protected createFromForm(): IMatch {
    return {
      ...new Match(),
      id: this.editForm.get(['id'])!.value,
      playerOneName: this.editForm.get(['playerOneName'])!.value,
      playerOneScore: this.editForm.get(['playerOneScore'])!.value,
      playerOneOdd: this.editForm.get(['playerOneOdd'])!.value,
      playerTwoName: this.editForm.get(['playerTwoName'])!.value,
      playerTwoScore: this.editForm.get(['playerTwoScore'])!.value,
      playerTwoOdd: this.editForm.get(['playerTwoOdd'])!.value,
      prediction: this.editForm.get(['prediction'])!.value,
      actualResult: this.editForm.get(['actualResult'])!.value,
      betAmount: this.editForm.get(['betAmount'])!.value,
      potentialGain: this.editForm.get(['potentialGain'])!.value,
      gain: this.editForm.get(['gain'])!.value,
    };
  }
}
