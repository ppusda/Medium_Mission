<script>

	import {onMount} from "svelte";

	let loginCheck = $state({});
	let loginUsername = $state({});
	let postListData = $state({});
	let postCount = $state({});

	async function memberCheck() {
		const response = await fetch(`/md/member/check`);
		if (response.ok) {
			const data = await response.json();

			if (data.email) {
				loginUsername = data.email.split('@')[0];
			}

			loginCheck = data.result;
		}
	}

	async function getHotPostList() {
		const response = await fetch(`/md/post/popular_posts`);
		const jsonResponse = await response.json();
		if (jsonResponse) {
			postListData = jsonResponse.content;

			postListData.forEach(async (post) => {
				post.content = formatContent(post.content);
			});
		}
	}

	function formatContent(contentPhrase) {
		if (contentPhrase.length > 30) {
			return contentPhrase.substring(0, 31).concat("...");
		}
	}

	async function handleResize() {
		const width = window.innerWidth;

		if (width >= 1730) {
			postCount = 5;
		} else if (width >= 1395) {
			postCount = 4;
		} else if (width >= 1060) {
			postCount = 3;
		} else if (width >= 725) {
			postCount = 2;
		} else {
			postCount = 1;
		}
	}

	onMount(async () => {
		loginCheck = false;
		await memberCheck();
		await getHotPostList();

		window.addEventListener('resize', handleResize);
		await handleResize();
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
			<div class="min-w-min bg-base-200 w-full justify-start">
				<div class="flex-col lg:flex-row-reverse ml-5 pl-36 pt-8 pb-8">
					<div class="mb-5">
						<p class="text-xl font-bold"><i class="fa-solid fa-chart-line"></i> 현재 가장 인기가 많은 글</p>
					</div>
					<div class="flex flex-row">
						{#if Array.isArray(postListData)}
							{#each postListData.slice(0, postCount) as post}
								<div class="flex flex-col gap-3 h-full w-52 mr-32">
									<div class="h-min w-full">
										<a class="bold" href="/post/{post.id}">{post.title}</a>
									</div>
									<div class="h-min w-full">
										<img src="https://images.unsplash.com/photo-1571916234808-adf437ac1644?q=80&w=2099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" />
									</div>
								</div>
							{/each}
						{/if}
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
