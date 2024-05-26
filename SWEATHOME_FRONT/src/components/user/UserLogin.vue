<template>
    <div id="login-container">
        <div id="login">
            <h1>LOGIN</h1>
            <div id="login-form">
                <input type="text" placeholder="아이디를 입력하세요" v-model.trim="id">
                <input type="password" placeholder="패스워드를 입력하세요" v-model.trim="password" @keyup.enter="login">
                <div class="error" v-if="store.loginError !== ''">{{ store.loginError }}</div>
                <button @click="login">로그인</button>
            </div>
            <hr class="hr">
            <div id="login-plus">
                <a href="#" @click.prevent="kakaoLogin">
                    <i class="ph-fill ph-chat-circle"></i>
                    카카오 간편 로그인
                </a>
            </div>
            <div id="login-etc">
                <RouterLink :to="{name: 'find'}">패스워드를 잊어버리셨나요?</RouterLink>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/stores/user';
import axios from 'axios';

import router from '@/router'

const store = useUserStore();
const id = ref("")
const password = ref("")

const login = function() {
    store.login(id.value, password.value)
}

const kakaoLogin = () => {
  if (!window.Kakao.isInitialized()) {
    window.Kakao.init('3823cd8a7e6d7186ed47fc4cacf66ae0');
  }
  window.Kakao.Auth.login({
    scope: "profile_nickname, account_email",
    success: getKakaoAccount,
  });
};

const getKakaoAccount = () => {
  window.Kakao.API.request({
    url: "/v2/user/me",
    success: (res) => {
      const kakao_account = res.kakao_account;
      const nickname = kakao_account.profile.nickname;
      const email = kakao_account.email;
      console.log("nickname", nickname);
      console.log("email", email);

      const kakaoUser = {
        userName: nickname,
        userId: email
      };

      // Axios를 사용하여 POST 요청을 보냅니다.
      axios.post('http://localhost:8080/api-user/kakaologin', kakaoUser)
      .then(response => {
        sessionStorage.setItem('access-token', response.data["access-token"])
            const token = response.data['access-token'].split('.')
            let userId = JSON.parse(atob(token[1]))['id']
            store.loginUserId = email;
        router.push({name: 'videoList'});
      })
      .catch(error => {
        console.error('Error:', error);
      });
    },
    fail: (error) => {
      console.log(error);
    },
  });
};
</script>

<style scoped>
#login-container {
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(45deg, #d04b71, #33001b);
    display: flex;
    justify-content: center;
    align-items: center;
}

#login {
    width: 360px;
    padding: 48px;
    border-radius: 20px;
    background-color: white;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.3);
}

h1 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 40px;
}

#login-form {
    display: flex;
    flex-direction: column;
}

#login-form input {
    font-size: 14px;
    padding: 8px 12px;
    border: none;
    border-bottom: 1px solid #878787;
    background-color: transparent;
    margin-bottom: 24px;
}

#login-form input:focus {
    outline: none;
}

.error {
    text-align: center;
    font-size: 12px;
    color: red;
    font-weight: 500;
    margin-bottom: 8px;
}

#login-form button {
    padding: 10px 0;
    background-color: #ff5c8a;
    border-radius: 8px;
    border: none;
    font-size: 14px;
    color: white;
    cursor: pointer;
}

#login-form button:hover {
    background-color: #f05581;
}

#login-plus a {
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 10px 0;
    background-color: #FEE502;
    font-size: 14px;
    color: #222;
    border-radius: 8px;
}

#login-plus a i {
    margin-right: 6px;
    font-size: 16px;
}

#login-plus a:hover {
    background-color: #f5dc00;
}

#login-etc {
    text-align: center;
    font-size: 12px;
    font-weight: 300;
    color: #a5a5a5;
    margin-top: 40px;
}

#login-etc:hover {
    text-decoration: underline;
}
</style>


