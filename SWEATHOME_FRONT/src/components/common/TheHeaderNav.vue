<template>
    <header id="header-1" v-if="!store.loginUserId">
        <div class="header-container">
            <div id="logo">
                <RouterLink to="/">SWEATHOME</RouterLink>
            </div>
            <nav id="video-none">
               <a href="">영상 목록</a>
               <a href="">영상 요청</a>
               <a href="">운동 기록</a>
            </nav>
            <nav id="user-off">
                <RouterLink :to="{name: 'login'}">로그인</RouterLink>
                <RouterLink :to="{name: 'signup'}">회원가입</RouterLink>
            </nav>
        </div>
    </header>

    <header id="header-2" v-if="store.loginUserId">
        <div class="header-container">
            <div id="logo">SWEATHOME</div>
            <nav id="video" v-if="store.loginUserId">
                <RouterLink :to="{name: 'videoList'}">영상 목록</RouterLink>
                <RouterLink :to="{name: 'askList'}">영상 요청</RouterLink>
                <RouterLink :to="{name: 'check'}">운동 기록</RouterLink>
            </nav>
            <nav id="user-on">
                <RouterLink :to="{name: 'mypage'}">마이페이지</RouterLink>
                <a href="#" @click="logout">로그아웃</a>
            </nav>
        </div>
    </header>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
import { onMounted } from 'vue';

const store = useUserStore();

const logout = function() {
    store.logout();
}
</script>

<style scoped>
#header-1 {
    position: absolute;
    z-index: 2;
    width: 100%;
}

#header-2 {
    background-color: #363636;
    margin-bottom: 60px;
}

#header-2 .router-link-exact-active {
    color: #ff5c8a;
    font-weight: 600;
}

.header-container {
    display: flex;
    justify-content: space-around;
    align-items: center;
    padding: 16px 0;
}

#video-none {
    visibility: hidden;
}

#logo {
    color: white;
    font-size: 24px;
    font-weight: 800;
}

a {
    color: white;
    margin: 0 8px;
}

nav a:hover {
    color: #ff5c8a;
    font-weight: 600;
}
</style>