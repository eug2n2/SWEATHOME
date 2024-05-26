<template>
  <nav>
    <RouterLink :to="{name: 'askList'}">Youtube 검색</RouterLink>
    <span>|</span>
    <RouterLink v-if="userId === 'oyj5775'" :to="{name: 'adminList'}">요청 목록</RouterLink>
    <RouterLink v-else :to="{name: 'userList'}">요청 목록</RouterLink> 
  </nav>
  <RouterView/>
</template>

<script setup>
const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));
const userId = decodedToken.id;
</script>

<style scoped>
nav {
  width: 70%;
  margin: 0 auto 32px;
}

nav span {
  margin: 0 8px;
}

nav a:hover {
  font-weight: 500;
  text-decoration: underline;
}

.router-link-exact-active {
  font-weight: 500;
  text-decoration: underline;
}
</style>