<script>
  import {toastWarning} from "../../../app.js";

  async function handleSubmit(event) {
    event.preventDefault();
    const formData = new FormData(event.target);

    if (formData) {
      const jsonData = {};
      for (let pair of formData.entries()) {
        jsonData[pair[0]] = pair[1];
      }

      const response = await fetch(`http://localhost:8080/member/login`, {
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

        if (errorData.password) {
          toastWarning(errorData.password);
          return;
        }
      }

      window.location.href = "/";
    }
  }

</script>

<svelte:head>
  <title>Medium</title>
  <meta name="description" content="Medium Member Login" />
</svelte:head>

<section class="pl-10 pr-10">
  <div>
    <h2 class="text-3xl font-bold border-bottom py-2 m-5">로그인</h2>
    <form on:submit={handleSubmit} method="post">
      <div class="flex flex-col m-5">
        <label for="email" class="form-label">아이디</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="email" id="email" type="text" placeholder="이메일을 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <label for="password" class="form-label">비밀번호</label>
        <input class="input input-bordered input-primary mt-3 max-w-full" name="password" id="password" type="password" placeholder="비밀번호를 입력해주세요."/>
      </div>
      <div class="flex flex-col m-5">
        <button type="submit" class="btn btn-primary mt-3">로그인</button>
      </div>
      <div class="flex flex-col m-5 items-center">
        <span class="text-center text-opacity-30"> ----- 처음 이용하시나요? ----- </span>
        <a class="btn btn-primary mt-3 w-96" href="/member/register">회원가입</a>
      </div>
    </form>
  </div>
</section>

<style>

</style>
