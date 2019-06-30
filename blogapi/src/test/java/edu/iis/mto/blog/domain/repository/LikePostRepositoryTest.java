package edu.iis.mto.blog.domain.repository;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.model.User;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LikePostRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LikePostRepository repository;

    private LikePost likePost;
    private BlogPost blogPost;
    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Nowak");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
        entityManager.persist(user);
        blogPost = new BlogPost();
        blogPost.setUser(user);
        blogPost.setEntry("Test");
        entityManager.persist(blogPost);
        likePost = new LikePost();
        likePost.setPost(blogPost);
        likePost.setUser(user);
    }

    @Test
    public void shouldFindNoLikesIfRepositoryIsEmpty() {

        List<LikePost> likePosts = repository.findAll();

        Assert.assertThat(likePosts, Matchers.hasSize(0));
    }

    @Test
    public void shouldFindOneLikeIfRepositoryContainsOneLikeEntity() {
        LikePost persistedLikePost = entityManager.persist(likePost);
        List<LikePost> likePosts = repository.findAll();

        Assert.assertThat(likePosts, Matchers.hasSize(1));
        Assert.assertThat(likePosts.get(0).getPost(), Matchers.equalTo(persistedLikePost.getPost()));
        Assert.assertThat(likePosts.get(0).getUser(), Matchers.equalTo(persistedLikePost.getUser()));
    }
}
