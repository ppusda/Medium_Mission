<script>

	import {onMount} from "svelte";

	let loginCheck = $state({});
	let postListData = $state({});

	async function memberCheck() {
		const response = await fetch(`/md/member/check`);
		if (response) {
			const data = await response.json();
			loginCheck = data.result;
		}
	}

	async function getHotPostList() {
		const response = await fetch(`/md/post/popular_posts`);
		const jsonResponse = await response.json();
		if (jsonResponse) {
			postListData = jsonResponse.content;
		}
	}

	onMount(async () => {
		loginCheck = false;
		await memberCheck();
		await getHotPostList();
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium" />
</svelte:head>

<section class="main-content">
	<div class="flex flex-row justify-center h-min w-full content-center">
		<div class="flex flex-col text-blue-400 h-min w-full mt-5">
			<div class="hero min-w-min bg-base-200 justify-start">
				<div class="hero-content flex-col lg:flex-row-reverse ml-5 p-36">
					<div>
						<h1 class="text-7xl font-bold">Medium Project</h1>
						<p class="py-6">유료 글 포스팅을 할 수 있는 사이트 입니다.</p>
						{#if loginCheck}
							<a class="btn btn-primary" href="/post">Start Read</a>
						{:else}
							<a class="btn btn-primary" href="/member/login">Get Started</a>
						{/if}
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="flex flex-row justify-center h-min w-full content-center">
		<div class="flex flex-col text-blue-400 h-min w-full mt-5">
			<div class="min-w-min bg-base-200 justify-start">
				<div class="flex-col lg:flex-row-reverse ml-5 pl-36 pt-8 pb-8">
					<div class="mb-5">
						<p class="text-xl font-bold"><i class="fa-solid fa-chart-line"></i> 현재 가장 인기가 많은 글</p>
					</div>
					<div class="flex flex-row">
						{#each postListData as post}
							<div class="flex flex-col gap-4 w-52 mr-32">
								<div class="h-4 w-28">
									<p>{post.title}</p>
								</div>
								<div class="h-4 w-full">
									<p>{post.content}</p>
								</div>
								<div class="skeleton h-32 w-full"></div>
							</div>
						{/each}
					</div>
				</div>
			</div>
		</div>
	</div>


</section>

<style>
	.main-content {
		height: calc(100vh - 4rem);
	}
</style>
