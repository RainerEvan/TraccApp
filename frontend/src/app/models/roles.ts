export enum ERoles{
    ADMIN = 'ADMIN',
    SUPERVISOR = 'SUPERVISOR',
    DEVELOPER = 'DEVELOPER',
    USER = 'USER'
}

export type Roles ={
    id:string;
    name:ERoles;
}