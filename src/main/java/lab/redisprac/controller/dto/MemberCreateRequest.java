package lab.redisprac.controller.dto;

import lab.redisprac.domain.Member;

public record MemberCreateRequest(String name, String email) {

    public Member createMember(String name, String email) {
        return new Member(name, email);
    }
}
