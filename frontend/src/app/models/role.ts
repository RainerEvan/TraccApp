export enum ERole{
    ADMIN,
    SUPERVISOR,
    DEVELOPER,
    USER
}

export type Role ={
    id:string;
    name:ERole;
}