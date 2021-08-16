const expressModule = require('express');
const dal = require('./data-access-layer')
const server = expressModule();

server.get('/top-ten', ( request, response ) => {
    Object.keys(request.query).length===0?dal.getTopTenMovieOverall( (result)=>{
        response.send( result );
    } )
    :dal.getTopTenMovieFromGenre( request.query.genre, (result) =>{
        response.send( result );
    } );
    
})

server.get('/', (req,res)=>{
    res.send("server running, serves on /top-ten")
})
server.listen(3006, ( request, response ) => {
    console.log("server started");
})