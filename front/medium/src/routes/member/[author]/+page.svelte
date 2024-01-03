<script>
	import {toastWarning, toastNotice} from "../../../toastr.js";
	import {onDestroy, onMount} from "svelte";

	import {memberCheck} from "../../../member.js";
	import {isLogin, loginUsername} from "../../../stores.js";

	import {page} from "$app/stores";
	import {goto} from "$app/navigation";

	import {remark} from "remark";
	import strip from "strip-markdown";

	const repository_href = "https://github.com/ppusda/Medium_Mission_JoDongGuk";
	let currentPage = $state({});
	let totalPages = $state({});
	let postListData = $state([]);
	let isPaidPostUser = $state({});
	let author = $state({});

	const NextPage = () => {
		if (currentPage >= totalPages-1) {
			toastNotice("더이상 글이 없습니다.")
			return;
		}
		currentPage ++;
		getAuthorPostList();
	}

	function handleScroll() {
		if ((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
			// 페이지 끝에 도달했을 때, 다음 페이지 불러오기
			NextPage();
		}
	}

	async function formatContent(contentPhrase) {
		const file = await remark()
		.use(strip)
		.process(contentPhrase);

		if (file.value.length > 250) {
			return file.value.substring(0, 251).concat("...");
		}

		file.value = file.value.replaceAll('image', '');
		file.value = file.value.trim();

		return file.value;
	}

	async function moveToModifyMemberPage() {
		await memberCheck();
		if ($isLogin) {
			await goto(`/member/${author}/modify`);
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function getAuthorPostList() {
		const response = await fetch(`http://localhost:8080/post/${author}/posts?page=${currentPage}`, {
			credentials: 'include',
		});
		const jsonResponse = await response.json();
		if (jsonResponse) {
			let formattedPostListData = [];

			for (let post of jsonResponse.content) {
				let formattedContent = await formatContent(post.content);
				let imageLinkMatch = post.content.match(/!\[.*?\]\((.*?)\)/);
				let imageLink = imageLinkMatch ? imageLinkMatch[1] : null;

				formattedPostListData.push({
					id: post.id,
					title: post.title,
					isPaid: post.isPaid,
					content: formattedContent,
					image: imageLink
				});
			}
			postListData = postListData.concat(formattedPostListData);
		}
	}

	onMount(async () => {
		currentPage = 0;
		author = $page.params['author'];
		isPaidPostUser = false;

		window.addEventListener('scroll', handleScroll);
		await memberCheck();
		await getAuthorPostList();
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
		<div class="flex flex-col text-blue-400 sticky top-0 h-screen w-2/12">
			<div class="m-5 ml-0 mb-0">
				<p class="text-xl font-bold"><i class="fa-solid fa-address-card"></i> 프로필</p>
			</div>

			<div class="card w-10/12 bg-neutral text-neutral-content mt-5 ml-0">
				<div class="card-body">
					{#if isPaidPostUser}
						<h2 class="card-title">🌟 {author}</h2>
					{:else}
						<h2 class="card-title">{author}</h2>
					{/if}
					<div class="flex flex-col justify-center">
						{#if $loginUsername === author}
							<a class="btn" on:click={moveToModifyMemberPage}>프로필 수정</a>
						{/if}
					</div>
				</div>
			</div>

			<div class="flex flex-row items-end h-full m-5 ml-0">
				<a href="https://github.com/ppusda/"><i class="fa-brands fa-github"></i> Developer</a>
				<p class="ml-1.5 mr-1.5">/</p>
				<a href="{repository_href}"><i class="fa-solid fa-book-bookmark"></i> Repository</a>
			</div>
		</div>

		<div class="flex flex-col w-full">
			{#each postListData as data}
				<div class="m-3 w-full">
					<div class="hero bg-base-200">
						<div class="hero-content flex-col w-full lg:flex-row">
							{#if data.image}
								<img src="{data.image}" class="max-w-sm rounded-lg shadow-2xl m-3" />
							{:else}
								<img src="https://images.unsplash.com/photo-1571916234808-adf437ac1644?q=80&w=2099&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" class="max-w-sm rounded-lg shadow-2xl m-3" />
							{/if}
							<div class="flex flex-col w-full justify-between">
								<h1 class="text-4xl font-bold">{data.title}</h1>
								<p class="py-6">{data.content}</p>
								<div class="flex flex-row justify-between items-center">
									<a class="btn btn-primary w-10/12" href="/post/{data.id}">Read Post</a>
									{#if data.isPaid}
										<p>🌟 Member-Only</p>
									{/if}
								</div>
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
