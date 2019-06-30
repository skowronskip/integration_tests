package edu.iis.mto.blog.domain;

import edu.iis.mto.blog.domain.model.BlogPost;
import edu.iis.mto.blog.domain.model.LikePost;
import edu.iis.mto.blog.domain.repository.BlogPostRepository;
import edu.iis.mto.blog.domain.repository.LikePostRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.iis.mto.blog.api.request.UserRequest;
import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;
import edu.iis.mto.blog.domain.repository.UserRepository;
import edu.iis.mto.blog.mapper.BlogDataMapper;
import edu.iis.mto.blog.services.BlogService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogManagerTest {

    @MockBean
    UserRepository userRepository;

    @MockBean
    BlogPostRepository blogPostRepository;

    @MockBean
    LikePostRepository likePostRepository;

    @Autowired
    BlogDataMapper dataMapper;

    @Autowired
    BlogService blogService;

    private User confirmedUser;
    private User unconfirmedUser;
    private BlogPost blogPost;

    @Before
    public void setUp() {
        confirmedUser = new User();
        confirmedUser.setFirstName("Confirmed");
        confirmedUser.setLastName("Confirmed");
        confirmedUser.setEmail("confirmed@domain.com");
        confirmedUser.setAccountStatus(AccountStatus.CONFIRMED);
        confirmedUser.setId(10L);

        unconfirmedUser = new User();
        unconfirmedUser.setFirstName("Unconfirmed");
        unconfirmedUser.setLastName("Unconfirmed");
        unconfirmedUser.setEmail("unconfirmed@domain.com");
        unconfirmedUser.setAccountStatus(AccountStatus.NEW);
        unconfirmedUser.setId(20L);

        blogPost = new BlogPost();
        blogPost.setEntry("test");
        blogPost.setUser(unconfirmedUser);
    }

    @Test
    public void creatingNewUserShouldSetAccountStatusToNEW() {
        blogService.createUser(new UserRequest("John", "Steward", "john@domain.com"));
        ArgumentCaptor<User> userParam = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userParam.capture());
        User user = userParam.getValue();
        Assert.assertThat(user.getAccountStatus(), Matchers.equalTo(AccountStatus.NEW));
    }

    @Test
    public void likePostByConfirmedUser() {
        Mockito.when(userRepository.findById(confirmedUser.getId())).thenReturn(Optional.of(confirmedUser));
        Mockito.when(blogPostRepository.findById(blogPost.getId())).thenReturn(Optional.of(blogPost));

        blogService.addLikeToPost(confirmedUser.getId(), blogPost.getId());

        ArgumentCaptor<LikePost> likeParam = ArgumentCaptor.forClass(LikePost.class);
        Mockito.verify(likePostRepository).save(likeParam.capture());
        LikePost like = likeParam.getValue();
        Assert.assertThat(like, Matchers.notNullValue());
    }

}
