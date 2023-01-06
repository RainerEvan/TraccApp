import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Teams } from 'src/app/models/teams';
import { TeamService } from 'src/app/services/team/team.service';

@Component({
  selector: 'app-team-detail',
  templateUrl: './team-detail.component.html',
  styleUrls: ['./team-detail.component.css']
})
export class TeamDetailComponent implements OnInit {

  team: Teams;
  loading: boolean;

  constructor(private route:ActivatedRoute, private teamService: TeamService) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.getTeam(params['id']);
    });
  }

  public getTeam(teamId:string):void{
    if(teamId){
      this.loading = true;

      this.teamService.getTeam(teamId).subscribe({
        next: (team: Teams) => {
          this.loading = false;
          this.team = team;
        },
        error: (error: any) => {
          console.log(error);
        }
      });
    }
  }

}
