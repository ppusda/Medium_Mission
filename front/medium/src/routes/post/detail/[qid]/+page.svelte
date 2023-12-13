<script>
	import { page } from "$app/stores";
	import {onMount} from "svelte";
	import {toastWarning} from "../../../../app.js";

	let questionId =  $state({});
	let questionData = $state({});
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

	async function getQuestion() {
		const response = await fetch(`/md/question/detail/${questionId}`);
		questionData = await response.json();

		answerCount = questionData.answerList.length;

		if (questionData.createDate) {
			questionData.createDate = formatDate(questionData.createDate);
		}

		if (questionData.modifyDate) {
			questionData.modifyDate = formatDate(questionData.modifyDate);
		}

		if (answerCount >= 1) {
			questionData.answerList.forEach(async (answer) => {
				answer.createDate = formatDate(answer.createDate);
				answer.modifyDate = formatDate(answer.modifyDate);
			});
		}
	}

	async function moveToModifyQuestionPage() {
		await memberCheck();
		if (loginCheck) {
			window.location.href = `/question/modify/${questionId}`;
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function removeQuestion() {
		await memberCheck();
		if (loginCheck) {
			await fetch(`/md/question/remove/${questionId}`, {
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
			await fetch(`/md/question/vote/${questionId}`, {
				method: 'POST',
			});
			await getQuestion();
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
			await getQuestion();
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
			await getQuestion();
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
			await getQuestion();
			window.location.hash=`#answer_${answerId}`
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function writeHandleSubmit(event) {
		event.preventDefault();
		await memberCheck();
		if (loginCheck) {
			const formData = new FormData(event.target);
			const response = await fetch(`/md/answer/write/${questionId}`, {
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
			await getQuestion();
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	onMount(async () => {
		questionId = $page.params['qid'];
		await memberCheck();
		await getQuestion();
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post" />
</svelte:head>

<section class="pl-10 pr-10">
	<div class="flex flex-col">
		<div class="flex flex-col content-start flex-wrap">
			<h2 class="text-3xl font-bold border-bottom py-2 m-5"> {questionData.subject} </h2>
			<div class="card bg-base-100 shadow-xl border m-5 w-7/12">
				<div class="card-body flex flex-col">
					<h2 class="card-title">{questionData.subject} </h2>
					<p>{questionData.content}</p>
					<div class="flex flex-row justify-between mt-5">
						<div class="flex flex-row justify-start">
							{#if questionData.voter}
								<a class="btn btn-ghost border-white mr-3" on:click={voteQuestion}>추천
									<span class="badge badge-primary badge-outline">{questionData.voter.length}</span>
								</a>
							{/if}
							{#if questionData.author}
								{#if loginUsername === questionData.author.username}
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
						<div class="flex flex-col">
							<div class="flex flex-row justify-end">
								<div class="badge badge-primary badge-outline mb-1.5">
									{#if questionData.author}
										{questionData.author.username}
									{/if}
								</div>
							</div>
							<div class="flex flex-row justify-end">
								<div class="badge badge-primary badge-outline text-start mr-1.5">
									작성일자: {questionData.createDate}
								</div>
								<div class="badge badge-primary badge-outline text-start">
									수정일자: {questionData.modifyDate}
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="flex flex-col content-end flex-wrap">
			<h5 class="text-1xl m-5">{answerCount} 개의 답변이 있습니다.</h5>
			{#if questionData && questionData.answerList}
				{#each questionData.answerList as answer}
					<div class="card bg-base-100 shadow-xl border m-5 w-7/12" id="answer_{answer.id}">
						<div class="card-body">
							<p>{answer.content}</p>
							<div class="flex flex-row justify-between mt-5">
								<div class="flex flex-row justify-start">
									{#if answer.voter}
										<a class="btn btn-ghost border-white mr-3" on:click|preventDefault={() => voteAnswer(answer.id)}>추천
											<span class="badge badge-primary badge-outline">{answer.voter.length}</span>
										</a>
									{/if}
									{#if answer.author}
										{#if loginUsername === answer.author.username}
											<a href="#edit_modal" class="btn btn-ghost border-white mr-3">수정</a>
											<div class="modal" role="dialog" id="edit_modal">
												<div class="modal-box">
													<h3 class="font-bold text-lg">답변 수정</h3>
													<form class="w-full" on:submit|preventDefault={(event) => modifyHandleSubmit(event, answer.id)} method="post">
														<div class="flex flex-row items-stretch">
															<textarea class="textarea textarea-primary m-5 w-full resize-none" name="content" id="content" value="{answer.content}" rows="3"></textarea>
															<button type="submit" class="btn btn-primary m-5 h-24">수정</button>
														</div>
													</form>
													<div class="modal-action">
														<a href="#" class="btn btn-ghost">닫기</a>
													</div>
												</div>
											</div>
											<a href="#remove_answer_modal" class="btn btn-ghost border-white">삭제</a>
											<div class="modal" role="dialog" id="remove_answer_modal">
												<div class="modal-box">
													<h3 class="font-bold text-lg">답변 삭제</h3>
													<span>정말로 삭제하시겠습니까?</span>
													<div class="modal-action">
														<a class="btn btn-error" on:click|preventDefault={() => removeAnswer(answer.id)}>삭제</a>
														<a href="#" class="btn btn-ghost">닫기</a>
													</div>
												</div>
											</div>
										{/if}
									{/if}
								</div>
								<div class="flex flex-col">
									<div class="flex flex-row justify-end">
										{#if answer.author}
											{#if questionData.author.username === answer.author.username}
												<div class="badge badge-info badge-outline mr-1.5 mb-1.5">
													질문 작성자
												</div>
											{/if}
											<div class="badge badge-primary badge-outline mb-1.5">
												{answer.author.username}
											</div>
										{/if}
									</div>
									<div class="flex flex-row justify-end">
										<div class="badge badge-primary badge-outline text-start mr-1.5">
											작성일자: {answer.createDate}
										</div>
										<div class="badge badge-primary badge-outline text-start">
											수정일자: {answer.modifyDate}
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				{/each}
			{/if}
		</div>

		<div class="flex content-center flex-wrap w-full">
			<form class="w-full" on:submit={writeHandleSubmit} method="post">
				<div class="flex flex-row m-5 items-stretch">
					<textarea class="textarea textarea-primary m-5 w-full resize-none" name="content" id="content" rows="3"></textarea>
					<button type="submit" class="btn btn-primary m-5 h-24">답변 등록</button>
				</div>
			</form>
		</div>
	</div>
</section>

<style>

</style>
