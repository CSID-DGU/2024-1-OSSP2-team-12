package com.oos12.scansavvy.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

public class MemberDTO {
    private String id;
    private Long idx;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String residenceId;

    public MemberDTO(String id, String name, String email, String password, String phone, String residenceId){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.residenceId = residenceId;
    }
}
