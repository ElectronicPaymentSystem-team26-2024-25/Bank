import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaymentFormComponent } from './payment-form/payment-form.component';
import { SuccessPageComponent } from './success-page/success-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';

const routes: Routes = [
  {path: 'payment/:paymentId', component: PaymentFormComponent},
  {path: 'success', component: SuccessPageComponent},
  {path: 'error', component: ErrorPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
