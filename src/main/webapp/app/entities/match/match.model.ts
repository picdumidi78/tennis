import { Resultat } from 'app/entities/enumerations/resultat.model';

export interface IMatch {
  id?: number;
  playerOneName?: string | null;
  playerOneScore?: number | null;
  playerOneOdd?: number | null;
  playerTwoName?: string | null;
  playerTwoScore?: number | null;
  playerTwoOdd?: number | null;
  prediction?: Resultat | null;
  actualResult?: Resultat | null;
  betAmount?: number | null;
  potentialGain?: number | null;
  gain?: number | null;
}

export class Match implements IMatch {
  constructor(
    public id?: number,
    public playerOneName?: string | null,
    public playerOneScore?: number | null,
    public playerOneOdd?: number | null,
    public playerTwoName?: string | null,
    public playerTwoScore?: number | null,
    public playerTwoOdd?: number | null,
    public prediction?: Resultat | null,
    public actualResult?: Resultat | null,
    public betAmount?: number | null,
    public potentialGain?: number | null,
    public gain?: number | null
  ) {}
}

export function getMatchIdentifier(match: IMatch): number | undefined {
  return match.id;
}
