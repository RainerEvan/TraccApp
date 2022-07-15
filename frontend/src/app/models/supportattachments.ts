import { Supports } from "./supports";

export type SupportAttachments ={
    id: string;
    support: Supports;
    fileBase64: string;
    fileName: string;
    fileType: string;
    fileSize: string;
}