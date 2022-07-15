import { Accounts } from "./accounts";
import { SupportAttachments } from "./supportattachments";
import { Tags } from "./tags";
import { Tickets } from "./tickets";

export type Supports ={
    id: string;
    ticket: Tickets;
    dateTaken: Date;
    developer: Accounts;
    result: string;
    description: string;
    tags: Tags[];
    devNote: string;
    isActive: boolean;
    attachments: SupportAttachments[];
}