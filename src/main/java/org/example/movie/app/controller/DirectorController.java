package org.example.movie.app.controller;

import lombok.RequiredArgsConstructor;
import org.example.movie.app.entity.Blog;
import org.example.movie.app.entity.Director;
import org.example.movie.app.service.DirectorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/directors")
public class DirectorController {
    private final DirectorService directorService;

    // Danh sách tất cả cac dao dien
    @GetMapping
    public String getHomePage(Model model) {
        // Lấy tất cả cac dao dien sap xep thep ten
        List<Director> directorList = directorService.getAllDirectors();
        model.addAttribute("directorList", directorList);
        return "admin/director/index";
    }
    @GetMapping("/create")
    public String getCreatePage(Model model) {

        return "admin/director/create";
    }

    // Chi tiết dao dien
    @GetMapping("/{id}/detail")
    public String getDetailPage(@PathVariable Integer id, Model model) {
        // Lấy bài viết theo id
        Director director = directorService.getDirectorById(id);
        model.addAttribute("director", director);
        return "admin/director/detail";
    }
}
