import { Component } from '@angular/core';
import { PSPRedirection } from '../model/psp-redirection';
import { Router } from '@angular/router';

@Component({
  selector: 'app-success-page',
  templateUrl: './success-page.component.html',
  styleUrls: ['./success-page.component.css']
})
export class SuccessPageComponent {
  
  receivedData: PSPRedirection | undefined

  constructor(private router: Router) {}

  ngOnInit(): void {
    const navigation = this.router.getCurrentNavigation();
    this.receivedData = navigation?.extras.state?.['data'] as PSPRedirection;
    if (this.receivedData) {
      console.log('Received Data:', this.receivedData);
    }
  }
  redirectToSuccessPSP(){
    
  }
}
