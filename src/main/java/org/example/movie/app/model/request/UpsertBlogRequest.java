package org.example.movie.app.model.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpsertBlogRequest { //phục vụ cho vuieejc thêm va cap nhat ve blogs
    String title;
    String description;
    String content;
    Boolean status;
}