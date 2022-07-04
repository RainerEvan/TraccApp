import { Role } from "./role";

export class AuthDetails{
    token: string;
    expirationDate: Date;
    accountId: string;
    username: string;
    email: string;
    isActive: boolean;
    roles: Role[];
} 