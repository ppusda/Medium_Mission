<script>
  import {toastWarning} from "../../../app.js";
  import {onMount} from "svelte";
  import {goto} from "$app/navigation";

  let isLogin = $state({});
  let isPaidUser = $state({});
  let loginUsername = $state({});

  async function memberCheck() {
    const response = await fetch(`http://localhost:8080/member/check`, {
      credentials: 'include',
    });
    if (response.ok) {
      const data = await response.json();

      if (data.nickname) {
        loginUsername = data.nickname;
      }

      isPaidUser = !!data.isPaid;
      isLogin = data.result;
    }
  }

  async function registerMembership() {
    await memberCheck();
    if (isLogin) {
      await fetch(`http://localhost:8080/member/membership`, {
        method: 'POST',
        credentials: 'include',
      });

      await memberCheck();
      return;
    }
    toastWarning("로그인이 필요합니다.")
  }

  async function cancelMembership() {
    await memberCheck();
    if (isLogin) {
      await fetch(`http://localhost:8080/member/membership`, {
        method: 'DELETE',
        credentials: 'include',
      });

      await memberCheck();
      await goto('/member/membership');
      return;
    }
    toastWarning("로그인이 필요합니다.")
  }

  onMount(async () => {
    isPaidUser = false;
    await memberCheck();
  });

</script>

<svelte:head>
  <title>Medium</title>
  <meta name="description" content="Medium Membership" />
</svelte:head>

<section class="pl-10 pr-10">
  <div>
    <h2 class="text-3xl font-bold border-bottom py-2 m-5">멤버쉽 가입</h2>
  </div>

  <div class="diff lg:aspect-[32/8] md:aspect-[32/16] sm:aspect-[32/24] aspect-[32/32] mb-5">
    <div class="diff-item-1">
      <div class="bg-primary text-primary-content font-black grid place-content-center">
        <div class="hero min-h-screen">
          <div class="hero-content text-center">
            <div class="max-w-md">
              <h1 class="text-5xl font-bold">🌟 Member 🌟</h1>
              <ul class="m-3">
                <li> Medium의 멤버이십니다!</li>
                <li> 🌟 Member-Only 글 작성이 가능합니다!</li>
                <li> 🌟 Member-Only 글을 읽어보실 수 있습니다!</li>
                <li> Medium과 함께 글을 작성해보고 수익을 내보세요!</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="diff-item-2">
      <div class="bg-base-200 font-black grid place-content-center">
        <div class="hero min-h-screen">
          <div class="hero-content text-center">
            <div class="max-w-md">
              <h1 class="text-5xl font-bold">Basic</h1>
              <ul class="m-3">
                <li> 미디엄의 회원이십니다!</li>
                <li> 미디엄의 기본적인 기능을 모두 이용하실 수 있습니다!</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

    {#if !isPaidUser}
      <div class="diff-resizer basic_resizer"></div>
    {:else}
      <div class="diff-resizer member_resizer"></div>
    {/if}
  </div>

  <div class="w-full">
    {#if !isPaidUser}
      <a class="btn btn-warning w-full" on:click={registerMembership}>2,000₩ 으로 시작하기!</a>
    {:else}
      <label for="cancel_membership_modal" class="btn btn-error w-full">멤버쉽 해지하기</label>
      <input type="checkbox" id="cancel_membership_modal" class="modal-toggle" />
      <div class="modal" role="dialog">
        <div class="modal-box">
          <h3 class="font-bold text-lg">멤버쉽 해지</h3>
          <span>정말로 해지하시겠습니까?</span>
          <div class="modal-action">
            <a class="btn btn-error" on:click={cancelMembership}>삭제</a>
            <label for="cancel_membership_modal" class="btn btn-ghost">취소</label>
          </div>
        </div>
      </div>
    {/if}
  </div>
</section>



<style>
  .member_resizer {
    width: 25rem;
  }

  .basic_resizer {
    width: 95rem;
  }
</style>
