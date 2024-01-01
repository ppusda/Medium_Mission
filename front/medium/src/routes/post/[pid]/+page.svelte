<script>
	import { page } from "$app/stores";
	import {onMount} from "svelte";
	import {toastWarning} from "../../../app.js";
	import {goto} from "$app/navigation";

	let postId =  $state({});
	let postData = $state({});
	let isPaidPost = $state({});

	let recommendCheck = $state({});

	let isLogin = $state({});
	let isPaidUser = $state({});
	let loginUsername = $state({});

	function formatDate(datePhrase) {
		const date = new Date(datePhrase);
		return date.toLocaleDateString('ko-KR', {
			year: 'numeric',
			month: '2-digit',
			day: '2-digit',
			hour: '2-digit',
			minute: '2-digit',
		});
	}

	async function memberCheck() {
		const response = await fetch(`http://localhost:8080/member/check`, {
			credentials: 'include',
		});
		if (response.ok) {
			const data = await response.json();

			if (data.nickname) {
				loginUsername = data.nickname;
			}

			if (data.isPaid) {
				isPaidUser = !!data.isPaid;
			}

			isLogin = data.result;
		}
	}

	async function checkRecommend() {
		const response = await fetch(`http://localhost:8080/post/${postId}/recommend`, {
			credentials: 'include',
		});
		const responseData = await response.json();

		if (response.ok) {
			recommendCheck = responseData.isRecommended;
			return;
		}
	}

	async function getPost() {
		const response = await fetch(`http://localhost:8080/post/${postId}`, {
			credentials: 'include',
		});
		postData = await response.json();

		if (postData.isPaid) {
			isPaidPost = postData.isPaid;
		}

		if (postData.createDate) {
			postData.createDate = formatDate(postData.createDate);
		}

		if (postData.modifiedDate) {
			postData.modifiedDate = formatDate(postData.modifiedDate);
		}
	}

	async function moveToModifyPostPage() {
		await memberCheck();
		if (isLogin) {
			await goto(`/post/${postId}/modify`);
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function removePost() {
		await memberCheck();
		if (isLogin) {
			const response = await fetch(`http://localhost:8080/post/${postId}`, {
				method: 'DELETE',
				credentials: 'include',
			});

			if (!response.ok) {
				const errorData = await response.json();

				toastWarning(errorData.message);
				return;
			}

			await goto(`/post`);
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function recommendPost() {
		await memberCheck();
		if (isLogin) {
			await fetch(`http://localhost:8080/post/${postId}/recommend`, {
				method: 'POST',
				credentials: 'include',
			});

			await checkRecommend();
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function checkMembership() {
		if (isPaidPost) {
			if (!isPaidUser) {
				toastWarning("유료 회원만 글을 볼 수 있습니다.");
				window.history.back();
			}
		}
	}

	onMount(async () => {
		postId = $page.params['pid'];
		recommendCheck = false;
		isPaidPost = false;
		isPaidUser = false;

		await memberCheck();
		await getPost();

		if (isLogin) {
			await checkRecommend();
		}

		await checkMembership();
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post" />
</svelte:head>

<section class="pl-10 pr-10">
	<div class="flex flex-col">
		<div class="flex flex-col content-center flex-wrap">
			<h2 class="text-3xl font-bold m-5">
				<a class="btn btn-ghost" href="/post"> <i class="fa-solid fa-arrow-left"></i> </a>
				{postData.title}
			</h2>
			<div class="flex flex-row justify-between">
				<div class="flex flex-col justify-start m-5">
					<div class="flex flex-row">
						<div class="badge badge-primary badge-outline mb-1.5">
							{#if postData.author}
								<a href="/member/{postData.author}">{postData.author}</a>
							{/if}
						</div>
					</div>
					<div class="flex flex-row">
						<div class="badge badge-primary badge-outline text-start mr-1.5">
							작성일자: {postData.createDate}
						</div>
						<div class="badge badge-primary badge-outline text-start mr-1.5">
							수정일자: {postData.modifiedDate}
						</div>
					</div>
				</div>
				<div class="flex flex-row justify-end m-5">
					<div class="flex flex-row items-center mr-5">
						<p><i class="fa-solid fa-eye"></i> {postData.viewCount}</p>
						{#if recommendCheck}
							<a class="btn btn-ghost" on:click={recommendPost}><i class="fa-solid fa-thumbs-up fa-xl"></i></a>
						{:else}
							<a class="btn btn-ghost" on:click={recommendPost}><i class="fa-regular fa-thumbs-up fa-xl"></i></a>
						{/if}
						<p>{postData.recommendCount}</p>
					</div>
					{#if postData.author}
						{#if loginUsername === postData.author}
							<a class="btn btn-ghost border-white mr-3" on:click={moveToModifyPostPage}>수정</a>
							<a href="#remove_post_modal" class="btn btn-ghost border-white">삭제</a>
							<div class="modal" role="dialog" id="remove_post_modal">
								<div class="modal-box">
									<h3 class="font-bold text-lg">글 삭제</h3>
									<span>정말로 삭제하시겠습니까?</span>
									<div class="modal-action">
										<a class="btn btn-error" on:click={removePost}>삭제</a>
										<a href="#" class="btn btn-ghost">닫기</a>
									</div>
								</div>
							</div>
						{/if}
					{/if}
				</div>
			</div>
			<div class="card bg-base-100 shadow-xl border m-5 w-7/12">
				<div class="card-body flex flex-col">
					<p style="white-space: pre-wrap;">{postData.content}</p>
				</div>
			</div>
		</div>
	</div>
</section>

<style>

</style>
