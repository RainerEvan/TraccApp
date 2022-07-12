export enum ERole{
    ADMIN = 'ADMIN',
    SUPERVISOR = 'SUPERVISOR',
    DEVELOPER = 'DEVELOPER',
    USER = 'USER'
}

export type Role ={
    id:string;
    name:ERole;
}