<script>
  import {toastWarning} from "../../../app.js";

  async function handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    if (formData) {
      const response = await fetch(`/md/member/join`, {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) {
        const validateData = await response.json();

        if (validateData.email) {
          toastWarning(validateData.email);
          return;
        }
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

      window.location.href = `/member/login`;
    }
  }

</script>

<svelte:head>
  <title>Medium</title>
  <meta name="description" content="Medium Member Register" />
</svelte:head>

<section class="pl-10 pr-10">
  <div>
    <h2 class="text-3xl font-bold border-bottom py-2 m-5">회원 가입</h2>
    <form on:submit={handleSubmit} method="post">
      <div class="flex flex-col m-5">
        <label for="email" class="form-label">이메일</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="email" id="email" type="text" placeholder="이메일을 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <label for="nickname" class="form-label">닉네임</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="nickname" id="nickname" type="text" placeholder="닉네임을 입력해주세요."/>
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
        <button type="submit" class="btn btn-primary mt-3">회원가입</button>
      </div>
    </form>
  </div>
</section>

<style>

</style>
