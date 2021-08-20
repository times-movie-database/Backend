package com.times.tmdb;

import com.times.tmdb.model.Movie;
import com.times.tmdb.repository.MovieRepository;
import com.times.tmdb.response.movie.BriefMovieDisplay;
import com.times.tmdb.service.movie.MovieService;
import com.times.tmdb.service.movie.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TmdbApplicationTests {

    @Test
    void contextLoads() {
    }

}
