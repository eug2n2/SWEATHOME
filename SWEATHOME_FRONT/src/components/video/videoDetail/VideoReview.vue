<template>
    <div id="review-container">
        <div id="reviews">
            <div id="review-count">댓글 {{ currentReviewList.length }}개</div>
            <div id="review-create">
                <div class="star">
                    <input type="radio" name="rate" id="rate1" value="5" v-model="review.rate">
                    <label for="rate1">
                        <i class="ph-fill ph-star"></i>
                    </label>
                    <input type="radio" name="rate" id="rate2" value="4" v-model="review.rate">
                    <label for="rate2">
                        <i class="ph-fill ph-star"></i>
                    </label>
                    <input type="radio" name="rate" id="rate3" value="3" v-model="review.rate">
                    <label for="rate3">
                        <i class="ph-fill ph-star"></i>
                    </label>
                    <input type="radio" name="rate" id="rate4" value="2" v-model="review.rate">
                    <label for="rate4">
                        <i class="ph-fill ph-star"></i>
                    </label>
                    <input type="radio" name="rate" id="rate5" value="1" v-model="review.rate">
                    <label for="rate5">
                        <i class="ph-fill ph-star"></i>
                    </label>
                    <span>별점 &nbsp;</span>
                </div>
                <textarea name="" id="review" placeholder="댓글을 입력해주세요." v-model="review.content"></textarea>
                <button @click="createReview" @keyup.enter="createReview">등록</button>
            </div>
            <ul>
                <li v-for="review in currentReviewList" :key="review.reviewId" :data-review-id="review.reviewId">
                    <div class="review-info">
                        <div class="review-user">
                            <div class="review-writer">{{ review.userName }}</div>
                            <div class="review-rate">
                                <i class="ph-fill ph-star"></i>
                                <span>{{ review.rate }}</span>
                            </div>
                            <div class="review-buttons" v-if="review.userId === decodedToken.id">
                                <button @click="deleteReview($event)">삭제</button>
                            </div>
                        </div>
                        <span class="review-date">{{ review.writeTime }}</span>
                    </div>
                    <div class="review-content">{{ review.content }}</div>
                </li>
            </ul>
        </div>
    </div>
</template>

<script setup>
import { useReviewStore } from '@/stores/review';
import { onMounted, ref, defineProps, watch, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const store = useReviewStore();
const route = useRoute();
const router = useRouter();

const token = sessionStorage.getItem('access-token')
const decodedToken = JSON.parse(atob(token.split('.')[1]));

const props = defineProps({
    videoId: Number
})

console.log(decodedToken.id)

const review = ref({
    videoId: props.videoId,
    userId: decodedToken.id,
    content: '',
    rate: ''
})

const createReview = function() {
    store.createReview(review.value)
    review.value.content = '';
    review.value.rate = '';
    location.reload();
}

const deleteReview = function(event) {
    const reviewId = event.target.closest('li').getAttribute('data-review-id');
    store.deleteReview(reviewId);
    location.reload();
}

const currentReviewList = computed(() => {
    return store.reviewList;
})

watch(() => store.reviewList, (newReviewList, oldReviewList) => {
        store.getReviewList(props.videoId);
    }
);

onMounted(() => {
    store.getReviewList(props.videoId);
})
</script>

<style scoped>
#review-container {
    width: 70%;
    margin: 0 auto 120px;
}

/* #reviews {
    width: 880px;
} */

#review-count {
    font-size: 18px;
    font-weight: 500;
    margin-bottom: 24px;
}

#review-create {
    margin-bottom: 24px;
}

/* 별점 */
#review-create .star {
    display: flex;
    justify-content: flex-end;
    flex-direction: row-reverse;
}

#review-create input[type=radio] {
    display: none;
}

#review-create i {
    font-size: 20px;
    color: transparent;
    text-shadow: 0 0 0 #f0f0f0;
    cursor: pointer;
}

#review-create input[type="radio"]:checked ~ label i{
    text-shadow: 0 0 0 rgba(255, 92, 138, 0.9);
}

#review-create span {
    margin-bottom: 8px;
    font-weight: 500;
}

#review {
    width: 100%;
    border-radius: 20px;
    padding: 20px;
    resize: none;
    box-sizing: border-box;
}

#review-create button {
    background-color: #ff5c8a;
    padding: 8px 20px;
    border-radius: 12px;
    font-size: 14px;
    cursor: pointer;
    border: none;
    color: white;
    margin-left: calc(100% - 65.89px);
    margin-top: 8px;
}

#review-create button:hover {
    background-color: #f05581;
}

/* 리뷰 목록 */
#review-container li {
    padding: 24px 0;
    border-bottom: 1px solid #bbb;
}

.review-info {
    display: flex;
    justify-content: space-between;
}

.review-user {
    display: flex;
    align-items: center;
    margin-bottom: 12px;
}
.review-user div {
    margin-right: 8px;
}

.review-user i {
    margin-right: 2px;
}

.review-user button {
    font-size: 12px;
    padding: 2px 8px;
    margin-left: 12px;
}

.review-writer {
    font-weight: 600;
}

.review-date {
    font-size: 14px;
    font-weight: 300;
}
</style>