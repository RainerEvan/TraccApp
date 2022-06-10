import { Ticket } from "./ticket";

export type TicketAttachment ={
    id: string;
    ticket: Ticket;
    fileBase64: string;
    fileName: string;
    fileType: string;
    fileSize: string;
}