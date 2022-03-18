package com.authserver.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.authserver.domain.dto.response.CreatedUser;
import com.authserver.domain.mapper.CreateUserMapper;
import com.authserver.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserMapperTest {

    @InjectMocks
    private CreateUserMapper userMapper;

    @Test
    public void toDTO_entityOk_dto() {

        User user = new User() {{
            setId(1L);
            setEmail("email");
            setFirstName("firstName");
            setLastName("lastName");
            setPassword("asd");
        }};

        CreatedUser cu = userMapper.toDto(user);

        assertNotNull(user);
        assertEquals(cu.getEmail(), user.getEmail());
        assertEquals(cu.getFirstName(), user.getFirstName());
        assertEquals(cu.getLastName(), user.getLastName());
        assertEquals(cu.getId(), user.getId());
    }
    
}
