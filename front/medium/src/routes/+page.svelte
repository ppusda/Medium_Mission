<script>
	const repository_href = "https://github.com/ppusda/Medium_Mission_JoDongGuk";

</script>

<svelte:head>
	<title>Medium</title>
	<meta name="description" content="Medium" />
</svelte:head>

<section class="main-content">
	<div class="flex flex-row justify-center h-full content-center">
		<div class="flex flex-col text-blue-400 justify-center content-center">
			<h1 class="text-6xl text-center">Medium Project</h1>
			<p class="mt-5 text-center">유료 글 포스팅을 할 수 있는 사이트 입니다.</p>

			<div class="flex justify-center">
				<ul class="menu menu-horizontal bg-base-200 rounded-box mt-6">
					<li>
						<a class="tooltip" data-tip="Home" href="/">
							<i class="fa-solid fa-house fa-xl"></i>
						</a>
					</li>
					<li>
						<a class="tooltip" data-tip="Post" href="/post">
							<i class="fa-solid fa-table-list fa-xl"></i>
						</a>
					</li>
					<li>
						<a class="tooltip" data-tip="Repository" href="{repository_href}">
							<i class="fa-solid fa-book-bookmark fa-xl"></i>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
</section>

<style>
	.main-content {
		height: calc(100vh - 4rem);
	}
</style>
