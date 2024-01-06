import {loginUsername, isPaidUser, isLogin, baseUrl} from "./stores.js";

let url = '';
const unsubscribe = baseUrl.subscribe(value => {
  url = value;
});

export async function memberCheck() {
  const response = await fetch(`${url}/member/check`, {
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