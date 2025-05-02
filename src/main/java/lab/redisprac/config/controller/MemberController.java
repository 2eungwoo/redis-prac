package lab.redisprac.config.controller;

import lab.redisprac.domain.Member;
import lab.redisprac.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final CacheService cacheService;

    @GetMapping("/api/users/{id}")
    public ResponseEntity<Member> getUser(@PathVariable Long id) {
        return cacheService.getCachedData("member:" + id, Member.class)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<Member> createUser(@RequestBody Member member) {
        cacheService.cacheData("member:" + member.getId(), member, 3600);
        return ResponseEntity.ok(member);
    }
}