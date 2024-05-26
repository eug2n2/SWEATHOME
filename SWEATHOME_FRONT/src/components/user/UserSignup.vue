<template>
    <div id="signup-container">
        <div id="signup">
            <h1>SIGNUP</h1>
            <div id="signup-form">
                <div class="check">
                    <input type="text" placeholder="아이디를 입력하세요 (최대 15자)" v-model="user.userId" required @keyup.enter="signup">
                    <button @click="checkId">중복확인</button>
                </div>
                <div v-if="store.isCheckId !== undefined">
                    <p class="pass" v-if="store.isCheckId">사용 가능한 아이디입니다.</p>
                    <p class="error" v-else>사용 불가능한 아이디입니다.</p>
                </div>
    
                <input type="password" placeholder="패스워드를 입력하세요" v-model="user.userPw" required @keyup.enter="signup">
                <div class="error" v-if="user.userPw.length !== 0">{{ validatePassword }}</div>
                <input type="password" placeholder="패스워드를 한 번 더 입력하세요" v-model="passwordCheck" required @keyup.enter="signup">
                <div class="error" v-if="passwordCheck.length !== 0">{{ checkPasswordEqual }}</div>
    
                <div class="check">
                    <input type="text" placeholder="닉네임을 입력하세요 (최대 15자)" v-model="user.userName" required @keyup.enter="signup">
                    <button @click="checkName">중복확인</button>
                </div>
                <div v-if="store.isCheckName !== undefined">
                    <p class="pass" v-if="store.isCheckName">사용 가능한 닉네임입니다.</p>
                    <p class="error" v-else>사용 불가능한 닉네임입니다.</p>
                </div>
    
                <input type="email" placeholder="이메일을 입력하세요" v-model="user.userEmail" required @keyup.enter="signup">
                <div class="error" v-if="user.userEmail.length !== 0">{{ validateEmail }}</div>
    
                <div class="error" v-if="store.signupError !== ''">{{ store.signupError}}</div>
                <button id="signup-btn" @click="signup">회원가입</button>
            </div>
            <div id="signup-etc">
                <RouterLink :to="{name: 'login'}">이미 회원이신가요?</RouterLink>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { computed, ref } from 'vue';

const store = useUserStore();

const user = ref({
    userId: '',
    userPw: '',
    userName: '',
    userEmail: '',
})

const checkId = function() {
    store.checkId(user.value.userId)
}

const validatePassword = computed(() => {
    var regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
    if (!regExp.test(user.value.userPw)) {
        return '패스워드는 문자, 숫자, 특수문자 포함 형태의 8-15자리 이내여야 합니다.'
    }
})

const passwordCheck = ref('');
const checkPasswordEqual = computed(() => {
    if (passwordCheck.value !== user.value.userPw) {
        return '패스워드와 일치하지 않습니다.'
    }
})

const checkName = function() {
    store.checkName(user.value.userName)
}

const validateEmail = computed(() => {
    const emailRegExp = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    if (!emailRegExp.test(user.value.userEmail)) {
        return '잘못된 이메일 형식입니다.';
    }
});

const signup = function() {
    store.signup(user.value)
}
</script>

<style scoped>
#signup-container {
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(45deg, #d04b71, #33001b);
    display: flex;
    justify-content: center;
    align-items: center;
}

#signup {
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

#signup-form {
    display: flex;
    flex-direction: column;
}

#signup-form input {
    font-size: 14px;
    padding: 8px 12px;
    border: none;
    border-bottom: 1px solid #878787;
    background-color: transparent;
    margin-bottom: 24px;
}

#signup-form input:focus {
    outline: none;
}

.check {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.check input {
    width: 260px;
}

.check button {
    margin-bottom: 24px;
    padding: 10px;
    border-radius: 4px;
    border: none;
    background-color: #ff5c8a;
    color: white;
    font-size: 12px;
    cursor: pointer;
}

.check button:hover {
    background-color: #f05581;
}

.pass {
    padding-left: 12px;
    font-size: 12px;
    color: dodgerblue;
    font-weight: 500;
    margin-bottom: 8px;
    margin-top: -10px;
}

.error {
    padding-left: 12px;
    font-size: 12px;
    color: red;
    font-weight: 500;
    margin-bottom: 8px;
    margin-top: -10px;
}

.error:last-of-type {
    padding-left: 12px;
    font-size: 12px;
    color: red;
    font-weight: 500;
    margin-bottom: 8px;
    margin-top: 8px;
}

#signup-btn {
    padding: 10px 0;
    background-color: #ff5c8a;
    border-radius: 8px;
    border: none;
    font-size: 14px;
    color: white;
    cursor: pointer;
}

#signup-btn:hover {
    background-color: #f05581;
}

#signup-etc {
    text-align: center;
    font-size: 12px;
    font-weight: 300;
    color: #a5a5a5;
    margin-top: 40px;
}

#signup-etc:hover {
    text-decoration: underline;
}
</style>