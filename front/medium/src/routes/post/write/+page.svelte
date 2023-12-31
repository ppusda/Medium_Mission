<script>
	import {toastWarning} from "../../../app.js";

	async function handleSubmit(event) {
		event.preventDefault();
		const formData = new FormData(event.target);

		if (formData) {
			const jsonData = {};
			for (let pair of formData.entries()) {
				jsonData[pair[0]] = pair[1];
			}
			jsonData['isPaid'] = jsonData['isPaid'] === 'on';

			const response = await fetch(`http://localhost:8080/post`, {
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

			window.location.href = `/post`;
		}
	}


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
				<div class="py-2 m-5">
					<label class="cursor-pointer label">
						<span class="label-text mr-3">유료 글로 작성</span>
						<input type="checkbox" class="toggle toggle-primary" name="isPaid"/>
					</label>
				</div>
			</div>
			<div class="flex flex-col m-5">
				<label for="title" class="form-label">제목</label>
				<input class="input input-bordered input-primary mt-3 max-w-full" name="title" id="title" type="text" placeholder="제목을 입력해주세요."/>
			</div>
			<div class="flex flex-col m-5">
				<label for="content" class="form-label">내용</label>
				<textarea class="textarea textarea-primary mt-3 resize-none" name="content" id="content" rows="8" placeholder="내용을 입력해주세요."></textarea>
			</div>
			<div class="flex flex-col m-5">
				<button type="submit" class="btn btn-primary mt-3">작성</button>
			</div>
		</form>
	</div>
</section>

<style>

</style>
