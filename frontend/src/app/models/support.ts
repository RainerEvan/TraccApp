import { Account } from "./account";
import { SupportAttachment } from "./supportattachment";
import { Tags } from "./tags";
import { Ticket } from "./ticket";

export type Support ={
    id: string;
    ticket: Ticket;
    dateTaken: Date;
    developer: Account;
    result: string;
    description: string;
    tags: Tags[];
    devNote: string;
    isActive: boolean;
    attachments: SupportAttachment[];
}