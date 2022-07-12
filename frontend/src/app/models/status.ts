export enum EStatus{
    PENDING = 'PENDING',
    IN_PROGRESS = 'IN PROGRESS',
    RESOLVED = 'RESOLVED',
    CLOSED = 'CLOSED',
    DROPPED = 'DROPPED'
}

export type Status = {
    id:string;
    name:EStatus;
}