import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {ImportComponent} from "./import/import.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {AggregateComponent} from "./aggregate/aggregate.component";
import {ChartComponent} from "./chart/chart.component";

const routes: Routes = [
  { path: 'import', component: ImportComponent },
  { path: 'aggregation', component: AggregateComponent },
  { path: 'chart', component: ChartComponent },
  { path: '', redirectTo: '/import', pathMatch: 'full'},
  { path: '**', component: PageNotFoundComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
