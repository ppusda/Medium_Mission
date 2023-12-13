<script>
  import {toastNotice} from "../app.js";
  import {onMount} from "svelte";
  import Cookies from 'js-cookie';

  let loginCheck = $state({});

  async function fetchData() {
    const response = await fetch(`/md/member/check`);
    if (response) {
      const data = await response.json();
      loginCheck = data.result;
    }
  }

  function logoutProcess() {
    logout();
    toastNotice("로그아웃 되었습니다.");
  }
  async function logout() {
    await fetch(`/md/member/logout`);
    Cookies.remove("token");
    await fetchData();
  }

  onMount(async () => {
    await fetchData();
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
      </ul>
    </div>
  </div>
  <div class="navbar-center">
    <a class="btn btn-ghost text-xl" href="/">Medium</a>
  </div>
  <div class="navbar-end">
    {#if loginCheck}
      <a class="btn btn-ghost" on:click={logoutProcess}>로그아웃</a>
    {:else}
      <a class="btn btn-ghost" href="/member/login">로그인</a>
    {/if}
  </div>
</div>