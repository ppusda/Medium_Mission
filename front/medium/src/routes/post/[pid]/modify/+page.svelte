<script>
	import {toastWarning} from "../../../../app.js";
	import {onMount} from "svelte";
	import {page} from "$app/stores";
	import {goto} from "$app/navigation";

	let postId = $state({});
	let postData = $state({});
	let isPaidPost = $state({});

	let isLogin = $state({});
	let isPaidUser = $state({});
	let loginUsername = $state({});

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

	async function checkPermission() {
		if (!isLogin && loginUsername !== postData.author) {
			toastWarning("로그인이 필요합니다.");
			await goto(`/post/${postId}`);
		}
	}

	async function getPost() {
		const response = await fetch(`http://localhost:8080/post/${postId}`, {
			credentials: 'include',
		});
		postData = await response.json();
		isPaidPost = !!postData.isPaid;
	}

	function goBack() {
		window.history.back();
	}

	async function handleSubmit(event) {
		event.preventDefault();
		const formData = new FormData(event.target);

		const jsonData = {};
		for (let pair of formData.entries()) {
			jsonData[pair[0]] = pair[1];
		}
		jsonData['isPaid'] = jsonData['isPaid'] === 'on' || isPaidPost;

		if (formData) {
			const response = await fetch(`http://localhost:8080/post/${postId}`, {
				method: 'PUT',
				credentials: 'include',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(jsonData),
			});

			if (!response.ok) {
				const errorData = await response.json();
				if (errorData.title) {
					toastWarning(errorData.title);
					return;
				}

				if (errorData.content) {
					toastWarning(errorData.content);
					return;
				}

				toastWarning(errorData.message);
				return;
			}

			await goto(`/post/${postId}`);
		}
	}

	onMount(async () => {
		postId = $page.params['pid'];
		isLogin = false;
		isPaidUser = false;

		await memberCheck();
		await getPost();

		await checkPermission();
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post Modify" />
</svelte:head>

<section class="pl-10 pr-10">
	<div>
		<div class="flex flex-row justify-between">
			<h2 class="text-3xl font-bold py-2 m-5">
				<a class="btn btn-ghost" on:click={goBack}> <i class="fa-solid fa-arrow-left"></i> </a>
				글 수정
			</h2>
			{#if isPaidUser}
				<div class="py-2 m-5">
					<label class="cursor-pointer label">
						<span class="label-text mr-3">유료 글로 전환</span>
						<input type="checkbox" class="toggle toggle-primary" name="isPaidPost" bind:checked={isPaidPost} />
					</label>
				</div>
			{/if}
		</div>
		<form on:submit={handleSubmit} method="post">
			<div class="flex flex-col m-5">
				<label for="title" class="form-label">제목</label>
				<input class="input input-bordered input-primary mt-3 max-w-full" name="title" id="title" type="text" placeholder="제목을 입력해주세요." value="{postData.title}"/>
			</div>
			<div class="flex flex-col m-5">
				<label for="content" class="form-label">내용</label>
				<textarea class="textarea textarea-primary mt-3 resize-none" name="content" id="content" rows="8" value="{postData.content}" placeholder="내용을 입력해주세요."></textarea>
			</div>
			<div class="flex flex-col m-5">
				<button type="submit" class="btn btn-primary mt-3">작성</button>
			</div>
		</form>
	</div>
</section>

<style>

</style>
