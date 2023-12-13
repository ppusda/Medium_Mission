<script>
	import {toastWarning} from "../../app.js";
	import {onMount} from "svelte";

	let currentPage = $state({});
	let searchCurrentPage = $state({});
	let totalItems = $state({});
	let totalPages = $state({});
	let postListData = $state([]);

	let loginCheck = $state({});
	let loginUsername = $state({});

	const changePage = (page) => {
		currentPage = page;
		getQuestionList();
	}

	const PrevPage = () => {
		if (currentPage > 0) {
			currentPage --;
			getQuestionList();
		}
	}

	const NextPage = () => {
		if (currentPage < totalPages-1) {
			currentPage ++;
			getQuestionList();
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
			loginUsername = data.username;
		}
	}

	async function getQuestionList() {
		const response = await fetch(`/md/post/list?page=${currentPage}`);
		const jsonResponse = await response.json();
		if (jsonResponse) {
			questionListData = jsonResponse.content;
			totalItems = jsonResponse.totalElements;
			totalPages = jsonResponse.totalPages;

			questionListData.forEach(async (question) => {
				question.createDate = formatDate(question.createDate);
			});
		}
	}

	async function searchHandleSubmit(event) {
		event.preventDefault();

		const formData = new FormData(event.target);

		if (formData.get("keyword") === '') {
			await getQuestionList();
			return;
		}

		currentPage = 0;
		const response = await fetch(`/md/post/search?page=${currentPage}`, {
			method: 'POST',
			body: formData,
		});

		const jsonResponse = await response.json();
		if (jsonResponse) {
			questionListData = jsonResponse.content;
			totalItems = jsonResponse.totalElements;
			totalPages = jsonResponse.totalPages;

			questionListData.forEach(async (question) => {
				question.createDate = formatDate(question.createDate);
			});
		}

	}

	onMount(async () => {
		currentPage = 0;
		await getQuestionList();
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

	<div class="overflow-x-auto m-5">
		<div class="hero bg-base-200">
			<div class="hero-content flex-col lg:flex-row">
				<img src="https://images.unsplash.com/photo-1571916234808-adf437ac1644?q=80&w=2099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" class="max-w-sm rounded-lg shadow-2xl m-3" />
				<div class="flex flex-col">
					<h1 class="text-5xl font-bold">New Post!</h1>
					<p class="py-6">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean purus orci, condimentum semper malesuada nec, vulputate id libero. Phasellus maximus augue nec risus vulputate gravida. Morbi blandit quam non dui fringilla, ut tristique eros efficitur.</p>
					<button class="btn btn-primary">Read Post</button>
				</div>
			</div>
		</div>
	</div>

</section>

<style>

</style>
