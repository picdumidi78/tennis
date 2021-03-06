import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'player',
        data: { pageTitle: 'tennisApp.player.home.title' },
        loadChildren: () => import('./player/player.module').then(m => m.PlayerModule),
      },
      {
        path: 'match',
        data: { pageTitle: 'tennisApp.match.home.title' },
        loadChildren: () => import('./match/match.module').then(m => m.MatchModule),
      },
      {
        path: 'bet',
        data: { pageTitle: 'tennisApp.bet.home.title' },
        loadChildren: () => import('./bet/bet.module').then(m => m.BetModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
