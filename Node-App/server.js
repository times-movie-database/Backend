const expressModule = require('express');

const cors=require('cors');
const dal = require('./data-access-layer')
const server = expressModule();

server.use(cors({
    origin: '*'
}));
server.get('/tmdb/movies/top-ten', ( request, response ) => {
    Object.keys(request.query).length===0?dal.getTopTenMovieOverall( (result)=>{
        response.send( result );
    } )
    :dal.getTopTenMovieFromGenre( request.query.genre, (result) =>{
        response.send( result );
    } );
    
})

server.get('/', (req,res)=>{
    res.send("server running, serves on /tmdb/movies/top-ten")
})
server.get('/tmdb', (req,res)=>{
    res.send("server serves on /movies/top-ten")
})
server.get('/tmdb/movies', (req,res)=>{
    res.send("server serves on /top-ten")
})
server.listen(process.env.PORT||3006, ( request, response ) => {
    console.log("server started");
})