import { Accounts } from "./accounts";
import { Members } from "./members";

export type Teams ={
    id:string;
    name:string;
    supervisor:Accounts
    members:Members[]
}