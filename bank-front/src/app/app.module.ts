import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { PaymentFormComponent } from './payment-form/payment-form.component';
import { HttpClientModule } from '@angular/common/http';
import { DateValidatorDirective } from './payment-form/date-validator.directive';
import { QrPaymentComponent } from './qr-payment/qr-payment.component';

@NgModule({
  declarations: [
    AppComponent,
    PaymentFormComponent,
    DateValidatorDirective,
    QrPaymentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
