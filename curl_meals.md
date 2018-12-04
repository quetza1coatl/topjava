#### Get all meals 
`curl http://localhost:8080/topjava/rest/meals`
#### Create meal  
`curl -X POST -H 'Content-Type:application/json' --data '{"dateTime":"2018-12-04T20:54", "description": "New dinner", "calories":210}' http://localhost:8080/topjava/rest/meals` 
#### Update meal  
`curl -X PUT -H 'Content-Type:application/json' --data '{"id":100010,"dateTime":"2018-12-04T20:54", "description": "Updated new dinner", "calories":499}' http://localhost:8080/topjava/rest/meals/100010` 
#### Delete meal  
`curl -X DELETE http://localhost:8080/topjava/rest/meals/100010`
#### Get meals(filter)  
`curl  "http://localhost:8080/topjava/rest/meals/filter?startDate=2015-05-30&startTime=11:00:00&endDate=2015-05-30&endTime=21:00:00"`