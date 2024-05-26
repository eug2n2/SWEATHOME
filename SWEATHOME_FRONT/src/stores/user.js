import { ref, computed, onMounted } from 'vue'
import { defineStore } from 'pinia'
import router from '@/router'
import axios from 'axios'

const REST_USER_API = `http://localhost:8080/api-user`

export const useUserStore = defineStore('user', () => {
    // 회원가입 전 확인 절차
    const signupError = ref('')
    const isValidUser = function(user) {
        if (!user.userId || !user.userPw || !user.userName || !user.userEmail) {
            return false;
        }
        const regExp = /^.*(?=^.{8,15}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/;
        if (!regExp.test(user.userPw)) {
            return false;
        }
        const emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
        if (!emailRegExp.test(user.userEmail)) {
            return false;
        }
        return true;
    }

    // 회원가입
    const signup = function(user) {
        if (!isValidUser(user)) {
            signupError.value = '잘못된 정보 입력으로 회원가입에 실패하였습니다. 다시 시도해주세요.';
            return
        }

        axios({
            url: `${REST_USER_API}/signup`,
            method: 'POST',
            data: user,
        }).then(() => {
            router.push({name: 'login'})
        })
    }
    
    // 로그인
    const loginError = ref('')
    const loginUserId = ref(null)
    const login = function(userId, userPw) {
        axios.post(`${REST_USER_API}/login`, {
            userId: userId,
            userPw: userPw
        }).then((response) => {
            sessionStorage.setItem('access-token', response.data["access-token"])
            const token = response.data['access-token'].split('.')
            let userId = JSON.parse(atob(token[1]))['id']
            loginUserId.value = userId;
            router.push({name: 'videoList'})
        }).catch(() => {
            loginError.value = '아이디 혹은 패스워드를 잘못 입력하셨습니다. 다시 시도해주세요.';
            return
        })
    }

    // 로그인 유지
    onMounted(() => {
        if (sessionStorage.getItem('access-token')) {
            const token = sessionStorage.getItem('access-token').split('.')
            loginUserId.value = JSON.parse(atob(token[1]))['id']
        }
    })

    // 로그아웃
    const logout = function() {
        loginUserId.value = null;
        sessionStorage.removeItem('access-token')
        router.replace({name: 'home'})
    }

    // 카카오 로그아웃

    const kakaoLogout = () => {
        window.Kakao.Auth.logout((res) => {
          console.log(res);
        });
      };

      
    // 아이디 중복체크
    const isCheckId = ref();
    const checkId = function(userId) {
        axios.get(`${REST_USER_API}/checkId`, {
            params: {userId: userId},
            headers: {'access-token': sessionStorage.getItem('access-token')}
        }).then((response) => {
            isCheckId.value = response.data;
        })
    }

    // 닉네임 중복체크
    const isCheckName = ref();
    const checkName = function(userName) {
        axios.get(`${REST_USER_API}/checkName`, {
            params: {userName: userName},
            headers: {'access-token': sessionStorage.getItem('access-token')}
        }).then((response) => {
            isCheckName.value = response.data;
        })
    }

    // 비밀번호 찾기
    // 아이디 & 이메일 정보 확인
    const findError = ref()
    const code = ref()
    const checkIdAndEmail = function(userId, userEmail) {
        axios.get(`${REST_USER_API}/selectnum`, {
            params: {
                userId: userId,
                userEmail: userEmail
            },
            headers: {'access-token': sessionStorage.getItem('access-token')}
        }).then((response) => {
            findError.value = ''
            code.value = response.data
        }).catch((error => {
            findError.value = '사용자 정보를 잘못 입력하셨습니다. 다시 시도해주세요.';
        }))
    }

    // 아이디 & 인증번호 일치하면 패스워드 반환
    const userPassword = ref()
    const returnPassword = function(userId) {
        axios.get(`${REST_USER_API}/findpw`, {
            params: { userId: userId },
            headers: {'access-token': sessionStorage.getItem('access-token')}
        }).then((response) => {
            userPassword.value = response.data
        })
    }

    // 사용자별 좋아요 목록
    const likeList = ref([])
    const getLikeList = function(userId) {
        axios.get(`${REST_USER_API}/likeList`, {
            params: {userId: userId},
            headers: {'access-token': sessionStorage.getItem('access-token')}
        }).then((response) => {
            likeList.value = response.data
        })
    }

    return { signup, signupError, loginUserId, login, loginError, logout, isCheckId, checkId, isCheckName, checkName, checkIdAndEmail, code, findError, returnPassword, userPassword, likeList, getLikeList }
})