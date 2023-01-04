import { Accounts } from "./accounts";
import { Tickets } from "./tickets";

export type Comments ={
    id: string;
    ticket: Tickets;
    author: Accounts;
    createdAt: Date;
    updatedAt: Date;
    body: string;
    isActive: boolean;
}