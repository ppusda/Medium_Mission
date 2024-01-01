<script>
  import {toastWarning} from "../../../app.js";
  import {goto} from "$app/navigation";

  async function handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    if (formData) {
      const jsonData = {};
      for (let pair of formData.entries()) {
        jsonData[pair[0]] = pair[1];
      }

      const response = await fetch(`http://localhost:8080/member/join`, {
        method: 'POST',
        credentials: 'include',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData),
      });

      if (!response.ok) {
        const errorData = await response.json();

        if (errorData.email) {
          toastWarning(errorData.email);
          return;
        }
        if (errorData.nickname) {
          toastWarning(errorData.nickname);
          return;
        }
        if (errorData.password) {
          toastWarning(errorData.password);
          return;
        }
        if (errorData.passwordConfirm) {
          toastWarning(errorData.passwordConfirm);
          return;
        }

        toastWarning(errorData.message);
        return
      }

      await goto(`/member/login`);
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
