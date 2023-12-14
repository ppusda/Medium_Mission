<script>
	import {toastWarning} from "../../app.js";
	import {onDestroy, onMount} from "svelte";

	let currentPage = $state({});
	let totalPages = $state({});
	let postListData = $state([]);
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

	async function moveToWriteQuestionPage() {
		await memberCheck();
		if (loginCheck) {
			window.location.href = '/post/write';
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function memberCheck() {
		const response = await fetch(`/md/member/check`);
		if (response) {
			const data = await response.json();
			loginCheck = data.result;
		}
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

	onMount(async () => {
		currentPage = 0;
		window.addEventListener('scroll', handleScroll);
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
	<div class="flex flex-row items-center justify-end">
		<div class="flex flex-row items-center">
			<a class="btn btn-primary py-2 m-5" on:click={moveToWriteQuestionPage}>질문 등록</a>
		</div>
	</div>
	{#each postListData as data}
		<div class="overflow-x-auto m-5">
			<div class="hero bg-base-200">
				<div class="hero-content flex-col lg:flex-row">
					<img src="https://images.unsplash.com/photo-1571916234808-adf437ac1644?q=80&w=2099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" class="max-w-sm rounded-lg shadow-2xl m-3" />
					<div class="flex flex-col">
						<h1 class="text-5xl font-bold">{data.title}</h1>
						<p class="py-6">{data.content}</p>
						<button class="btn btn-primary">Read Post</button>
					</div>
				</div>
			</div>
		</div>
	{/each}

</section>

<style>

</style>
