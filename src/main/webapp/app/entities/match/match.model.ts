import { IPlayer } from 'app/entities/player/player.model';

export interface IMatch {
  id?: number;
  playerOneName?: string | null;
  playerOneScore?: number | null;
  playerTwoName?: string | null;
  playerTwoScore?: number | null;
  predication?: string | null;
  players?: IPlayer[] | null;
}

export class Match implements IMatch {
  constructor(
    public id?: number,
    public playerOneName?: string | null,
    public playerOneScore?: number | null,
    public playerTwoName?: string | null,
    public playerTwoScore?: number | null,
    public predication?: string | null,
    public players?: IPlayer[] | null
  ) {}
}

export function getMatchIdentifier(match: IMatch): number | undefined {
  return match.id;
}
