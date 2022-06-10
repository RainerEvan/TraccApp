import { Account } from "./account";
import { SupportAttachment } from "./supportattachment";
import { Tag } from "./tag";
import { Ticket } from "./ticket";

export type Support ={
    id: string;
    ticket: Ticket;
    dateTaken: Date;
    developer: Account;
    result: string;
    description: string;
    tags: Array<Tag>;
    devNote: string;
    isActive: boolean;
    attachments: SupportAttachment[];
}