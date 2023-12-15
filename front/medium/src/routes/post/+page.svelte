<script>
	import {toastWarning} from "../../app.js";
	import {onDestroy, onMount} from "svelte";

	const repository_href = "https://github.com/ppusda/Medium_Mission_JoDongGuk";
	let currentPage = $state({});
	let totalPages = $state({});
	let postListData = $state([]);
	let hotPostListData = $state([]);
	let loginCheck = $state({});

	const NextPage = () => {
		if (currentPage >= totalPages-1) {
			toastNotice("더이상 글이 없습니다.")
			return;
		}
		currentPage ++;
		getPostList();
	}

	function handleScroll() {
		if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
			// 페이지 끝에 도달했을 때, 다음 페이지 불러오기
			NextPage();
		}
	}


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
		}
	}

	async function moveToWriteQuestionPage() {
		await memberCheck();
		if (loginCheck) {
			window.location.href = '/post/write';
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function getPostList() {
		const response = await fetch(`/md/post?page=${currentPage}`);
		const jsonResponse = await response.json();
		if (jsonResponse) {
			postListData = postListData.concat(jsonResponse.content);
			totalPages = jsonResponse.totalPages;
			postListData.forEach(async (question) => {
				question.createDate = formatDate(question.createDate);
			});
		}
	}

	async function getHotPostList() {
		const response = await fetch(`/md/post/popular_posts`);
		const jsonResponse = await response.json();
		if (jsonResponse) {
			hotPostListData = jsonResponse.content;
		}
	}

	onMount(async () => {
		currentPage = 0;
		window.addEventListener('scroll', handleScroll);
		await getHotPostList();
		await getPostList();
	});

	onDestroy(() => {
		window.removeEventListener('scroll', handleScroll);
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post List" />
</svelte:head>

<section class="pl-10 pr-10">
	<div class="flex flex-row w-full">
		<div class="flex flex-col text-blue-400 sticky top-0 h-screen">
			<div class="m-5 ml-0 mb-0">
				<p class="text-xl font-bold"><i class="fa-solid fa-star"></i> 읽어 볼 만한 글</p>
			</div>
			{#each hotPostListData as post}
				<div class="flex flex-col gap-4 w-52 m-5 ml-0">
					<div class="h-4 w-full">
						<p>{post.author}</p>
					</div>
					<div class="h-4 w-28">
						<a class="text-xl font-bold" href="/post/{post.id}">{post.title}</a>
					</div>
				</div>
			{/each}

			<div class="card w-52 bg-neutral text-neutral-content m-5 ml-0">
				<div class="card-body ">
					<h2 class="card-title">Medium에서 글 써보기!</h2>
					<div class="flex flex-col justify-center">
						<a class="btn btn-ghost" href="/post/0">작성자를 위한 FAQ</a>
						<a class="btn btn-ghost" href="/post/1">좋은 글을 위한 꿀팁</a>
						<a class="btn" on:click={moveToWriteQuestionPage}>글 작성 해보기!</a>
					</div>
				</div>
			</div>

			<div class="flex flex-row m-5 ml-0">
				<a href="https://github.com/ppusda/"><i class="fa-brands fa-github"></i> Developer</a>
				<p class="ml-1.5 mr-1.5">/</p>
				<a href="{repository_href}"><i class="fa-solid fa-book-bookmark"></i> Repository</a>
			</div>
		</div>

		<div class="flex flex-col w-full">
			{#each postListData as data}
				<div class="m-3 w-full">
					<div class="hero bg-base-200">
						<div class="hero-content flex-col lg:flex-row">
							<img src="https://images.unsplash.com/photo-1571916234808-adf437ac1644?q=80&w=2099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" class="max-w-sm rounded-lg shadow-2xl m-3" />
							<div class="flex flex-col">
								<h1 class="text-5xl font-bold">{data.title}</h1>
								<p class="py-6">{data.content}</p>
								<a class="btn btn-primary" href="/post/{data.id}">Read Post</a>
							</div>
						</div>
					</div>
				</div>
			{/each}
		</div>
	</div>
</section>

<style>
</style>
