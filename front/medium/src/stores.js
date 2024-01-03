import {writable} from "svelte/store";

export const loginUsername = writable('');
export const isPaidUser = writable(false);
export const isLogin = writable(false);