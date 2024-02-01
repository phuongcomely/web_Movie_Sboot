package org.example.movie.app.rest;

import lombok.RequiredArgsConstructor;
import org.example.movie.app.entity.Director;
import org.example.movie.app.entity.Movie;
import org.example.movie.app.model.request.UpsertDirectorRequest;
import org.example.movie.app.model.request.UpsertMovieRequest;
import org.example.movie.app.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin/movies")
@RequiredArgsConstructor
public class MovieResource {
    private final MovieService movieService;
    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody UpsertMovieRequest request) {
        Movie movie = movieService.createMovie(request);
        return ResponseEntity.ok(movie); // status code 200
    }
    // Cập nhật review - PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDirector(@RequestBody UpsertMovieRequest request, @PathVariable Integer id) {
        Movie movie = movieService.updateMovie(id, request);
        return ResponseEntity.ok(movie); // status code 200
    }

    // Xóa review - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build(); // status code 204
    }

    // Upload thumbnail - POST
    @PostMapping("/{id}/upload-thumbnail")
    public ResponseEntity<?> uploadPoster(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
        String filePath = movieService.uploadPoster(id, file);
        return ResponseEntity.ok(filePath); // status code 200
    }
}
