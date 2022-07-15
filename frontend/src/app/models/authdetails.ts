import { Roles } from "./roles";

export class AuthDetails{
    token: string;
    expirationDate: Date;
    accountId: string;
    username: string;
    email: string;
    isActive: boolean;
    roles: Roles[];
} 