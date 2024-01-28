package org.example.movie.app.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.movie.app.entity.Blog;
import org.example.movie.app.entity.Director;
import org.example.movie.app.exception.ResourceNotFoundException;
import org.example.movie.app.model.request.UpsertBlogRequest;
import org.example.movie.app.model.request.UpsertDirectorRequest;
import org.example.movie.app.repository.DirectorRepository;
import org.example.movie.app.utils.FileUtils;
import org.example.movie.app.utils.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final HttpSession session;
    private final FileService fileService;
    // Lấy tất cả dao dien sắp xếp theo ten

    public List<Director> getAllDirectors() {
        return  directorRepository.findAll();

    }

    public Director createDirector(UpsertDirectorRequest request) {
        // Create director
        Director director = Director.builder()
                .name(request.getName())
                .description(request.getDescription())
                .birthday(request.getBirthday())
                .avatar(StringUtils.generateLinkImage(request.getName()))
                .build();

        return directorRepository.save(director);
    }

    public Director getDirectorById(Integer id) {
        return directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dao dien có id: " + id));
    }

    public Director updateDirector(Integer id, UpsertDirectorRequest request) {
        Director director = directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dao dien với id = " + id));

        // update director
        director.setName(request.getName());
        director.setDescription(request.getDescription());
        director.setBirthday(request.getBirthday());

        return directorRepository.save(director);
    }

    //xoa dao dien
    public void deleteDirector(Integer id) {
        Director director= directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dao dien với id = " + id));

        // Kiểm tra xem director có avt không. Nếu có thì xóa file thumbnail
        if (director.getAvatar() != null) {
            FileUtils.deleteFile(director.getAvatar());
        }

        directorRepository.delete(director);
    }

    public String uploadThumbnail(Integer id, MultipartFile file) {

        // Kỉem tra xem dao dien có tồn tại không
        Director director= directorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy dao dien có id: " + id));

        // Upload file vào trong thư mục trên server (image_uploads)
        String filePath = fileService.uploadFile(file);

        // Cập nhật đường dẫn avt cho bài viết
        director.setAvatar(filePath);
        directorRepository.save(director);

        return filePath;
    }
}
