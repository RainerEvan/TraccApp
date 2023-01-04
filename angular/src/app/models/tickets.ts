import { Accounts } from "./accounts";
import { Applications } from "./applications";
import { Status } from "./status";
import { Supports } from "./supports";
import { TicketAttachments } from "./ticketattachments";

export type Tickets ={
    ticketId: string;
    ticketNo: string;
    application: Applications;
    dateAdded: Date;
    reporter: Accounts;
    title: string;
    description: string;
    dateResolved: Date;
    status: Status;
    support: Supports;
    attachments: TicketAttachments[];
}

