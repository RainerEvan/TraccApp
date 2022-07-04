import { Account } from "./account";
import { Application } from "./application";
import { Status } from "./status";
import { Support } from "./support";
import { TicketAttachment } from "./ticketattachment";

export type Ticket ={
    ticketId: string;
    ticketNo: string;
    application: Application;
    dateAdded: Date;
    reporter: Account;
    title: string;
    description: string;
    dateClosed: Date;
    status: Status;
    support: Support[];
    attachments: TicketAttachment[];
}

