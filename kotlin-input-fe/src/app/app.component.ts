import { Component } from '@angular/core';
import { RestService } from './rest.service';
import { Greeting } from './Greeting';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  name: string;
  greeting: string;

  constructor(private restService: RestService) {
  }

  send(): void {
    this.restService.sendName(this.name).subscribe((o: Greeting) => {
      this.greeting = o.content;
    });
  }
}
