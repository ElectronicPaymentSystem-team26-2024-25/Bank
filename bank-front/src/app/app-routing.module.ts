import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaymentFormComponent } from './payment-form/payment-form.component';
import { QrPaymentComponent } from './qr-payment/qr-payment.component';

const routes: Routes = [
  {path: 'payment/:paymentId', component: PaymentFormComponent},
  {path: 'qrpayment/:paymentId', component: QrPaymentComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
