<template>
    <div id="error">
        <div class="title">
            <i class="ph-duotone ph-warning"></i>
            <h1>페이지를 찾을 수 없습니다.</h1>
        </div>
        <p>
            운동에 집중해야지, 웹사이트에서 이상한 짓을 하면 어떡하나요!<br>
            어서 페이지로 돌아가서 운동에 전념하세요!
        </p>

        <img src="/src/assets/img/error.gif" alt="이놈쒸끼">

        <p class="fighting">건강한 몸을 위해 오늘도 파이팅 ( •̀ω•́ )و✧</p>

        <RouterLink :to="{name: 'videoList'}">영상 목록으로 이동하기</RouterLink>
        <RouterLink :to="{name: 'check'}">운동 기록으로 이동하기</RouterLink>
    </div>
</template>

<script setup>
</script>

<style scoped>
#error {
    width: 70%;
    margin: 0 auto 100px;
    text-align: center;
}

.title {
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 36px;
    margin-bottom: 64px;
}

.title i {
    margin-right: 8px;
    color: #ff5c8a;
}

#error img {
    width: 320px;
}

.fighting {
    margin-bottom: 120px;
}

a {
    background-color: #ff5c8a;
    border-radius: 20px;
    padding: 16px 40px;
    margin: 0 8px;
    color: white;
}

a:hover {
    background-color: #f05581;
}
</style>