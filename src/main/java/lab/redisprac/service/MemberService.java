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

    public Member createMember(String username) {
        return memberRepository.save(new Member(username));
    }

    public Member findMemberByUsername(String username) {
        final String cacheKey = "member:" + username;

        // getOrLoad를 호출하여 캐시 로직을 위임합니다.
        return cacheService.getOrLoad(
            cacheKey,          // 캐시 키
            Member.class,      // 반환 타입
            () -> memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username)), // DB 조회 로직
            CACHE_TTL_SECONDS  // 캐시 시간
        );
    }
}
