package lab.redisprac.controller;

import lab.redisprac.domain.Member;
import lab.redisprac.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody MemberCreateRequest request) {
        Member member = memberService.createMember(request.getUsername());
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Member> getMember(@PathVariable String username) {
        Member member = memberService.findMemberByUsername(username);
        return ResponseEntity.ok(member);
    }

    // Simple DTO for the request
    @Getter
    public static class MemberCreateRequest {
        private String username;
    }
}
