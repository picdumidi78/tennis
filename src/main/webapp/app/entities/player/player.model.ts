export interface IPlayer {
  id?: number;
  firstname?: string | null;
  lastname?: string | null;
  country?: string | null;
  age?: number | null;
  firstServePercentage?: number | null;
  servicePointsWon?: number | null;
  breakPointsSaved?: number | null;
  secondServeReturnPointsWon?: number | null;
  breakPointsConverted?: number | null;
  score?: number | null;
}

export class Player implements IPlayer {
  constructor(
    public id?: number,
    public firstname?: string | null,
    public lastname?: string | null,
    public country?: string | null,
    public age?: number | null,
    public firstServePercentage?: number | null,
    public servicePointsWon?: number | null,
    public breakPointsSaved?: number | null,
    public secondServeReturnPointsWon?: number | null,
    public breakPointsConverted?: number | null,
    public score?: number | null
  ) {}
}

export function getPlayerIdentifier(player: IPlayer): number | undefined {
  return player.id;
}
