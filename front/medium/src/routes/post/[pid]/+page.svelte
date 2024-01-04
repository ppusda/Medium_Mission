<script>
	import {onMount} from "svelte";
	import { page } from "$app/stores";
	import {goto} from "$app/navigation";
	import {toastWarning} from "../../../toastr.js";

	import {memberCheck} from "../../../member.js";
	import {isLogin, isPaidUser, loginUsername, baseUrl} from "../../../stores.js";

	import '@toast-ui/editor/dist/toastui-editor-viewer.css';
	import '@toast-ui/editor/dist/theme/toastui-editor-dark.css';
	import '@toast-ui/editor-plugin-code-syntax-highlight/dist/toastui-editor-plugin-code-syntax-highlight.css';

	import Prism from 'prismjs';
	import 'prismjs/themes/prism.css';
	import 'prismjs/components/prism-c.js';
	import 'prismjs/components/prism-sql.js';
	import 'prismjs/components/prism-bash.js';
	import 'prismjs/components/prism-java.js';
	import 'prismjs/components/prism-kotlin.js';
	import 'prismjs/components/prism-python.js';
	import 'prismjs/components/prism-markup.js';
	import 'prismjs/components/prism-javascript.js';
	import 'prismjs/components/prism-typescript.js';

	let postId =  $state({});
	let postData = $state({});
	let isPaidPost = $state({});

	let recommendCheck = $state({});
	let recommendCount = $state({});

	let viewer = $state({});

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

	async function checkRecommend() {
		const response = await fetch(`${$baseUrl}/post/${postId}/recommend`, {
			credentials: 'include',
		});
		const responseData = await response.json();

		if (response.ok) {
			recommendCheck = responseData.isRecommended;
			recommendCount = responseData.recommendCount;
		}
	}

	async function getPost() {
		const response = await fetch(`${$baseUrl}/post/${postId}`, {
			credentials: 'include',
		});
		postData = await response.json();

		isPaidPost = postData.isPaid;

		if (postData.createDate) {
			postData.createDate = formatDate(postData.createDate);
		}

		if (postData.modifiedDate) {
			postData.modifiedDate = formatDate(postData.modifiedDate);
		}

		recommendCount = postData.recommendCount;
	}

	async function moveToModifyPostPage() {
		await memberCheck();
		if ($isLogin) {
			await goto(`/post/${postId}/modify`);
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function removePost() {
		await memberCheck();
		if ($isLogin) {
			const response = await fetch(`${$baseUrl}/post/${postId}`, {
				method: 'DELETE',
				credentials: 'include',
			});

			if (!response.ok) {
				const errorData = await response.json();

				toastWarning(errorData.message);
				return;
			}

			await goto(`/post`);
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function recommendPost() {
		await memberCheck();
		if ($isLogin) {
			await fetch(`${$baseUrl}/post/${postId}/recommend`, {
				method: 'POST',
				credentials: 'include',
			});

			await checkRecommend();
			return;
		}
		toastWarning("로그인이 필요합니다.");
	}

	async function checkMembership() {
		if (isPaidPost) {
			if (!$isPaidUser) {
				toastWarning("유료 회원만 글을 볼 수 있습니다.");
				window.history.back();
			}
		}
	}

	onMount(async () => {
		const { default: Viewer } = await import('@toast-ui/editor/dist/toastui-editor-viewer');
		const { default: codeSyntaxHighlight } = await import('@toast-ui/editor-plugin-code-syntax-highlight');

		postId = $page.params['pid'];
		recommendCheck = false;

		await memberCheck();
		await getPost();

		if ($isLogin) {
			await checkRecommend();
		}

		await checkMembership();

		if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
			viewer = new Viewer({
				toolbarItems: [],
				el: document.querySelector('#viewer'),
				previewStyle: 'tab',
				initialValue: postData.content,
				hideModeSwitch: true,
				height: '40rem',
				theme: "dark",
				plugins: [[codeSyntaxHighlight, { highlighter: Prism }]]
			});
		} else {
			viewer = new Viewer({
				toolbarItems: [],
				el: document.querySelector('#viewer'),
				previewStyle: 'tab',
				initialValue: postData.content,
				hideModeSwitch: true,
				height: '40rem',
				plugins: [[codeSyntaxHighlight, { highlighter: Prism }]]
			});
		}
	});

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium Post" />
</svelte:head>

<section class="pl-10 pr-10">
	<div class="flex flex-col">
		<div class="flex flex-col content-center flex-wrap">
			<h2 class="text-3xl font-bold m-5">
				<a class="btn btn-ghost" href="/post"> <i class="fa-solid fa-arrow-left"></i> </a>
				{postData.title}
			</h2>
			<div class="flex flex-row justify-between">
				<div class="flex flex-col justify-start m-5">
					<div class="flex flex-row">
						<div class="badge badge-primary badge-outline mb-1.5">
							{#if postData.author}
								<a href="/member/{postData.author}">{postData.author}</a>
							{/if}
						</div>
					</div>
					<div class="flex flex-row">
						<div class="badge badge-primary badge-outline text-start mr-1.5">
							작성일자: {postData.createDate}
						</div>
						<div class="badge badge-primary badge-outline text-start mr-1.5">
							수정일자: {postData.modifiedDate}
						</div>
					</div>
				</div>
				<div class="flex flex-row justify-end m-5">
					<div class="flex flex-row items-center mr-5">
						<p><i class="fa-solid fa-eye"></i> {postData.viewCount}</p>
						{#if recommendCheck}
							<a class="btn btn-ghost" on:click={recommendPost}><i class="fa-solid fa-thumbs-up fa-xl"></i></a>
						{:else}
							<a class="btn btn-ghost" on:click={recommendPost}><i class="fa-regular fa-thumbs-up fa-xl"></i></a>
						{/if}
						<p>{recommendCount}</p>
					</div>
					{#if postData.author}
						{#if $loginUsername === postData.author}
							<a class="btn btn-ghost border-white mr-3" on:click={moveToModifyPostPage}>수정</a>
							<a href="#remove_post_modal" class="btn btn-ghost border-white">삭제</a>
							<div class="modal" role="dialog" id="remove_post_modal">
								<div class="modal-box">
									<h3 class="font-bold text-lg">글 삭제</h3>
									<span>정말로 삭제하시겠습니까?</span>
									<div class="modal-action">
										<a class="btn btn-error" on:click={removePost}>삭제</a>
										<a href="#" class="btn btn-ghost">닫기</a>
									</div>
								</div>
							</div>
						{/if}
					{/if}
				</div>
			</div>
			<div class="card bg-base-300 shadow-xl border m-5 w-7/12">
				<div class="card-body flex flex-col">
					<div id="viewer">
				</div>
			</div>
			</div>
		</div>
	</div>
</section>

<style>

</style>
