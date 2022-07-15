import { Divisions } from "./divisions";
import { Roles } from "./roles";

export type Accounts ={
    id: string;
    username: string;
    fullname: string;
    email: string;
    contactNo: string;
    division: Divisions;
    profileImg: any;
    isActive: boolean;
    roles: Roles[];
}