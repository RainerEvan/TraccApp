import { Accounts } from "./accounts";
import { SupportAttachments } from "./supportattachments";
import { Tags } from "./tags";
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