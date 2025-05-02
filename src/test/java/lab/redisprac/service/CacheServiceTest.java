package lab.redisprac.service;

import lab.redisprac.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CacheServiceTest {

    @Autowired
    private CacheService cacheService;

    @Test
    void cacheDataTest() {
        // given
        Member member = new Member(1L, "tester1 name", "tester1@example.com");

        // when
        cacheService.cacheData("user:1", member, 60);
        Optional<Member> cachedUser = cacheService.getCachedData("member:1", Member.class);

        // then
        assertTrue(cachedUser.isPresent());
        assertEquals(member.getName(), cachedUser.get().getName());
    }
}