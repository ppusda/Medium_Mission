import {writable} from "svelte/store";

export const loginUsername = writable('');
export const isPaidUser = writable(false);
export const isLogin = writable(false);

export const baseUrl = writable("http://localhost:8080");
// export const baseUrl = writable("https://api.medium.bbgk.me");