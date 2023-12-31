<script>
  import {toastWarning} from "../../../../app.js";
  import { goto } from '$app/navigation';
  import {onMount} from "svelte";
  import {page} from "$app/stores";

  let author = $state({});
  let memberData = $state({});

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

      if (data.isPaid) {
        isPaidUser = !!data.isPaid;
      }

      isLogin = data.result;
    }
  }

  async function checkPermission() {
    console.log(isLogin);
    if (!isLogin || loginUsername !== author) {
      toastWarning("로그인이 필요합니다.");
      await goto(`/member/${author}`);
    }
  }

  async function getMember() {
    const response = await fetch(`http://localhost:8080/member/profile`, {
      credentials: 'include',
    });
    memberData = await response.json();
  }

  async function handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    const jsonData = {};
    for (let pair of formData.entries()) {
      jsonData[pair[0]] = pair[1];
    }

    if (formData) {
      const response = await fetch(`http://localhost:8080/member/modify`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData),
      });

      if (!response.ok) {
        const validateData = await response.json();

        if (validateData.nickname) {
          toastWarning(validateData.nickname);
          return;
        }
        if (validateData.password) {
          toastWarning(validateData.password);
          return;
        }
        if (validateData.passwordConfirm) {
          toastWarning(validateData.passwordConfirm);
          return;
        }
      }

      await goto(`/member/${author}`);
    }
  }


  onMount(async () => {
    author = $page.params['author'];
    isLogin = false;

    await memberCheck();
    await checkPermission();

    await getMember();
  });

</script>

<svelte:head>
  <title>Medium</title>
  <meta name="description" content="Medium Member Register" />
</svelte:head>

<section class="pl-10 pr-10">
  <div>
    <h2 class="text-3xl font-bold border-bottom py-2 m-5">프로필 수정</h2>
    <form on:submit={handleSubmit} method="post">
      <div class="flex flex-col m-5">
        <label for="email" class="form-label">이메일</label>
        <p>{memberData.email}</p>
        <a class="mt-3 max-w-full"></a>
      </div>
      <div class="flex flex-col m-5">
        <label for="nickname" class="form-label">닉네임</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="nickname" id="nickname" type="text" value="{memberData.nickname}" placeholder="닉네임을 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <label for="password" class="form-label">비밀번호</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="password" id="password" type="password" placeholder="비밀번호를 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <label for="passwordConfirm" class="form-label">비밀번호 확인</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="passwordConfirm" id="passwordConfirm" type="password" placeholder="비밀번호를 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <button type="submit" class="btn btn-primary mt-3">수정</button>
      </div>
    </form>
  </div>
</section>

<style>

</style>
