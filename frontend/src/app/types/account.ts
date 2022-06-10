import { Division } from "./division";
import { Role } from "./role";

export type Account ={
    id: string;
    username: string;
    email: string;
    contactNo: string;
    division: Division;
    profileImg: string;
    isActive: boolean;
    roles: Role[];
}