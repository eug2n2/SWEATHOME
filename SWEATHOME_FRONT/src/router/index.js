import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import VideoView from '@/views/VideoView.vue'
import MyPageView from '@/views/MyPageView.vue'
import CheckView from '@/views/CheckView.vue'
import AskView from '@/views/AskView.vue'
import ErrorView from '@/views/ErrorView.vue'

import VideoList from '@/components/video/videoList/VideoList.vue'
import VideoDetail from '@/components/video/videoDetail/VideoDetail.vue'
import VideoCreate from '@/components/video/videoList/VideoCreate.vue'
import VideoUpdate from '@/components/video/videoList/VideoUpdate.vue'
import UserLogin from '@/components/user/UserLogin.vue'
import UserSignup from '@/components/user/UserSignup.vue'
import UserFindPassword from '@/components/user/UserFindPassword.vue'
import UserGotcha from '@/components/mypage/UserGotcha.vue'
import AskVideoList from '@/components/ask/AskVideoList.vue'
import AskUserBoardList from '@/components/ask/AskUserBoardList.vue'
import AskAdminBoardList from '@/components/ask/AskAdminBoardList.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/login',
      name: 'login',
      component: UserLogin
    },
    {
      path: '/signup',
      name: 'signup',
      component: UserSignup
    },
    {
      path: '/find',
      name: 'find',
      component: UserFindPassword
    },
    {
      path: '/video',
      name: 'video',
      component: VideoView,
      children: [
        {
          path: '',
          name: 'videoList',
          component: VideoList
        },
        {
          path: ':videoId',
          name: 'videoDetail',
          component: VideoDetail
        },
        {
          path: 'create',
          name: 'videoCreate',
          component: VideoCreate
        },
        {
          path: 'update',
          name: 'videoUpdate',
          component: VideoUpdate
        },
      ]
    },
    {
      path: '/check',
      name: 'check',
      component: CheckView
    },
    {
      path: '/ask',
      name: 'ask',
      component: AskView,
      children: [
        {
          path: '',
          name: 'askList',
          component: AskVideoList
        },
        {
          path: 'user',
          name: 'userList',
          component: AskUserBoardList
        },
        {
          path: 'admin',
          name: 'adminList',
          component: AskAdminBoardList
        },
      ]
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: MyPageView
    },
    {
      path: '/gotcha',
      name: 'gotcha',
      component: UserGotcha
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'error',
      component: ErrorView
    }
  ]
})

export default router
