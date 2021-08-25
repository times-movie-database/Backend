const mysql = require('mysql2');

const pool = mysql.createPool({
    host: 'root.cgte9rgghuql.us-east-2.rds.amazonaws.com',
    user: 'root',
    port: 3306,
    password: 'Aman2211',
    database: 'tmdb',
    connectionLimit: 5
});

function getTopTenMovieOverall(callback) {
    const getTopTenMoviesAllSQL = `SELECT DISTINCT movie_id AS id , title , rating
    FROM tmdb.movie m 
    JOIN tmdb.movie_genre mg
        ON m.id=mg.movie_id
    JOIN tmdb.genres g
        ON mg.genre_id=g.id
    ORDER BY rating DESC
        LIMIT 10;`;
    pool.getConnection((err, connection) => {
        if (err) throw err;
        console.log('connected as id ' + connection.threadId);
        connection.query( getTopTenMoviesAllSQL , (err, result) => {
            connection.release(); // return the connection to pool
            if (err) throw err;
            callback(result);
        });
    });
}

function getTopTenMovieFromGenre(genre, callback) {
    const getTopTenMovieFromGenreSQL = `SELECT DISTINCT movie_id AS id , title , rating
    FROM tmdb.movie m 
    JOIN tmdb.movie_genre mg
        ON m.id=mg.movie_id
    JOIN tmdb.genres g
        ON mg.genre_id=g.id
    WHERE g.name="${genre}"
    ORDER BY rating DESC
        LIMIT 10;`
    pool.getConnection((err, connection) => {
        if (err) throw err;
        console.log('connected as id ' + connection.threadId);
        connection.query( getTopTenMovieFromGenreSQL , (err, result) => {
            connection.release(); // return the connection to pool
            if (err) throw err;
            callback(result);
        });
    });
}

module.exports = {
    getTopTenMovieFromGenre: getTopTenMovieFromGenre,
    getTopTenMovieOverall: getTopTenMovieOverall
}