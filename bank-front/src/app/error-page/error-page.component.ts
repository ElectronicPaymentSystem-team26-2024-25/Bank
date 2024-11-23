import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PSPRedirection } from '../model/psp-redirection';

@Component({
  selector: 'app-error-page',
  templateUrl: './error-page.component.html',
  styleUrls: ['./error-page.component.css']
})
export class ErrorPageComponent {
  
  receivedData: PSPRedirection | undefined

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['data']) {
        this.receivedData = JSON.parse(params['data']) as PSPRedirection;
        console.log('Received Data:', this.receivedData);
      }
    });
    console.log(this.receivedData)
  }
  redirectToErrorPSP(){

  }
}
