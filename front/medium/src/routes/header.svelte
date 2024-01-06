<script>
  import {toastNotice, toastWarning} from "../toastr.js";
  import {onMount} from "svelte";
  import {goto} from "$app/navigation";

  import {memberCheck} from "../member.js";
  import {loginUsername, isLogin, baseUrl} from "../stores.js";

  const repository_href = "https://github.com/ppusda/Medium_Mission_JoDongGuk";


  async function moveToWritePostPage() {
    await memberCheck();
    if ($isLogin) {
      await goto('/post/write');
      return;
    }
    toastWarning("로그인이 필요합니다.");
  }

  async function logoutProcess() {
    await logout();
    toastNotice("로그아웃 되었습니다.");
    window.location.href = '/';
  }

 async function moveToMyPage() {
    await memberCheck();
    if ($isLogin) {
      window.location.href = `/member/${$loginUsername}`;
      return;
    }
    toastWarning("로그인이 필요합니다.");
  }

  async function logout() {
    await fetch(`${$baseUrl}/member/logout`, {
      method: 'DELETE',
      credentials: 'include',
    });
  }

  onMount(async () => {
    await memberCheck();
  });

</script>

<div class="sticky top-0 h-16 bg-base-200 navbar bg-base-100 z-10">
  <div class="navbar-start">
    <div class="dropdown">
      <label tabindex="0" class="btn btn-ghost btn-circle">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h7" /></svg>
      </label>
      <ul tabindex="0" class="menu menu-sm dropdown-content mt-3 z-[1] p-2 shadow bg-base-100 rounded-box w-52">
        <li><a href="/"><i class="fa-solid fa-house"></i>메인 페이지</a></li>
        <li><a href="/post"><i class="fa-solid fa-table-list"></i>글 목록</a></li>
        <li><a href="https://github.com/ppusda/"><i class="fa-brands fa-github"></i>Developer Git</a></li>
        <li><a href="{repository_href}"><i class="fa-solid fa-book-bookmark"></i>Repository</a></li>
      </ul>
    </div>
  </div>
  <div class="navbar-center">
    <a class="btn btn-ghost text-xl" href="/">Medium</a>
  </div>
  <div class="navbar-end">
    {#if $isLogin}
      <div class="dropdown dropdown-end">
        <div tabindex="0" role="button" class="btn btn-ghost rounded-btn">{$loginUsername} 님, 환영합니다!</div>
        <ul tabindex="0" class="menu dropdown-content z-[1] p-2 shadow bg-base-100 rounded-box w-52 mt-4">
          <li><a on:click={moveToWritePostPage}>글 작성</a></li>
          <li><a on:click={moveToMyPage}>마이페이지</a></li>
          <li><a href="/member/membership">미디엄과 함께하기!</a></li>
          <li><a on:click={logoutProcess}>로그아웃</a></li>
        </ul>
      </div>
      <span class="mr-3"></span>
    {:else}
      <a class="btn btn-ghost" href="/member/login">로그인</a>
    {/if}
  </div>
</div>