import { Division } from "./division";
import { Role } from "./role";

export type Account ={
    id: string;
    username: string;
    fullname: string;
    email: string;
    contactNo: string;
    division: Division;
    profileImg: any;
    isActive: boolean;
    roles: Role[];
}