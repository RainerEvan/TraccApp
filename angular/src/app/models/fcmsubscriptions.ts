import { Accounts } from "./accounts";

export type FcmSubscriptions ={
    id: string;
    account: Accounts;
    token: string;
    timestamp: Date;
}