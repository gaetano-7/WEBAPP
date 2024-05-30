import { Component } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-successdialog',
  templateUrl: './fineastadialog.component.html',
  styleUrls: ['./fineastadialog.component.css'],
  imports: [MatDialogModule, MatButtonModule],
  standalone: true,
})
export class fineastadialogComponent {}
