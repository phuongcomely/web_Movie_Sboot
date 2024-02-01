package org.example.movie.app.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.SSLHostConfigCertificate;
import org.example.movie.app.entity.Actor;
import org.example.movie.app.entity.Director;
import org.example.movie.app.entity.Genre;
import org.example.movie.app.entity.Movie;
import org.example.movie.app.exception.ResourceNotFoundException;
import org.example.movie.app.model.enums.MovieType;
import org.example.movie.app.model.request.UpsertDirectorRequest;
import org.example.movie.app.model.request.UpsertMovieRequest;
import org.example.movie.app.repository.ActorRepository;
import org.example.movie.app.repository.DirectorRepository;
import org.example.movie.app.repository.GenreRepository;
import org.example.movie.app.repository.MovieRepository;
import org.example.movie.app.utils.FileUtils;
import org.example.movie.app.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private  final MovieRepository movieRepository;
    private final HttpSession session;
    private final FileService fileService;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;


    public List<Movie> getAllMovies() {
        return movieRepository.findAll(Sort.by("createdAt").descending());
    }
    public Movie createMovie(UpsertMovieRequest request) {
        // Create movie
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .type(request.getType())
                .status(request.getStatus())
                .description(request.getDescription())
                .actors(actorRepository.findAllById(request.getActorIds()))
                .directors(directorRepository.findAllById(request.getDirectorIds()))
                .releaseYear(request.getReleaseYear())
                .build();
        return movieRepository.save(movie);
    }


    public Movie getMovieById(Integer id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim có id: " + id));
    }

    public Movie updateMovie(Integer id, UpsertMovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim với id = " + id));

        // update movie

        movie.setTitle(request.getTitle());
        movie.setDescription(request.getDescription());
        movie.setStatus(request.getStatus());
        movie.setType(request.getType());
        movie.setReleaseYear(request.getReleaseYear());
        movie.setActors(actorRepository.findAllById(request.getActorIds()));
        movie.setDirectors(directorRepository.findAllById(request.getDirectorIds()));

        return movieRepository.save(movie);
    }

    //xoa dao dien
    public void deleteMovie(Integer id) {
        Movie movie= movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim với id = " + id));

        // Kiểm tra xem director có avt không. Nếu có thì xóa file thumbnail
        if (movie.getPoster() != null) {
            FileUtils.deleteFile(movie.getPoster());
        }

        movieRepository.delete(movie);
    }

    public String uploadPoster(Integer id, MultipartFile file) {

        // Kỉem tra xem dao dien có tồn tại không
        Movie movie= movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim có id: " + id));

        // Upload file vào trong thư mục trên server (image_uploads)
        String filePath = fileService.uploadFile(file);

        // Cập nhật đường dẫn avt cho bài viết
        movie.setPoster(filePath);
        movieRepository.save(movie);

        return filePath;
    }

}
