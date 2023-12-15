<script>
	import {toastWarning} from "../../../../app.js";
	import {onMount} from "svelte";
	import {page} from "$app/stores";

	let postId = $state({});
	let questionData = $state({});

	async function getQuestion() {
		const response = await fetch(`/md/post/${postId}`);
		questionData = await response.json();
	}

	async function handleSubmit(event) {
		event.preventDefault();
		const formData = new FormData(event.target);

		if (formData) {
			const response = await fetch(`/md/post/modify/${postId}`, {
				method: 'POST',
				body: formData,
			});

			if (!response.ok) {
				const errorData = await response.json();
				if (errorData.subject) {
					toastWarning(errorData.subject);
					return;
				}
			}


		}
	}

	onMount(async () => {
		postId = $page.params['pid'];
		await getQuestion();
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post Modify" />
</svelte:head>

<section class="pl-10 pr-10">
	<div>
		<h2 class="text-3xl font-bold border-bottom py-2 m-5">글 수정</h2>
		<form on:submit={handleSubmit} method="post">
			<div class="flex flex-col m-5">
				<label for="title" class="form-label">제목</label>
				<input class="input input-bordered input-primary mt-3 max-w-full" name="title" id="title" type="text" placeholder="제목을 입력해주세요." value="{questionData.subject}"/>
			</div>
			<div class="flex flex-col m-5">
				<label for="content" class="form-label">내용</label>
				<textarea class="textarea textarea-primary mt-3 resize-none" name="content" id="content" rows="8" value="{questionData.content}" placeholder="내용을 입력해주세요."></textarea>
			</div>
			<div class="flex flex-col m-5">
				<button type="submit" class="btn btn-primary mt-3">작성</button>
			</div>
		</form>
	</div>
</section>

<style>

</style>
