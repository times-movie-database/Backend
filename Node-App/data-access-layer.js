const mysql = require('mysql2');

const pool = mysql.createPool({
    host: process.env.DATABASE_HOST_URL,
    user: 'root',
    port: 3306,
    password: 'root',
    database: 'movie',
    connectionLimit: 5
});

function getTopTenMovieOverall(callback) {
    const getTopTenMoviesAllSQL = `SELECT movie_id AS id , title , rating
    FROM movie.movie m 
    JOIN movie.movie_genre mg
        ON m.id=mg.movie_id
    JOIN movie.genres g
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
    const getTopTenMovieFromGenreSQL = `SELECT movie_id AS id , title , rating
    FROM movie.movie m 
    JOIN movie.movie_genre mg
        ON m.id=mg.movie_id
    JOIN movie.genres g
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