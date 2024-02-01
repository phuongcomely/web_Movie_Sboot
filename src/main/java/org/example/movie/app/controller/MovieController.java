package org.example.movie.app.controller;

import lombok.RequiredArgsConstructor;
import org.example.movie.app.entity.Actor;
import org.example.movie.app.entity.Director;
import org.example.movie.app.entity.Genre;
import org.example.movie.app.entity.Movie;
import org.example.movie.app.service.ActorService;
import org.example.movie.app.service.DirectorService;
import org.example.movie.app.service.GenreService;
import org.example.movie.app.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/films")
public class MovieController {
  private final MovieService movieService;
  private final ActorService actorService;
  private final DirectorService directorService;
private final GenreService genreService;

    @GetMapping
    public String getHomePage(Model model) {
        // Lấy tất cả cac dao dien sap xep thep ten
        List<Movie> movieList = movieService.getAllMovies();
        model.addAttribute("movieList", movieList);
        return "admin/movie/index";
    }
    @GetMapping("/create")
    public String getCreatePage(Model model) {

        return "admin/movie/create";
    }

    // Chi tiết dao dien
    @GetMapping("/{id}/detail")
    public String getDetailPage(@PathVariable Integer id, Model model) {
        /*
         * Trả về movie theo id
         * Trả về danh sách tất cả diễn viên
         * Trả về danh sách tất cả đạo diễn
         * Trả về danh sách tất cả thể loại
         * */
        Movie movie = movieService.getMovieById(id);
        List<Actor> actorList = actorService.getAllActors();
        List<Director> directorList = directorService.getAllDirectors();
        List<Genre> genreList = genreService.getAllGenres();

        model.addAttribute("movie", movie);
        model.addAttribute("actorList", actorList);
        model.addAttribute("directorList", directorList);
        model.addAttribute("genreList", genreList);
        return "admin/movie/detail";
    }
    @GetMapping("/{id}/detail")
    public String getCreatePage(@PathVariable Integer id, Model model) {
        /*
         * Trả về movie theo id
         * Trả về danh sách tất cả diễn viên
         * Trả về danh sách tất cả đạo diễn
         * Trả về danh sách tất cả thể loại
         * */

        List<Actor> actorList = actorService.getAllActors();
        List<Director> directorList = directorService.getAllDirectors();
        List<Genre> genreList = genreService.getAllGenres();
        model.addAttribute("actorList", actorList);
        model.addAttribute("directorList", directorList);
        model.addAttribute("genreList", genreList);
        return "admin/movie/create";
    }
}
