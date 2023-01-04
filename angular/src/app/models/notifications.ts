import { Accounts } from "./accounts";

export type Notifications ={
    id: string;
    receiver: Accounts;
    createdAt: Date;
    readAt: Date;
    title: string;
    body: string;
    data: string;
}