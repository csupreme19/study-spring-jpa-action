package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@SuperBuilder
@DiscriminatorValue("B")
@NoArgsConstructor(access = PROTECTED)
public class Book extends Item {

    private String author;
    private String isbn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, isbn);
    }
}
