import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Payment } from '../model/payment';
import { BankPaymentResponse } from '../model/payment-response';
import { PSPRedirection } from '../model/psp-redirection';
import { BankService } from '../service/bank-service.service';
import { PaymentStatus } from '../model/payment-status';

@Component({
  selector: 'app-payment-form',
  templateUrl: './payment-form.component.html',
  styleUrls: ['./payment-form.component.css']
})
export class PaymentFormComponent {
  paymentRequest: Payment = {cardHolderName: '', expirationDate: new Date(), pan: "", paymentId: '0', securityCode: 0}
  paymentId: string | null = null
  pan: string = ""
  security_code: number = 0
  card_holder_name: string = ""
  expiration_date: string = ""
  
  constructor(private route: ActivatedRoute, private service: BankService, private router: Router) {}

  ngOnInit(): void{
    const idString = this.route.snapshot.paramMap.get('paymentId')
    this.paymentId = idString != null ? idString : null
  }

  onSubmit(){
    this.paymentRequest.cardHolderName = this.card_holder_name
    this.paymentRequest.pan = this.pan
    this.paymentRequest.securityCode = this.security_code
    this.paymentRequest.paymentId = this.paymentId!
    this.paymentRequest.expirationDate = this.parseDate(this.expiration_date)
    console.log(this.paymentRequest)
    this.service.executePayment(this.paymentRequest).subscribe({
      next: (response) => {
        if(response.status === PaymentStatus.SUCCESS){
            this.sendSuccessData(response.redirectUrl)
        }else{
          this.sendErrorData(response.redirectUrl, response.failReason)
        }
      },
      error: (error) => {
        this.sendErrorData('', 'Invalid request')
      }
    });
  }

  parseDate(dateInput: string): Date{
    const [yy, mm] = dateInput.split('/')
    const year = 2000 + parseInt(yy, 10)
    const month = parseInt(mm, 10) - 1
    const date = new Date(year, month, 28)
    return date
  }
  sendSuccessData(successUrl: string){
    const successData: PSPRedirection = { failReason: '.', url: successUrl }
    this.router.navigate(['/success'], { state: { data: successData } });
  }
  sendErrorData(errorUrl: string, failReason: string){
    const errorData: PSPRedirection = { failReason: failReason, url: errorUrl }
    this.router.navigate(['/error'], { queryParams: { data: JSON.stringify(errorData) }  });
  }
}
