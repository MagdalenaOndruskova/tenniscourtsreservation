# Tennis courts reservation application 
#####  REST endpoints implemented 

*Author:* Magdaléna Ondrušková

## Endpoints 

- api/v1/court -- returns list of courts 
- api/v1/reservation -- returns list of reservations 
- api/v1/reservation/find/court_id/{courtId} -- list of reservation for given court id 
- api/v1/reservation/find/telephone_number/{telephoneNumber} -- list of reservation for given telephone number 
- api/v1/reservation/{courtId}/new" -- creating new reservation for given court (court id), returns price of reservation 
- api/v1/reservation/{reservationId} -- deletes given reservation 
