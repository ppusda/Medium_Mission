<script>
	import { page } from "$app/stores";
	import {onMount} from "svelte";
	import {toastWarning} from "../../../app.js";

	let postId =  $state({});
	let postData = $state({});
	let answerCount = $state({});

	let loginCheck = $state({});
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
		const response = await fetch(`/md/member/check`);
		if (response) {
			const data = await response.json();
			loginCheck = data.result;
			loginUsername = data.username;
		}
	}

	async function getPost() {
		const response = await fetch(`/md/post/${postId}`);
		postData = await response.json();

		console.log(postData);
		if (postData.createDate) {
			postData.createDate = formatDate(postData.createDate);
		}

		if (postData.modifiedDate) {
			postData.modifiedDate = formatDate(postData.modifiedDate);
		}
	}

	async function moveToModifyQuestionPage() {
		await memberCheck();
		if (loginCheck) {
			window.location.href = `/question/modify/${postId}`;
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function removeQuestion() {
		await memberCheck();
		if (loginCheck) {
			await fetch(`/md/question/remove/${postId}`, {
				method: 'POST',
			});
			window.location.href = `/question`;
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function voteQuestion() {
		await memberCheck();
		if (loginCheck) {
			await fetch(`/md/question/vote/${postId}`, {
				method: 'POST',
			});
			await getPost();
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function removeAnswer(answerId) {
		await memberCheck();
		if (loginCheck) {
			await fetch(`/md/answer/remove/${answerId}`, {
				method: 'POST',
			});
			location
			await getPost();
			window.location.hash = `#`;
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function voteAnswer(answerId) {
		await memberCheck();
		if (loginCheck) {
			await fetch(`/md/answer/vote/${answerId}`, {
				method: 'POST',
			});
			await getPost();
			window.location.hash=`#answer_${answerId}`
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function modifyHandleSubmit(event, answerId) {
		event.preventDefault();
		await memberCheck();
		if (loginCheck) {
			const formData = new FormData(event.target);
			const response = await fetch(`/md/answer/modify/${answerId}`, {
				method: 'POST',
				body: formData,
			});
			console.log(response);

			if (!response.ok) {
				const errorData = await response.json();
				if (errorData.content) {
					toastWarning(errorData.content);
					return;
				}
			}
			await getPost();
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function writeHandleSubmit(event) {
		event.preventDefault();
		await memberCheck();
		if (loginCheck) {
			const formData = new FormData(event.target);
			const response = await fetch(`/md/answer/write/${postId}`, {
				method: 'POST',
				body: formData,
			});

			if (!response.ok) {
				const errorData = await response.json();
				if (errorData.content) {
					toastWarning(errorData.content);
					return;
				}
			}
			await getPost();
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	onMount(async () => {
		postId = $page.params['pid'];
		await memberCheck();
		await getPost();
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post" />
</svelte:head>

<section class="pl-10 pr-10">
	<div class="flex flex-col">
		<div class="flex flex-col content-center flex-wrap">
			<h2 class="text-3xl font-bold py-2 m-5">{postData.title}</h2>
			<div class="flex flex-col justify-start m-5">
				<div class="flex flex-row">
					<div class="badge badge-primary badge-outline mb-1.5">
						{#if postData.author}
							<a>{postData.author}</a>
						{/if}
					</div>
				</div>
				<div class="flex flex-row">
					<div class="badge badge-primary badge-outline text-start mr-1.5">
						작성일자: {postData.createDate}
					</div>
					<div class="badge badge-primary badge-outline text-start">
						수정일자: {postData.modifiedDate}
					</div>
				</div>
			</div>
			<div class="card bg-base-100 shadow-xl border m-5 w-7/12">
				<div class="card-body flex flex-col">
					<p>{postData.content}</p>
					<div class="flex flex-row justify-between mt-5">
						<div class="flex flex-row justify-start">
							{#if postData.author}
								{#if loginUsername === postData.author}
									<a class="btn btn-ghost border-white mr-3" on:click={moveToModifyQuestionPage}>수정</a>
									<a href="#remove_question_modal" class="btn btn-ghost border-white">삭제</a>
									<div class="modal" role="dialog" id="remove_question_modal">
										<div class="modal-box">
											<h3 class="font-bold text-lg">질문 삭제</h3>
											<span>정말로 삭제하시겠습니까?</span>
											<div class="modal-action">
												<a class="btn btn-error" on:click={removeQuestion}>삭제</a>
												<a href="#" class="btn btn-ghost">닫기</a>
											</div>
										</div>
									</div>
								{/if}
							{/if}
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>

<style>

</style>
