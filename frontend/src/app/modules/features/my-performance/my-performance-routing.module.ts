import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PerformanceComponent } from './components/performance/performance.component';

const routes: Routes = [
  {
    path: '',
    component: PerformanceComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MyPerformanceRoutingModule { }
