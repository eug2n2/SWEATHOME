<template>
    <footer>
        <ul>
            <li>이용약관</li>
            <li>개인정보처리방침</li>
            <li>운영정책</li>
            <li>브랜드보호정책</li>
            <li>공지사항</li>
            <li>Contact Us</li>
        </ul>
        <div>
             &copy; TEAM 오윤완 - 서울 5반 김윤 & 오유진 All rights reserved.
        </div>
    </footer>
</template>

<script setup>
import { useUserStore } from '@/stores/user';
const store = useUserStore();
</script>

<style scoped>
footer {
    background-color: #363636;
    margin-top: 60px;
    padding: 40px;
    font-weight: 200;
}

footer ul {
    display: flex;
    margin-bottom: 16px;
}

footer li {
    margin-right: 16px;
    color: white;
}

footer div {
    color: white;
}
</style>