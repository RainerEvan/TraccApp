import { Roles } from "./roles";

export class AuthDetails{
    token: string;
    expirationDate: Date;
    accountId: string;
    username: string;
    isActive: boolean;
    roles: string[];
} 