<script>
	import {toastWarning} from "../../../toastr.js";
	import {onMount} from "svelte";
	import {goto} from "$app/navigation";

	import {memberCheck} from "../../../member.js";
	import {uploadImage, downloadImage} from "../../../image.js";
	import {isLogin, isPaidUser, baseUrl} from "../../../stores.js";

	import '@toast-ui/editor/dist/toastui-editor.css';
	import '@toast-ui/editor/dist/theme/toastui-editor-dark.css';

	let editor = $state({});

	async function checkPermission() {
		if (!$isLogin) {
			toastWarning("로그인이 필요합니다.");
			await goto(`/post`);
		}
	}

	async function handleSubmit(event) {
		event.preventDefault();
		const formData = new FormData(event.target);

		if (formData) {
			const jsonData = {};
			for (let pair of formData.entries()) {
				jsonData[pair[0]] = pair[1];
			}
			jsonData['content'] = editor.getMarkdown();
			jsonData['isPaid'] = jsonData['isPaid'] === 'on';

			const response = await fetch(`${$baseUrl}/post`, {
				method: 'POST',
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
			}

			await goto(`/post`);
		}
	}

	onMount(async () => {
		const { default: Editor } = await import('@toast-ui/editor');

		await memberCheck();

		await checkPermission();

		if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
			editor = new Editor({
				el: document.querySelector('#editor'),
				previewStyle: 'vertical',
				height: '30rem',
				initialValue: "내용을 입력해주세요!",
				theme: 'dark',
				hooks: {
					async addImageBlobHook(blob, callback) {
						try {
							const formData = new FormData();
							formData.append('image', blob);

							const filename = await uploadImage(formData);
							const imageUrl = await downloadImage(filename);

							callback(imageUrl, 'image');
						} catch (error) {
							console.error(error);
						}
					}
				},
			});
		} else {
			editor = new Editor({
				el: document.querySelector('#editor'),
				previewStyle: 'vertical',
				initialValue: "내용을 입력해주세요!",
				height: '30rem',
				hooks: {
					async addImageBlobHook(blob, callback) {
						try {
							const formData = new FormData();
							formData.append('image', blob);

							const filename = await uploadImage(formData);
							const imageUrl = await downloadImage(filename);

							callback(imageUrl, 'image');
						} catch (error) {
							console.error(error);
						}
					}
				},
			});
		}
	});
</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post Write" />
</svelte:head>

<section class="pl-10 pr-10">
	<div>
		<form on:submit={handleSubmit} method="post">
			<div class="flex flex-row justify-between">
				<h2 class="text-3xl font-bold py-2 m-5">글 작성</h2>
				{#if $isPaidUser}
					<div class="py-2 m-5">
						<label class="cursor-pointer label">
							<span class="label-text mr-3">유료 글로 작성</span>
							<input type="checkbox" class="toggle toggle-primary" name="isPaid"/>
						</label>
					</div>
				{/if}
			</div>
			<div class="flex flex-col m-5">
				<label for="title" class="form-label">제목</label>
				<input class="input input-bordered input-primary mt-3 max-w-full" name="title" id="title" type="text" placeholder="제목을 입력해주세요."/>
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
