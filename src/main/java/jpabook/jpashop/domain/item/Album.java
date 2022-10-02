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
@DiscriminatorValue("A")
@NoArgsConstructor(access = PROTECTED)
public class Album extends Item {

    private String artist;
    private String etc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(artist, album.artist) && Objects.equals(etc, album.etc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, etc);
    }
}

