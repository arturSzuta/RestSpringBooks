package pl.sda.restspringbooks.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
public class Post {
    private long id;
    @NotNull(message = "Title can't be null")
    @NotEmpty(message = "Title can't be empty")
    private String title;
    @NotEmpty(message = "Content can't be empty")
    @Length(max = 2000, message = "Content can't be longer than 2000 characters")
    private String content;
}
