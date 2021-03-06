import { Component } from '@angular/core';
import { RestService } from './rest.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  name: string;
  greeting: string;
  problem: string;
  valid: boolean;
  problemSend: boolean;
  invalidPositions: string;
  hint: string;

  constructor(private restService: RestService) {
  }

  send(): void {
    this.restService.sendName(this.name).subscribe((value: {content, unknownUser}) => {
      this.greeting = value.content;
    });
  }

  submitProblem(no: number) {
    this.restService.sendProblem(this.name, no, this.problem).subscribe((result: {success, invalidPositions: number[], hint}) => {
      this.problemSend = true;
      this.valid = result.success
      this.invalidPositions = result.invalidPositions.join(', ');
      this.hint = result.hint
    });
  }
}
