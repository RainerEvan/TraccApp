export type DashboardActivity ={
    menu:string;
    period:string;
    totalPending:number;
    totalInProgress:number;
    totalResolved:number;
    totalDropped:number;
    totalTickets:number;
    label:string[];
    data:number[];
}

export type DashboardAnalytics ={
    period:string;
    minTickets:number;
    maxTickets:number;
    avgTickets:number;
    totalTickets:number;
    label:string[];
    data:number[];
    topApplications:TopApplications[];
    topTags:TopTags[];
    ticketRate:TicketRate;
}

export type TopApplications ={
    application:string;
    count:number;
}


export type TopTags ={
    tag:string;
    count:number;
}

export type TicketRate ={
    label:string;
    rate:number;
}