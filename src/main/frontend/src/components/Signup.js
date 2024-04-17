// Signup.js
import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Signup.css";

const Signup = () => {
    const [signupInfo, setSignupInfo] = useState({
        username: "",
        id: "",
        password: "",
        confirmPassword: "",
        email: "",
    });

    const [passwordMismatch, setPasswordMismatch] = useState(false);
    const [confirmPasswordStyle, setConfirmPasswordStyle] = useState(null);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSignupInfo({
            ...signupInfo,
            [name]: value,
        });

        if (name === "confirmPassword" && value.trim() === "") {
            document
                .getElementById("confirmPassword")
                .classList.remove("error", "success");
            return;
        }

        if (name === "confirmPassword" && signupInfo.password) {
            if (value === signupInfo.password) {
                document.getElementById("confirmPassword").classList.remove("error");
                document.getElementById("confirmPassword").classList.add("success");
            } else {
                document.getElementById("confirmPassword").classList.remove("success");
                document.getElementById("confirmPassword").classList.add("error");
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (signupInfo.password !== signupInfo.confirmPassword) {
            setPasswordMismatch(true);
        } else {
            setPasswordMismatch(false);
            // 회원가입 처리 로직을 여기에 작성하세요.
            try {
                const response = await fetch("http://your-backend-url/signup", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(signupInfo),
                });

                if (response.ok) {
                    const data = await response.json();
                    if (data.isMember) {
                        alert("이미 회원입니다.");
                    } else {
                        alert("회원가입 성공!");
                    }
                } else {
                    console.error("회원가입 실패");
                }
            } catch (error) {
                console.error("오류 발생:", error);
            }
        }
    };

    return (
        <div className="container_signup">
            <div className="title_signup">회원가입</div>
            <form onSubmit={handleSubmit}>
                <input
                    type="text"
                    name="username"
                    value={signupInfo.username}
                    onChange={handleChange}
                    className="input-field_signup"
                    placeholder="이름"
                />
                <br />
                <input
                    type="text"
                    name="id"
                    value={signupInfo.id}
                    onChange={handleChange}
                    className="input-field_signup"
                    placeholder="아이디"
                />
                <br />
                <input
                    type="password"
                    name="password"
                    value={signupInfo.password}
                    onChange={handleChange}
                    className="input-field_signup"
                    placeholder="비밀번호"
                />
                <br />
                <input
                    type="password"
                    name="confirmPassword"
                    id="confirmPassword"
                    value={signupInfo.confirmPassword}
                    onChange={handleChange}
                    className={`input-field_signup ${confirmPasswordStyle}`}
                    placeholder="비밀번호 확인"
                />
                <br />
                <input
                    type="email"
                    name="email"
                    value={signupInfo.email}
                    onChange={handleChange}
                    className="input-field_signup"
                    placeholder="이메일"
                />
                <br />
                <button type="submit" className="signup_button">
                    확인
                </button>
                <p className="login_link">
                    <Link to="/login">이미 회원입니다</Link>
                </p>
            </form>
        </div>
    );
};

export default Signup;
