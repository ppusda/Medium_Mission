import {loginUsername, isPaidUser, isLogin} from "./stores.js";

export async function memberCheck() {
  const response = await fetch(`http://localhost:8080/member/check`, {
    credentials: 'include',
  });
  if (response.ok) {
    const data = await response.json();

    if (data.nickname) {
      loginUsername.set(data.nickname);
    }

    isPaidUser.set(!!data.isPaid);
    isLogin.set(data.result);
  }
}