package com.oos12.scansavvy.member.model;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "member")
public class Member {
    @Transient
    public static final String SEQUENCE_NAME = "members_sequence";

    @Id
    private String id;
    private Long idx;
    @NotBlank
    @Size(max = 10)
    private String name;
    @NotBlank
    @Size(max = 10)
    @Indexed(unique = true)
    private String email;
    @NotBlank
    @Size(min = 10)
    private String password;
    private String phone;
    private String residenceId;


    @Override
    public String toString(){
        return "Member{" +
                "id='" + id + '\'' +
                ", name='" + name +'\'' +
                ", email='" + email +'\'' +
                ", password=" + password +
                ", phone=" + phone +
                ", residenceId=" + residenceId +
                '}';
    }
}
