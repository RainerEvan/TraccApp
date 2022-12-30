<body>
<div style="background-color:whitesmoke;padding:2rem">
<span style="display:flex;align-items:center;padding:0.5rem;border-bottom:1px solid black;">
<img src="/image/tracc.png" alt="Logo" width="36px" height="36px"/>
<span style="font-style:italic;font-weight:600;font-size:1.25rem;letter-spacing:0.1em;">TRACC - Activity</span>
</span><br>
<h1 style="font-weight:600;font-size:1.5rem">${notification.title}</h1><br>
<span>Dear, </span><span style="font-weight:600">${notification.receiver.fullname}</span><br><br>
<span>${notification.body}. See more details in the Tracc App or by clicking the button below</span><br><br>
<a href="http://localhost:4200/#/notifications/${notification.id}" style="background-color:slategrey;padding:0.75rem;font-weight:500;font-size:0.875rem;text-decoration:none;color:white;">See Details</a>
</div>
 </body>  