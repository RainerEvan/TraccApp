import { Tickets } from "./tickets";

export type TicketAttachments ={
    id: string;
    ticket: Tickets;
    fileBase64: string;
    fileName: string;
    fileType: string;
    fileSize: string;
}