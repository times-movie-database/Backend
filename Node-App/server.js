const expressModule = require('express');
const dal = require('./data-access-layer')
const server = expressModule();

server.get('/top-ten', ( request, response ) => {
      response.send(request.query);
})

server.listen(3000, ( request, response ) => {
    console.log("server started");
})