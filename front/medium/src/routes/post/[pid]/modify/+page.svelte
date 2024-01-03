<script>
	import {toastWarning} from "../../../../toastr.js";
	import {onMount} from "svelte";
	import {page} from "$app/stores";
	import {goto} from "$app/navigation";

	import {memberCheck} from "../../../../member.js";
	import {isLogin, isPaidUser, loginUsername} from "../../../../stores.js";

	import '@toast-ui/editor/dist/toastui-editor.css';
	import '@toast-ui/editor/dist/theme/toastui-editor-dark.css';

	let postId = $state({});
	let postData = $state({});
	let isPaidPost = $state({});

	let editor = $state({});

	async function checkPermission() {
		if (!$isLogin && $loginUsername !== postData.author) {
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
		jsonData['content'] = editor.getMarkdown();
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
		const { default: Editor } = await import('@toast-ui/editor');

		postId = $page.params['pid'];

		await memberCheck();
		await getPost();
		await checkPermission();

		if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
			editor = new Editor({
				el: document.querySelector('#editor'),
				previewStyle: 'vertical',
				height: '30rem',
				initialValue: postData.content,
				theme: 'dark',
			});
		} else {
			editor = new Editor({
				el: document.querySelector('#editor'),
				previewStyle: 'vertical',
				initialValue: postData.content,
				height: '30rem',
			});
		}
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
			{#if $isPaidUser}
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
				<div class="mt-3 border-primary border-round" id="editor"></div>
				<textarea class="hidden" name="content" id="content"></textarea>
			</div>
			<div class="flex flex-col m-5">
				<button type="submit" class="btn btn-primary mt-3">작성</button>
			</div>
		</form>
	</div>
</section>

<style>
	.border-round {
		border-width: 1px;
		border-radius: 0.35rem;
	}
</style>
