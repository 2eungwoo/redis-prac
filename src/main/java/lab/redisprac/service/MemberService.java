package lab.redisprac.service;

import jakarta.persistence.EntityNotFoundException;
import lab.redisprac.domain.Member;
import lab.redisprac.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CacheService cacheService;

    private static final long CACHE_TTL_SECONDS = 3600; // 1 hour

    public Member findCacheMember(String name) {
        final String cacheKey = "member:" + name;

        return cacheService.getOrLoad(
            cacheKey,
            Member.class,
            () -> memberRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + name)),
            CACHE_TTL_SECONDS
        );
    }

    public Member createMember(String name, String email) {
        Member member = new Member(name, email);
        memberRepository.save(member);
        return member;
    }
}
