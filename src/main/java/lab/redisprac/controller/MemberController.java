package lab.redisprac.controller;

import lab.redisprac.controller.dto.MemberCreateRequest;
import lab.redisprac.domain.Member;
import lab.redisprac.service.MemberService;
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
        Member member = memberService.createMember(request.name(), request.email());
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Member> getMember(@PathVariable String name) {
        Member member = memberService.findCacheMember(name);
        return ResponseEntity.ok(member);
    }
}
