package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Album;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() throws Exception {
        // given
        Member member = Member.builder()
                .name("member")
                .build();
        member.changeName("member1");

        // when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.findById(saveId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getName()).isEqualTo(member.getName());
        assertThat(findMember).isEqualTo(member);

    }

    @Test
    public void ItemDiscriminatorTest() throws Exception {
        // given
        Album album = Album.builder()
                .name("album1")
                .price(1000)
                .stockQuantity(10)
                .artist("artist1")
                .etc("etc1")
                .build();
        em.persist(album);
        Book book = Book.builder()
                .name("book1")
                .price(500)
                .stockQuantity(93)
                .author("author1")
                .isbn("isbn1")
                .build();
        em.persist(book);
        Movie movie = Movie.builder()
                .name("movie1")
                .price(700)
                .stockQuantity(3)
                .actor("actor1")
                .director("director1")
                .build();
        em.persist(movie);

        em.flush();
        em.clear();

        // when
        Album findAlbum = em.find(Album.class, album.getId());
        Book findBook = em.find(Book.class, book.getId());
        Movie findMovie = em.find(Movie.class, movie.getId());

        // then
        assertThat(album).isEqualTo(findAlbum);
        assertThat(book).isEqualTo(findBook);
        assertThat(movie).isEqualTo(findMovie);
    }

}