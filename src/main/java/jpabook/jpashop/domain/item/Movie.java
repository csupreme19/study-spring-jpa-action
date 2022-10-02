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
@DiscriminatorValue("M")
@NoArgsConstructor(access = PROTECTED)
public class Movie extends Item {

    private String director;
    private String actor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(director, movie.director) && Objects.equals(actor, movie.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(director, actor);
    }
}
