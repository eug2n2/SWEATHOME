<template>
    <div id="find-password-container">
        <div id="find-password">
            <h1>FIND PASSWORD</h1>
            <p class="info">아이디와 이메일을 입력하면 해당 이메일로 인증번호를 발급해 드립니다.<br/>인증번호가 일치하면 패스워드를 알려드립니다.</p>

            <div id="find-password-form">
                <input type="text" placeholder="아이디를 입력하세요" v-model="userId">
                <input type="email" placeholder="이메일을 입력하세요" v-model="userEmail">
                <button @click="checkIdAndEmail">인증번호 발급</button>
            </div>
            
            <div id="password">
                <div class="error" v-if="store.findError">{{ store.findError }}</div>
                <div class="password-input" v-if="store.findError === ''">
                    <input type="text" placeholder="인증번호" v-model="code">
                    <button @click="returnPassword">인증번호 확인</button>
                </div>
                <div class="password-output">
                    <p class="error" v-if="passwordError">{{ passwordError }}</p>
                    <p class="pass" v-if="userPassword">
                        회원님의 패스워드는 <b>{{ userPassword }}</b> 입니다.
                    </p>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { ref, watch } from 'vue';

const store = useUserStore();
const userId = ref('');
const userEmail = ref('');
const code = ref('');

const checkIdAndEmail = function() {
    store.checkIdAndEmail(userId.value, userEmail.value);
}

const userPassword = ref('')
const passwordError = ref('')
const returnPassword = function() {
    if (code.value.toString() === store.code.toString()) {
        store.returnPassword(userId.value);
        passwordError.value = '';
    } else {
        passwordError.value = '인증번호를 잘못 입력하셨습니다. 다시 시도해주세요.';
        userPassword.value = '';
    }
}

watch(() => store.userPassword, (newValue) => {
    userPassword.value = newValue;
});
</script>

<style scoped>
#find-password-container {
    width: 100vw;
    height: 100vh;
    background-image: linear-gradient(45deg, #d04b71, #33001b);
    display: flex;
    justify-content: center;
    align-items: center;
}

#find-password {
    width: 360px;
    padding: 48px;
    border-radius: 20px;
    background-color: white;
    box-shadow: 0 4px 16px rgba(0, 0, 0, 0.3);
}

h1 {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

.info {
    text-align: center;
    font-size: 12px;
    margin-bottom: 40px;
}

#find-password-form {
    display: flex;
    flex-direction: column;
}

#find-password-form input {
    font-size: 14px;
    padding: 8px 12px;
    border: none;
    border-bottom: 1px solid #878787;
    background-color: transparent;
    margin-bottom: 24px;
}

#find-password-form input:focus {
    outline: none;
}

#find-password-form button {
    padding: 10px 0;
    background-color: #ff5c8a;
    border-radius: 8px;
    border: none;
    font-size: 14px;
    color: white;
    cursor: pointer;
    margin-bottom: 20px;
}

#find-password-form button:hover {
    background-color: #f05581;
}

.password-input {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.password-input input {
    width: 240px;
    font-size: 14px;
    padding: 8px 12px;
    border: 1px solid #878787;
    background-color: transparent;
    margin-bottom: 8px;
    border-radius: 4px;
}

.password-input input:focus {
    outline: none;
}

.password-input button {
    margin-bottom: 8px;
    padding: 10px;
    border-radius: 4px;
    border: none;
    background-color: #ff5c8a;
    color: white;
    font-size: 12px;
    cursor: pointer;
}
.error {
    text-align: center;
    font-size: 12px;
    color: red;
    font-weight: 500;
    margin-top: 8px;
}

.pass {
    text-align: center;
    font-size: 14px;
    font-weight: 500;
    margin-top: 8px;
}

.pass b {
    text-decoration: underline
}
</style>