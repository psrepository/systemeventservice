# SystemEventService
SystemEvent services exposes two rest end-points:

•	/process: This is to kick start the spring batch job to read events from the json file and save it to the Mongo database. 
Spring Batch uses custom ItemReader to read events from the json file. For this service, events.json file is in the resource folder.

•	/events: This returns list of events to the UI. Handles paging, filtering and sorting of result set.

## Constraint
- Minimum Java version : 1.8
