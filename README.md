To run the program locally:
1. Start mongodb service on port 27017, create two databases named 'db0' and 'db1',
   create a collection named 'conf' in db0, insert a row {'numDatabases' : 2}.
2. Run Distributed-Mongodb as Spring Application, where edu.guopengl.Application is the Main class.
3. Run the following in command line:
   1. curl -POST "http://localhost:8080/createCollection" -d '{"collectionName":"user","partitionKey":"name"}' -H "Content-Type:application/json"
   2. curl -POST "http://localhost:8080/createCollection" -d '{"collectionName":"game","partitionKey":"name"}' -H "Content-Type:application/json"
   3. curl -POST "http://localhost:8080/createCollection" -d '{"collectionName":"purchase_record","partitionKey":"username"}' -H "Content-Type:application/json"
   4. curl -POST "http://localhost:8080/createCollection" -d '{"collectionName":"like_record","partitionKey":"username"}' -H "Content-Type:application/json"
   5. curl -POST "http://localhost:8080/insertMany" -d '{
      "collectionName":"user",
      "data":[
      {
      "name": "admin",
      "password": "123",
      "role": 0,
      "balance": 1000000
      },
      {
      "name": "lgp",
      "password": "123",
      "role": 1,
      "balance": 100
      }
      ]
      }' -H "Content-Type:application/json"
   6. curl -POST "http://localhost:8080/insertMany" -d '{
      "collectionName":"game",
      "data":[
      {
      "name": "Wii Sports",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "New Super Mario Bros. Wii",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "Call of Duty: Modern Warfare",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 99
      },
      {
      "name": "Super Mario Bros.",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "Counter-Strike: Global Offensive",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "Mario Kart Wii",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "PLAYERUNKNOWN'S BATTLEGROUNDS",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "Minecraft",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "Wii Sports Resort",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "Pokemon Red / Green / Blue Version",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "New Super Mario Bros.",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      },
      {
      "name": "Tetris",
      "platform": "Wii",
      "publisher": "Nintendo",
      "developer": "Nintendo EAD",
      "criticScore": 7.7,
      "userScore": 8,
      "year": 2006,
      "price": 8.29
      }
      ]
      }' -H "Content-Type:application/json"
4. Run games as Spring Application, where edu.guopengl.Application is the Main class.
