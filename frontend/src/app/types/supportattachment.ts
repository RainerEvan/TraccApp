import { Support } from "./support";

export type SupportAttachment ={
    id: string;
    support: Support;
    fileBase64: string;
    fileName: string;
    fileType: string;
    fileSize: string;
}