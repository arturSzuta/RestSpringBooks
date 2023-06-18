package pl.sda.restspringbooks.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Length(max = 50, min = 2, message = "Title has to be longer than 2 characters")
    private String title;
    @Range(min = 1900, message = "Edition year has to be bigger than 1900")
    private int editionYear;
    private String authors;
    public List<String> getAuthors(){
        return Arrays.stream(authors.split("\\|")).toList();
    }
    public void srtAuthors(List<String> authors){
        this.authors = authors.stream().collect(Collectors.joining("|"));
    }
}
